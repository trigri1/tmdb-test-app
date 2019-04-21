package com.test.project24.di.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 *
 *
 * @author Gohar Ali
 *         Custom defined annotaion that is used to differentiate between Activity Context and Application Context.
 */


@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityContext {
}
