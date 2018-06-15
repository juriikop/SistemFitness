package fitness.sistem.compon.base;

import fitness.sistem.compon.ComponGlob;
import fitness.sistem.compon.network.NetworkParams;

public class SetSettings {
    public static void setNetworkParams(NetworkParams params) {
        ComponGlob.getInstance().networkParams = params;
    }

    public static void setListScreens(ListScreens listScreens) {
        ComponGlob.getInstance().setContext(listScreens.context);
        listScreens.setMapScreen(ComponGlob.getInstance().MapScreen);
        listScreens.initScreen();
    }
}
