package com.example.cronauto;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.example.cronauto.data.db.SQL;
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
        paramDB.nameDB = SQL.DB_NAME;
        paramDB.versionDB = 5;
        paramDB.addTable(SQL.CATALOG_TAB, SQL.CATALOG_FIELDS);
        paramDB.addTable(SQL.PRODUCT_TAB, SQL.PRODUCT_FIELDS, SQL.PRODUCT_INDEX_NAME, SQL.PRODUCT_INDEX_COLUMN);
        paramDB.addTable(SQL.ORDER_TAB, SQL.ORDER_FIELDS, SQL.ORDER_INDEX_NAME, SQL.ORDER_INDEX_COLUMN);
        paramDB.addTable(SQL.PROPERTY_TAB, SQL.PROPERTY_FIELDS, SQL.PROPERTY_INDEX_NAME, SQL.PROPERTY_INDEX_COLUMN);
        paramDB.addTable(SQL.PRODUCT_ORDER, SQL.PROPERTY_FIELDS, SQL.PRODUCT_ORDER_INDEX_NAME, SQL.PRODUCT_ORDER_INDEX_COLUMN);
        SetSettings.setDB(new DatabaseManager(context, paramDB));

    }
}
