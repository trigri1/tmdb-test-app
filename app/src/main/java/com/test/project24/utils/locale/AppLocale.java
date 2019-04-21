package com.test.project24.utils.locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.test.project24.utils.Consts;

import java.util.Locale;

public class AppLocale {

    private static final String KEY_LANG = "key_lang";
    private static final String LANG_PREF = "lang_pref";

    public static void changeLanguage(Activity activity, String lang) {

        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        activity.getApplicationContext()
                .getResources()
                .updateConfiguration(config, activity.getApplicationContext()
                                                     .getResources()
                                                     .getDisplayMetrics());

    }

    public static void changeLanguage(Context activity, String lang) {

        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        activity.getApplicationContext()
                .getResources()
                .updateConfiguration(config, activity.getApplicationContext()
                                                     .getResources()
                                                     .getDisplayMetrics());

    }

    //    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getCurrentLocale(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return activity.getResources()
                           .getConfiguration()
                           .getLocales()
                           .get(0);
        } else {
            //noinspection deprecation
            return activity.getResources()
                           .getConfiguration().locale;
        }
    }

    public static void putLang(Context context, String lang) {
        SharedPreferences sharedPref = context.getSharedPreferences(LANG_PREF, Context.MODE_PRIVATE);
        sharedPref.edit()
                  .putString(KEY_LANG, lang)
                  .apply();
    }

    public static String getLang(Context context) {

        SharedPreferences sharedPref = context.getSharedPreferences(LANG_PREF, Context.MODE_PRIVATE);
        return sharedPref.getString(KEY_LANG, Consts.LANG_ENG);

    }

}
