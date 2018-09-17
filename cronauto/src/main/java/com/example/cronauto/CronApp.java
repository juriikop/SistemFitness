package com.example.cronauto;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.example.cronauto.params.CronAppParams;
import com.example.cronauto.params.CronListScreens;
import com.example.cronauto.tools.PreferenceTool;

import fitness.sistem.compon.base.SetSettings;
import fitness.sistem.compon.db.DatabaseManager;
import fitness.sistem.compon.interfaces_classes.ParamDB;

public class CronApp extends MultiDexApplication {
    private static CronApp instance;
    private Context context;

    public static CronApp getInstance() {
        if (instance == null) {
            instance = new CronApp();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        PreferenceTool.setContext(context);
//
        SetSettings.setNetworkParams(new CronAppParams());
        SetSettings.setListScreens(new CronListScreens(context));

        ParamDB paramDB = new ParamDB();
        paramDB.nameDB = "db_cron";
        paramDB.versionDB = 1;
        paramDB.addTable("catalog", "catalog_id text primary key,parent_id text,catalog_name text");
        SetSettings.setDB(new DatabaseManager(context, paramDB));

    }
}
