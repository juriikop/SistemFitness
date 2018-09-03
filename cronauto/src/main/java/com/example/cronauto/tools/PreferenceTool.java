package com.example.cronauto.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceTool {
    private static Context context;
    private static final String PREFERENCES_NAME = "cron_app_prefs";


    //  *************************************************

    public static void setContext(Context cont) {
        context = cont;
    }

    private static SharedPreferences.Editor getEditor() {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
    }

    private static SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }
}
