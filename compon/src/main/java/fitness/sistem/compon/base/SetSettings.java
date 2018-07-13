package fitness.sistem.compon.base;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.param.AppParams;

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
}
