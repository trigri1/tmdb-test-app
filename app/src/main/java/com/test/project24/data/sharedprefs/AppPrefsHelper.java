package com.test.project24.data.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.test.project24.di.annotations.ApplicationContext;
import com.test.project24.di.annotations.PreferenceInfo;
import com.test.project24.utils.Consts;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Gohar Ali
 *         * This class is responsible for implementing all the methods that are declared in
 * @see IAppPrefsHelper and return proper data types for each mathod.
 * This class is singlton (Based on singlton Design Pattern).
 */
@Singleton
public class AppPrefsHelper implements IAppPrefsHelper {


    private String KEY_LANG = "key_lang";

    private final SharedPreferences mPrefs;
    private final SharedPreferences.Editor editor;


    /**
     * Constructor is provided parameters using
     * Dependency Injection (Dagger2)
     *
     * @see ApplicationModule#provideSharedPrefsName()
     */

    @Inject
    public AppPrefsHelper(@ApplicationContext Context c, @PreferenceInfo String sharedPrefName) {

        mPrefs = c.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        editor = mPrefs.edit();

    }

    @Override
    public String getLang() {
        return mPrefs.getString(KEY_LANG, Consts.LANG_ENG);
    }

    @Override
    public void putLang(String lang) {
        editor.putString(KEY_LANG, lang);
        editor.commit();
    }
}
