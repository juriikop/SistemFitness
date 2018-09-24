package fitness.sistem.compon.json_simple;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JsonSimple {
    private int ind, indMax;
    private String json;
    private String separators = " ,\n";
    private final String quote = "\"";
    private final String a = "\\";
    private String currentSymbol;
    private String digits = "1234567890.+-";
    int ii = 0;
    public Field jsonToModel(String st) {
        if (st == null) return null;
        Field res = null;
        json = st;
        indMax = st.length();
        ind = -1;
        if (firstSymbol()) {
            res = new Field();
            res.name = "";
//            Log.d("JSON_S","currentSymbol="+currentSymbol+" indMax="+indMax);
            switch (currentSymbol) {
                case "[" :
                    res.value = getList();
                    if (res.value instanceof ListRecords) {
                        res.type = Field.TYPE_LIST_RECORD;
                    } else {
                        res.type = Field.TYPE_LIST_FIELD;
                    }
                    return res;
                case "{" :
                    res.type = Field.TYPE_CLASS;
                    res.value = getClazz();
                    return res;
            }
        }
        return res;
    }

    public String ModelToJson(Record model) {
        if (model != null) {
            StringBuilder st = new StringBuilder(512);
            st.append("{");
            String sep = "";
            for (Field f : model) {
                if (f.value != null) {
                    st.append(sep + "\"" + f.name + "\":\"" + (String) f.value + "\"");
                    sep = ",";
                }
            }
            st.append("}");
            return st.toString();
        } else {
            return null;
        }
    }

//    private ListRecords getList() {
//        ListRecords list = new ListRecords();
//        if (firstSymbol()) {
//            while ( ! currentSymbol.equals("]")) {
//                if (currentSymbol.equals("{")) {
//                    list.add(getClazz());
//                    if ( ! firstSymbol()) {
//                        Log.d("JSON_L", "No ]");
//                    }
//                } else {
//                    Log.d("JSON_L", "No { ind=" + ind);
//                }
//            }
//        }
//        return list;
//    }

    private Object getList() {
        int ii = 0;
        Log.d("QWERT","getList getList getList getList getList getList getList getList");
        if (firstSymbol()) {
            if (currentSymbol.equals("{") || currentSymbol.equals("]")) {
                ListRecords list = new ListRecords();
                while (!currentSymbol.equals("]")) {
                    ii++;
                    if (ii > 332 && ii < 325) Log.d("QWERT","getList IIIIIIII="+ii);
                    if (ii == 332) {
                        int jj = json.length();
                        int ik = ind + 500;
                        if (ik >= jj) {
                            ik = jj - 1;
                        }
                        Log.d("QWERT","getList ind="+ind+" SSSS="+json.substring(ind - 10, ik)+"<<<<<<");
                    }

                    if (currentSymbol.equals("{")) {
                        list.add(getClazz());
                        if (!firstSymbol()) {
                            Log.d("JSON_L", "No ]");
                        }
                    } else {
                        if (ii > 332 && ii < 325) Log.d("JSON_L", "No { ind=" + ind);
                    }
                }
                return list;
            } else {
                ListFields listF = new ListFields();
                while (!currentSymbol.equals("]")) {
                    listF.add(getField());
                    if (!firstSymbol()) {
                        Log.d("JSON_L", "No ]");
                    }
                }
                return listF;
            }
        }
        return new ListRecords();
    }

    private Field getField() {
        Field item = new Field();
        item.name = "";
        switch (currentSymbol) {
            case quote : // String
                Field fs = getStringValue();
                item.type = fs.type;
                item.value = fs.value;
                break;
            case "n" :   // null
                item.type = Field.TYPE_NULL;
                item.value = getNullValue();
                break;
            case "f" :   // boolean
            case "t" :
                item.type = Field.TYPE_BOOLEAN;
                item.value = getBooleanValue();
                break;
            default:
                if (digits.contains(currentSymbol)) {    // digit
//                                item.type = Field.TYPE_INTEGER;
                    Field f = getDigitalValue(item.name);
                    item.value = f.value;
                    item.type = f.type;
                } else {
                }
        }
        return item;
    }

    private Record getClazz() {
        Record list = new Record();
        if (firstSymbol()) {
            while ( ! currentSymbol.equals("}")) {
                if (ii > 332 && ii < 325) Log.d("QWERT","getClazz ii="+ii+" currentSymbol="+currentSymbol);
                if (currentSymbol.equals(quote)) {
                    Field item = getValue();
                    if (item == null) {
                        return list;
                    }
                    list.add(item);
                    if ( ! firstSymbol()) {
                        Log.d("JSON_L", "No } ind=" + ind);
                    }
                } else {
                    if (ind < indMax) {
                    Log.d("JSON_L", "Invalid character " + currentSymbol + " ind=" + ind);
                        firstSymbol();
                    }
                }
            }
        }
        return list;
    }

    private Field getValue() {
        Field item = new Field();
        item.name = getName(quote);
        if (ii > 332 && ii < 325)  Log.d("QWERT","getValue item.name="+item.name);
//        Log.d("JSON_L","NAME="+item.name);
        if (item.name != null && firstSymbol()) {
            if (ii > 332 && ii < 325)  Log.d("QWERT","getValue currentSymbol="+currentSymbol);
            if (currentSymbol.equals(":")) {
                if (firstSymbol()) {
                    if (ii > 332 && ii < 325) Log.d("QWERT","getValue firstSymbol currentSymbol="+currentSymbol+" BBB="+(currentSymbol.equals(quote)));
                    switch (currentSymbol) {
                        case quote : // String
//                            item.type = Field.TYPE_STRING;
//                            item.value = getStringValue();
                            Field fs = getStringValue();
                            item.type = fs.type;
                            item.value = fs.value;
                            break;
                        case "n" :   // null
                            item.type = Field.TYPE_NULL;
                            item.value = getNullValue();
                            break;
                        case "f" :   // boolean
                        case "t" :
                            item.type = Field.TYPE_BOOLEAN;
                            item.value = getBooleanValue();
                            break;
                        case "[" :   // List
                            item.value = getList();
                            if (item.value instanceof ListRecords) {
                                item.type = Field.TYPE_LIST_RECORD;
                            } else {
                                item.type = Field.TYPE_LIST_FIELD;
                            }
                            break;
                        case "{" :   // Class
                            item.type = Field.TYPE_CLASS;
                            item.value = getClazz();
                            break;
                        default:
                            if (digits.contains(currentSymbol)) {    // digit
//                                item.type = Field.TYPE_INTEGER;
                                Field f = getDigitalValue(item.name);
                                item.value = f.value;
                                item.type = f.type;
//                                item.value = getIntegerValue();
                            } else {
                            }
                    }
                } else {
                    Log.d("JSON_L", "No value ind=" + ind);
                    return null;
                }
            } else {
                Log.d("JSON_L", "No : ind=" + ind);
                return null;
            }
        } else {
            Log.d("JSON_L", "No : ind=" + ind);
            return null;
        }
        return item;
    }

    private Object getNullValue() {
        String st = json.substring(ind, ind + 4);
        if (st.toUpperCase().equals("NULL")) {
            ind+=3;
        } else {
            Log.d("JSON_L", "No NULL ind=" + ind);
        }
        return null;
    }

    private Boolean getBooleanValue() {
        String st;
        switch (currentSymbol) {
            case "f" :
                st = json.substring(ind, ind + 5);
                if (st.toUpperCase().equals("FALSE")) {
                    ind+=4;
                    return new Boolean(false);
                } else {
                    return null;
                }
            case "t" :
                st = json.substring(ind, ind + 4);
                if (st.toUpperCase().equals("TRUE")) {
                    ind+=3;
                    return new Boolean(true);
                } else {
                    return null;
                }
            default:
            return null;
        }
    }

    private Field getStringValue() {
        if (ii > 332) Log.d("QWERT","getStringValue ++++++++++++++++++");
        int i = ind, j;
        do {
            j = i + 1;
            i = json.indexOf(quote, j);
            if (i < 0) {
                Log.d("JSON_L", "No \" ind=" + ind);
            }
        } while (json.substring(i - 1, i).equals("\\"));
        String st = json.substring(ind + 1, i);
        if (ii > 332 && ii < 325) Log.d("QWERT","getStringValue SSSSSSSttttt="+st);
        st = delSlesh(st);
        ind = i;
        Field field = new Field();
        field.name = "";
        if (st.startsWith("/D")) {
            String t = st.substring(6, 18);
            Date d = new Date(Long.valueOf(t));
            field.type = Field.TYPE_DATE;
            field.value = d;
        } else {
            field.type = Field.TYPE_STRING;
            field.value = st;
        }
        return field;
    }

//    private String delSlesh(String st) {
//        char[] c = st.toCharArray();
//        StringBuilder builder = new StringBuilder();
//        int start = 0;
//        int ik = c.length;
//        for (int i = 0; i < ik; i++) {
//            if (c[i] == '\\') {
//                if (i > 0) {
//                    builder.append(c, start, i - start);
//                }
//                start = i + 1;
//            }
//        }
//        if (start > 0) {
//            builder.append(c, start, ik - start);
//            return builder.toString();
//        } else {
//            return st;
//        }
//    }

    private String delSlesh(String st) {
        char[] c = st.toCharArray();
        StringBuilder builder = new StringBuilder();
        int i1;
        int ik = c.length;
        boolean isYetSlash = false;
        for (int i = 0; i < ik; i++) {
            if (c[i] == '\\') {
                i1 = i + 1;
                if (i1 < ik ) {
                    char c1 = c[i1];
                    if (c1 == 'u') {
                        int iu = i + 5;
                        if (iu < ik) {
                            char cu = (char) Integer.parseInt(new String(new char[]{c[i + 2], c[i + 3], c[i + 4], c[i + 5]}), 16);
                            builder.append(cu);
                            i = iu;
                        }
                    } else {
                        builder.append(c[i]);
                        isYetSlash = true;
                    }
                }
            } else {
                builder.append(c[i]);
            }
        }
        String result = builder.toString();
        c = result.toCharArray();
        ik = c.length;
        builder = new StringBuilder();
        int start = 0;
        if (isYetSlash) {
            for (int i = 0; i < ik; i++) {
                if (c[i] == '\\') {
                    i1 = i + 1;
                    if (i1 < ik) {
                        if (c[i1] == '/') {
                            if (i > 0) {
                                builder.append(c, start, i - start);
                            }
                            start = i + 1;
                        }
                    }
                }
            }
            if (start > 0) {
                builder.append(c, start, ik - start);
                result = builder.toString();
            }
        }
        return result;
    }

    private Integer getIntegerValue() {
        int j = -1;
        int l = json.length();
        for (int i = ind; i < l; i++) {
            if ( ! digits.contains(json.substring(i, i + 1))) {
                j = i;
                break;
            }
        }
        if (j == -1) {
            return null;
        } else {
            String st = json.substring(ind, j);
            ind = j - 1;
            return Integer.valueOf(st);
        }
    }

    private Field getDigitalValue(String name) {
        Field f = new Field();
        f.name = name;
        int j = -1;
        int l = json.length();
        for (int i = ind; i < l; i++) {
            if ( ! digits.contains(json.substring(i, i + 1))) {
                j = i;
                break;
            }
        }
        if (j == -1) {
            return null;
        } else {
            String st = json.substring(ind, j);
            ind = j - 1;
            if (st.contains(".")) {
                Double d = Double.valueOf(st);
                f.type = Field.TYPE_DOUBLE;
                f.value = d;
                return f;
            } else {
                f.type = Field.TYPE_LONG;
                f.value = Long.valueOf(st);
                return f;
            }
        }
    }

    private int indexOf(String separ, int begin) {
        int l = json.length();
        for (int i = begin; i < l; i++) {
            if (separ.contains(json.substring(i, i + 1))) {
                return i;
            }
        }
        return -1;
    }

    private String getName(String separ) {
//        String st = "";
        String separators_1 = "}]";
        boolean errorName = false;
//        int i = json.indexOf(quote, ind + 1);
        ind++;
        int i = ind;
        currentSymbol = json.substring(ind, ind + 1);
        while ( ! currentSymbol.equals(quote) && ind < indMax) {
            if (separators_1.contains(currentSymbol)) {
                errorName = true;
                break;
            }
            ind++;
            currentSymbol = json.substring(ind, ind + 1);
        }
        if (errorName) {
            return null;
        } else {
            return json.substring(i, ind);
//            if (i > -1) {
//                st = json.substring(ind + 1, i);
//                ind = i;
//            } else {
//                Log.d("JSON_L", "No name ind=" + ind);
//            }
        }
//        return st;
    }

    private boolean firstSymbol() {
        ind++;
        currentSymbol = json.substring(ind, ind + 1);
        while (separators.contains(currentSymbol)) {
            ind++;
            if (ind < indMax) {
                currentSymbol = json.substring(ind, ind + 1);
            } else {
                break;
            }
        }
//        Log.d("JSON_S","firstSymbol ind="+ind+" currentSymbol="+currentSymbol+"<<");
        return ind < indMax;
    }
}
