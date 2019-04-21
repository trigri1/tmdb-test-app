package com.test.project24.di.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Gohar Ali on 10/11/2017.
 *
 * @author Gohar Ali
 *         Custom defined annotaion that is used to define between data that is used specifically by SharedPreference.
 * @see com.khaleef.one23fun.data.sharedprefs.AppPrefsHelper
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface PreferenceInfo {
}
