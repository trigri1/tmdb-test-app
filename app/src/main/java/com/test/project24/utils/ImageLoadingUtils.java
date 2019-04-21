package com.test.project24.utils;

import android.widget.ImageView;

import com.bumptech.glide.load.DecodeFormat;
import com.test.project24.BuildConfig;
import com.test.project24.R;
import com.test.project24.utils.glide_utils.GlideApp;

/**
 * Created by Gohar Ali on 05/12/2017.
 */

public class ImageLoadingUtils {

    private static final String TAG = ImageLoadingUtils.class.getSimpleName();

    public static void loadImage(ImageView imageView, String posterUrl, String imageSize) {

        String url = null;
        if (posterUrl != null)
            url = BuildConfig.BASE_URL_IMAGE + imageSize + "/" + posterUrl;

//        AppLogger.e(TAG, url);

        GlideApp.with(imageView.getContext())
                .asBitmap()
                .format(DecodeFormat.PREFER_RGB_565)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .fallback(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .override(imageView.getWidth(), imageView.getHeight())
                .into(imageView);
    }


    public static boolean isImageSVG(String url) {
        if (url.endsWith(".svg")) {
            return true;
        } else {
            return false;
        }

    }

}
