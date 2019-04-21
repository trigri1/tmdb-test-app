package com.test.project24.di.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Gohar Ali on 30/10/2017.
 *
 * @author Gohar Ali
 *         Custom defined annotaion that is used to define scope of a class. This is specifically for each activity.
 *         that means if any class is annotated with this will have scope for the activity life time.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
