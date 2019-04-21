package com.test.project24.data.sharedprefs;

/**
 *
 * @author Gohar Ali
 *         This interface holds all methods that are used to store and fetch data
 *         from SharedPreferense. This is implemented by
 * @see AppPrefsHelper
 */

public interface IAppPrefsHelper {


    String getLang();

    void putLang(String lang);




}
