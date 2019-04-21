package com.test.project24.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.AnyRes;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SizeClass {

    public static void setSize(Activity context, View view, @AnyRes int widthRes, @AnyRes int heightRes) {

//        ViewGroup.LayoutParams params = getLaoutParams(view);

        setWidth(context, view, widthRes);
        setHeight(context, view, heightRes);

//        view.setLayoutParams(params);
    }


    private static ViewGroup.LayoutParams getLaoutParams(View view) {
        ViewGroup.LayoutParams params;

        if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        } else if (view.getLayoutParams() instanceof RecyclerView.LayoutParams) {
            params = (RecyclerView.LayoutParams) view.getLayoutParams();
        } else if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            params = (LinearLayout.LayoutParams) view.getLayoutParams();
        } else {
            params = view.getLayoutParams();
        }

        return params;
    }


    public static void setWidth(Activity context, View view, @AnyRes int widthRes) {
        TypedValue outValue = new TypedValue();
        Resources res = context.getResources();

        ViewGroup.LayoutParams params = getLaoutParams(view);

        res.getValue(widthRes, outValue, true);
        float width = outValue.getFloat();

        params.width = (int) (DisplayScreen.getWidth(context) * width);
    }

    public static void setHeight(Activity context, View view, @AnyRes int heightRes) {
        TypedValue outValue = new TypedValue();
        Resources res = context.getResources();

        ViewGroup.LayoutParams params = getLaoutParams(view);

        res.getValue(heightRes, outValue, true);
        float height = outValue.getFloat();

        params.height = (int) (DisplayScreen.getHeight(context) * height);
    }
}
