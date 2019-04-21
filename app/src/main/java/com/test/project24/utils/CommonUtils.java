package com.test.project24.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Toast;

import com.test.project24.BuildConfig;
import com.test.project24.R;

/**
 * Created by Gohar Ali on 01/11/2017.
 */

public class CommonUtils {


    public static Snackbar showSnackBar(View view, String text, int duration) {

        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        snackbar.setDuration(duration);


        return snackbar;
    }


    public static String getLastToken(String str, String separatorRegex) {
//        String tokens[] = str.split(separatorRegex);

        return str.substring(str.lastIndexOf(separatorRegex) + 1);
//        return tokens[tokens.length - 1];
    }

    public static String getFirstToken(String str, String separatorRegex) {
        String[] strs = str.split(" ");
        if (strs.length > 1) {
            str = strs[0];
        }
        return str;
    }

    public static int dpToPx(int dp, Resources res) {
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    public static int pxToDp(int px, Resources res) {
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

    }


    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();

        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            progressDialog.getWindow().setGravity(Gravity.CENTER);
        }

//        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.dialog_progress);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);


        return progressDialog;
    }

    public static void showToast(Context context, int messageId) {
        showToast(context, context.getString(messageId));
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


    public static void setActivityToggleFullScreen(View decorView, int uiOptions) {


        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
//        int uiOptions = getActivity().getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("setActivityFullScreen", "Turning immersive mode mode off. ");
        } else {
            Log.i("setActivityFullScreen", "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
//        if (Build.VERSION.SDK_INT >= 14) {
//            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        decorView.setSystemUiVisibility(newUiOptions);

    }



    public static void changeStatusBarColor(Activity activity) {

        //changing statusbar color
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.black));
        }
    }


    public static void changeBackgroundTint(Context context, View layout, int color) {


        final Drawable originalDrawable = layout.getBackground();
        final Drawable wrappedDrawable = DrawableCompat.wrap(originalDrawable);
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color));

        layout.setBackground(wrappedDrawable);

    }


    public static void changeBackgroundTint(View layout, int color) {

        final Drawable originalDrawable = layout.getBackground();
        final Drawable wrappedDrawable = DrawableCompat.wrap(originalDrawable);
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color));

        layout.setBackground(wrappedDrawable);

    }


    public static Boolean isTablet(Context context) {
        return false;//context.getResources().getBoolean(R.bool.isTablet);
    }


    public static void crossFade(Context context, View viewToShow, final View viewToHide) {
        int mShortAnimationDuration = context.getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        viewToShow.setAlpha(0f);
        viewToShow.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        viewToShow.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        viewToHide.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        viewToHide.setVisibility(View.GONE);
                    }
                });
    }


    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
//        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density) * 4);
        a.setDuration(Consts.DEFAULT_TIME);
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
//        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density) * 4);

        a.setDuration(Consts.DEFAULT_TIME);
        v.startAnimation(a);
    }


    public static void enableControlsInLayout(boolean enable, ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                enableControlsInLayout(enable, (ViewGroup) child);
            }
        }
    }


    public static void addStrictMood() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }


}
