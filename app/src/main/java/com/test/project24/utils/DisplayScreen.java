package com.test.project24.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
import android.view.Surface;

public class DisplayScreen {


    public static int getHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    public static int getWidth(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }


    public static float getHeightDpi(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels / displayMetrics.density;

    }

    public static float getWidthDpi(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels / displayMetrics.density;

    }


    public static int getScreenOrientation(Activity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = getWidth(activity);
        int height = getHeight(activity);

        AppLogger.e("getScreenOrientation", "rotation " + rotation);

        int orientation;
        // if the device's natural orientation is portrait:
        if ((rotation == Surface.ROTATION_0
                || rotation == Surface.ROTATION_180) && height > width
                || (rotation == Surface.ROTATION_90
                || rotation == Surface.ROTATION_270) && width > height) {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    AppLogger.e("getScreenOrientation", "ROTATION_0 ");

                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    AppLogger.e("getScreenOrientation", "ROTATION_90 ");

                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    AppLogger.e("getScreenOrientation", "ROTATION_180 ");

                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    AppLogger.e("getScreenOrientation", "ROTATION_270 ");

                    break;
                default:
                    AppLogger.e("getScreenOrientation", "Unknown screen orientation. Defaulting to " +
                            "portrait.");
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    break;
            }
        }
        // if the device's natural orientation is landscape or if the device
        // is square:
        else {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    AppLogger.e("getScreenOrientation", "LAND_ROTATION_0 ");

                    break;
                case Surface.ROTATION_90:
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    AppLogger.e("getScreenOrientation", "LAND_ROTATION_90 ");

                    break;
                case Surface.ROTATION_180:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    AppLogger.e("getScreenOrientation", "LAND_ROTATION_180 ");

                    break;
                case Surface.ROTATION_270:
                    orientation =
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                    AppLogger.e("getScreenOrientation", "LAND_ROTATION_270 ");

                    break;
                default:
                    AppLogger.e("getScreenOrientation", "Unknown screen orientation. Defaulting to " +
                            "landscape.");
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                    break;
            }
        }

        return orientation;
    }

}
