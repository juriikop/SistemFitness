package fitness.sistem.compon.json_simple;
import android.util.Log;

import java.util.ArrayList;

public class Record extends ArrayList<Field>{

    public Object getValue(String name) {
        Field f = getField(name);
        if (f == null) {
            return null;
        } else {
            return f.value;
        }
    }

    public Record addIntField(String name, int value) {
        add(new Field(name, Field.TYPE_INTEGER, value));
        return this;
    }

    public Field getField(String name) {
        if (name.indexOf(".") < 0) {
            for (Field f : this) {
                if (f.name.equals(name)) {
                    return f;
                }
            }
        } else {
            String st;
//            String st = name.replaceAll("\\.", "-");
//            String[] nameList = st.split("-");
            String[] nameList = name.split("\\.");
            Record record = this;
            int ik = nameList.length - 1;
            boolean yes;
            for (int i = 0; i < ik; i++) {
                st = nameList[i];
                yes = true;
                for (Field f : record ) {
                    if (f.name.equals(st)) {
                        if (f.type == Field.TYPE_CLASS){
                            record = (Record) f.value;
                            yes = false;
                            break;
                        } else {
                            return null;
                        }
                    }
                }
                if (yes) {
                    return null;
                }
            }
            st = nameList[ik];
            for (Field f : record ) {
                if (f.name.equals(st)) {
                    return f;
                }
            }
        }
        return null;
    }

    public Double getDouble(String name) {
        Field f = getField(name);
        if (f != null) {
            switch (f.type) {
                case Field.TYPE_FLOAT:
                case Field.TYPE_DOUBLE:
                    return (double) f.value;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    public Float getFloat(String name) {
        Field f = getField(name);
        if (f != null) {
            switch (f.type) {
                case Field.TYPE_DOUBLE:
                    double d = (double) f.value;
                    return (float) d;
                case Field.TYPE_FLOAT:
                case Field.TYPE_INTEGER:
                    return (float) f.value;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    public String getString(String name) {
        Field f = getField(name);
        if (f != null) {
            return String.valueOf(f.value);
        } else {
            return null;
        }
    }

    public Integer getInteger(String name) {
        Field f = getField(name);
        if (f != null) {
            return (Integer)f.value;
        } else {
            return null;
        }
    }

    public int getInt(String name) {
        Field f = getField(name);
        if (f != null) {
            if (f.value instanceof Long) {
                long vv = (Long) f.value;
                int ii = (int)vv;
                return ii;
            } else if (f.value instanceof Integer) {
                return (Integer) f.value;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public boolean getBooleanVisibility(String name) {
        Field f = getField(name);
        if (f != null) {
            switch (f.type) {
                case Field.TYPE_BOOLEAN : return (boolean) f.value;
                case Field.TYPE_DOUBLE : return ((Double) f.value) != 0d;
                case Field.TYPE_INTEGER : return ((Integer) f.value) != 0;
                case Field.TYPE_LONG : return ((Long) f.value) != 0;
                case Field.TYPE_STRING : return ((String) f.value).length() > 0;
                default: return false;
            }
        } else {
            return false;
        }
    }

    public void setString(String nameField, String value) {
        Field f = getField(nameField);
        if (f != null) {
            f.value = value;
        } else {
            f = new Field(nameField, Field.TYPE_STRING, value);
            add(f);
        }
    }

    public void setInteger(String nameField, Integer value) {
        Field f = getField(nameField);
        if (f != null) {
            f.value = value;
        } else {
            f = new Field(nameField, Field.TYPE_INTEGER, value);
            add(f);
        }
    }

    public void setBoolean(String nameField, Boolean value) {
        Field f = getField(nameField);
        if (f != null) {
            f.value = value;
        } else {
            f = new Field(nameField, Field.TYPE_BOOLEAN, value);
            add(f);
        }
    }

    @Override
    public String toString() {
        SimpleRecordToJson recordToJson = new SimpleRecordToJson();
        return recordToJson.recordToJson(this);
    }
}
