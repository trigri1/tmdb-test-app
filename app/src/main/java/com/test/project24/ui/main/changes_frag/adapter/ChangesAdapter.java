package com.test.project24.ui.main.changes_frag.adapter;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.ui.base.BaseViewHolder;
import com.test.project24.ui.search.search_frag.view_holder.ItemViewHolder;
import com.test.project24.ui.search.search_frag.view_holder.LoaderViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ChangesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOADER = 2;

    private List<MovieDetail> list = new ArrayList<>();

    private boolean showLoader = false;


    public void updateAdapter(List<MovieDetail> updatedList) {

        if (updatedList.isEmpty()) {
            list.clear();
            notifyDataSetChanged();
        } else {
            new Handler().post(() -> {
                if (list.size() == 0) {
                    list.addAll(updatedList);
                    notifyItemRangeInserted(0, list.size());
                } else {
                    DiffUtil.DiffResult result = DiffUtil.calculateDiff(new ChangesDiffCallBack(list, updatedList));
                    list.clear();
                    list.addAll(updatedList);
                    result.dispatchUpdatesTo(ChangesAdapter.this);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {

        int type = TYPE_ITEM;

        if (position != 0 && position == getItemCount() - 1) {
            type = TYPE_LOADER;
        }

        return type;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADER) {
            return LoaderViewHolder.newInstance(parent);
        } else if (viewType == TYPE_ITEM) {
            return ItemViewHolder.newInstance(parent);
        }

        throw new IllegalArgumentException("Invalid ViewType: " + viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).loadData(list.get(position).getTitle(), list.get(position).getPosterPath());
            ((ItemViewHolder) holder).setClickListener(list.get(position).getId());
        } else {
            if (showLoader)
                holder.itemView.setVisibility(View.VISIBLE);
            else
                holder.itemView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() == 0) {
            return 0;
        }
        return list.size() + 1;
    }

    public void setShowLoader(boolean showLoader) {
        this.showLoader = showLoader;
    }
}
