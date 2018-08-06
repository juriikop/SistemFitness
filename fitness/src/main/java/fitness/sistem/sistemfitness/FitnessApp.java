package fitness.sistem.sistemfitness;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import fitness.sistem.compon.base.DataBase;
import fitness.sistem.compon.base.SetSettings;
import fitness.sistem.compon.interfaces_classes.ParamDB;
import fitness.sistem.sistemfitness.network.MyAppParams;
import fitness.sistem.sistemfitness.params.MyListScreens;
import fitness.sistem.sistemfitness.tools.PreferenceTool;
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
        PreferenceTool.setContext(context);
        String st = PreferenceTool.getAppColors();
        if (st.length() > 0) {
            AppColors.jsonToColor(st);
        }
        SetSettings.setNetworkParams(new MyAppParams());
        SetSettings.setListScreens(new MyListScreens(context));
        SetSettings.setLocale("uk");

        ParamDB paramDB = new ParamDB();
        paramDB.nameDB = "db_fitness";
        paramDB.versionDB = 1;
        paramDB.addTable("qqqq", "ZZZZZZZZZZZZZZZZZZZZZZZZZZ");
        SetSettings.setDB(new DataBase(context, paramDB));

    }
}
