package fitness.sistem.compon.base;

import android.content.Context;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.interfaces_classes.ParamDB;
import fitness.sistem.compon.param.AppParams;
import fitness.sistem.compon.tools.ComponPrefTool;

public class SetSettings {
    public static void setNetworkParams(AppParams params) {
        ComponGlob.getInstance().appParams = params;
    }

    public static void setListScreens(ListScreens listScreens) {
        ComponGlob.getInstance().setContext(listScreens.context);
        listScreens.setMapScreen(ComponGlob.getInstance().MapScreen);
        listScreens.initScreen();
    }

    public static void addParam(String name, String value) {
        ComponGlob.getInstance().addParamValue(name, value);
    }

    public static void setLocale(String locale) {
        ComponPrefTool.setLocale(locale);
    }

    public static void setDB(BaseDB baseDB) {
        ComponGlob.getInstance().baseDB = baseDB;
    }

}
