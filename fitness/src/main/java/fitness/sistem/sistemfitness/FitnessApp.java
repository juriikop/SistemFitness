package fitness.sistem.sistemfitness;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import fitness.sistem.compon.base.SetSettings;
import fitness.sistem.sistemfitness.network.MyNetworkParams;
import fitness.sistem.sistemfitness.params.MyListScreens;
import fitness.sistem.sistemfitness.tools.changeColor.AppColors;

public class FitnessApp extends MultiDexApplication {
    private static FitnessApp instance;
    private Context context;

    public static FitnessApp getInstance() {
        if (instance == null) {
            instance = new FitnessApp();
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
