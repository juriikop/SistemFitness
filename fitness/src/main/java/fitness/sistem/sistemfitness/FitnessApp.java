package fitness.sistem.sistemfitness;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import fitness.sistem.compon.base.SetSettings;
import fitness.sistem.sistemfitness.network.MyNetworkParams;
import fitness.sistem.sistemfitness.params.MyListScreens;

public class FuelApp extends MultiDexApplication {
    private static FuelApp instance;
    private Context context;

    public static FuelApp getInstance() {
        if (instance == null) {
            instance = new FuelApp();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        SetSettings.setNetworkParams(new MyNetworkParams());
        SetSettings.setListScreens(new MyListScreens(context));
    }
}
