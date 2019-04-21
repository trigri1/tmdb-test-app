package com.test.project24.di.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Gohar Ali on 22/11/2017.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheInfo {
}
