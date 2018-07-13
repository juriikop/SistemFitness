package fitness.sistem.compon;

import android.content.Context;

import fitness.sistem.compon.components.MultiComponents;
import fitness.sistem.compon.interfaces_classes.Param;
import fitness.sistem.compon.param.AppParams;
import fitness.sistem.compon.param.ParamModel;
import fitness.sistem.compon.json_simple.Field;
import fitness.sistem.compon.json_simple.FieldBroadcaster;
import fitness.sistem.compon.json_simple.Record;
import fitness.sistem.compon.network.CacheWork;
import fitness.sistem.compon.tools.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponGlob {
    private static ComponGlob instance;
    public FieldBroadcaster profile;
    public Context context;
    public CacheWork cacheWork;
    public Map<String, MultiComponents> MapScreen;
    public AppParams appParams;
    public List<Param> paramValues = new ArrayList<>();
    public String token;
    public String language;

    public static ComponGlob getInstance() {
        if (instance == null) {
            instance = new ComponGlob();
        }
        return instance;
    }

    public ComponGlob() {
        instance = this;
        token = "";
        language = "ru";
        MapScreen = new HashMap<String, MultiComponents>();
        profile = new FieldBroadcaster("profile", Field.TYPE_RECORD, null);
    }

    public void setContext(Context context) {
        this.context = context;
        cacheWork = new CacheWork(context);
    }

    public void setParam(Record fields) {
        int ik = paramValues.size();
        for (Field f: fields) {
            String name = f.name;

            for (int i = 0; i < ik; i++) {
                Param param = paramValues.get(i);
                if (param.name.equals(name)) {
                    switch (f.type) {
                        case Field.TYPE_STRING :
                            param.value = new String((String) f.value);
                            break;
                        case Field.TYPE_INTEGER :
                            param.value = String.valueOf((Integer) f.value);
                            break;
                        case Field.TYPE_LONG :
                            param.value = String.valueOf((Long) f.value);
                            break;
                        case Field.TYPE_FLOAT :
                            param.value = String.valueOf((Float) f.value);
                            break;
                        case Field.TYPE_DOUBLE :
                            param.value = String.valueOf((Double) f.value);
                            break;
                        case Field.TYPE_BOOLEAN :
                            param.value = String.valueOf((Boolean) f.value);
                            break;
                    }
                    break;
                }
            }
        }
    }

    public void addParam(String paramName) {
        for (Param param : paramValues) {
            if (paramName.equals(param.name)) {
                return;
            }
        }
        paramValues.add(new Param(paramName, ""));
    }

    public void addParamValue(String paramName, String paramValue) {
        for (Param param : paramValues) {
            if (paramName.equals(param.name)) {
                return;
            }
        }
        paramValues.add(new Param(paramName, paramValue));
    }

    public String installParamName(String param, String url) {
        String st = "";
        if (param != null && param.length() > 0) {
            if (url.contains("?")) {
                st = "&";
            } else {
                st = "?";
            }
            String[] paramArray = param.split(Constants.SEPARATOR_LIST);
            String sep = "";
            for (String paramOne : paramArray) {
                for (Param paramV : paramValues) {
                    if (param.equals(paramV.name)) {
                        String valuePar = paramV.value;
                        if (valuePar != null && valuePar.length() > 0) {
                            st = st + sep + paramOne + "=" + paramV.value;
                            sep = "&";
                        }
                        break;
                    }
                }
            }
        }
        if (st.length() == 1) {
            st = "";
        }
        return st;
    }

    public String installParamSlash(String param) {
        String st = "";
        if (param != null && param.length() > 0) {
            String[] paramArray = param.split(Constants.SEPARATOR_LIST);
            for (String par : paramArray) {
                for (Param paramV : paramValues) {
                    if (param.equals(paramV.name)) {
                        if (paramV.value != null && paramV.value.length() > 0) {
                            st = st + "/" + paramV.value;
                        }
                        break;
                    }
                }
            }
        }
        return st;
    }

    public String getParamValue(String nameParam) {
        for (Param paramV : paramValues) {
            if (nameParam.equals(paramV.name)) {
                return paramV.value;
            }
        }
        return "";
    }

    public String installParam(String param, ParamModel.TypeParam typeParam, String url) {
        switch (typeParam) {
            case NAME: return installParamName(param, url);
            case SLASH: return installParamSlash(param);
            default: return "";
        }
    }
}
