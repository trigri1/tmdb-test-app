package com.test.project24.ui;

import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.test.project24.data.network.models.MovieModel;
import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.ui.main.changes_frag.adapter.ChangesAdapter;
import com.test.project24.ui.search.search_frag.adapter.SearchAdapter;
import com.test.project24.utils.ImageLoadingUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gohar Ali on 25/02/2018.
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
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), span, GridLayoutManager.VERTICAL, false));
    }


    @BindingAdapter({"setImage", "imageSize"})
    public static void setImageWIthSize(ImageView imageView, String url, String imageSize) {
        ImageLoadingUtils.loadImage(imageView, url, imageSize);
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
