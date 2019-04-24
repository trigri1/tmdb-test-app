package com.test.project24.di.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author Gohar Ali
 *         Custom defined annotaion that is used to define between data that is used specifically by SharedPreference.
 * @see com.test.project24.data.sharedprefs.AppPrefsHelper
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface PreferenceInfo {
}
