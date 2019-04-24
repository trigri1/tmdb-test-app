package com.test.project24.di.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author goharali
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheInfo {
}
