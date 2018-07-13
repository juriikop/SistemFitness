package fitness.sistem.sistemfitness.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceTool {
    private static Context context;
    private static final String PREFERENCES_NAME = "fitness_app_prefs";
    private static final String APP_COLOR = "APP_COLOR";
    private static final String COUNTRY = "COUNTRY";

    public static void setCountry(String value) {
        getEditor().putString(COUNTRY, value).commit();
    }

    public static String getCountry() {
        return getSharedPreferences().getString(COUNTRY, "");
    }

    public static void setAppColors(String value) {
        getEditor().putString(APP_COLOR, value).commit();
    }

    public static String getAppColors() {
        return getSharedPreferences().getString(APP_COLOR, "");
    }

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
