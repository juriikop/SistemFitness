package fitness.sistem.sistemfitness;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import fitness.sistem.compon.db.DatabaseManager;
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
        paramDB.addTable("order_tab", "order_id integer primary key,order_name text");
        SetSettings.setDB(new DatabaseManager(context, paramDB));

    }
}
