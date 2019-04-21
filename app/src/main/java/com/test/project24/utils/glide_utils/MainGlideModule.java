package com.test.project24.utils.glide_utils;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by Gohar Ali on 01/11/2017.
 */
@GlideModule
public class MainGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
    }

    // Disable manifest parsing to avoid adding similar modules twice.
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
