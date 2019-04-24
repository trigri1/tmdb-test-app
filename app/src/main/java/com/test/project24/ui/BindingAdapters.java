package com.test.project24.ui;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.project24.data.network.models.MovieModel;
import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.ui.main.changes_frag.adapter.ChangesAdapter;
import com.test.project24.ui.search.search_frag.adapter.SearchAdapter;
import com.test.project24.utils.CommonUtils;
import com.test.project24.utils.ImageLoadingUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author goharali
 */

public final class BindingAdapters {

    private BindingAdapters() {
    }

    @BindingAdapter("adapter")
    public static void setSearchAdapter(RecyclerView recyclerView
            , List<MovieModel> contentList) {

        SearchAdapter adapter = (SearchAdapter) recyclerView.getAdapter();

        if (adapter == null) {
            adapter = new SearchAdapter();
            recyclerView.setAdapter(adapter);
        }

        adapter.setData(contentList);
        recyclerView.invalidate();
    }

    @BindingAdapter("changesAdapter")
    public static void setChangesAdapter(RecyclerView recyclerView, List<MovieDetail> contentList) {
        ChangesAdapter adapter = (ChangesAdapter) recyclerView.getAdapter();

        if (adapter == null) {
            adapter = new ChangesAdapter();
            recyclerView.setAdapter(adapter);
        }

        if (contentList == null) {
            contentList = new ArrayList<>();
        }

        adapter.updateAdapter(contentList);
        recyclerView.invalidate();
    }

    @BindingAdapter("gridManager")
    public static void setHorizontalSectionAdapter(RecyclerView recyclerView, int span) {

        int orientation = CommonUtils.isTablet(recyclerView.getContext()) ? GridLayoutManager.HORIZONTAL : GridLayoutManager.VERTICAL;
        span = CommonUtils.isTablet(recyclerView.getContext()) ? 1 : 3;

        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), span, orientation, false));
    }


    @BindingAdapter({"setImage", "imageSize"})
    public static void setImageWIthSize(ImageView imageView, String url, String imageSize) {
        ImageLoadingUtils.loadImage(imageView, url, imageSize);
    }

    @BindingAdapter("spannableText")
    public static void setSpannableText(TextView textView, String text) {
        if (!CommonUtils.isStringEmptyOrNull(text) && text.contains(":")) {
            final SpannableStringBuilder ssb = new SpannableStringBuilder(text);
            ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, text.indexOf(":"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(ssb, TextView.BufferType.SPANNABLE);
        }
    }

    @BindingAdapter("showView")
    public static void setViewVisibility(View view, boolean show) {

        if (show) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
