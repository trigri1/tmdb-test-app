package com.test.project24.ui.main.changes_frag.adapter;

import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.ui.base.BaseDiffCallBack;

import java.util.List;

/**
 * Created by Gohar Ali on 24/02/2018.
 */

public class ChangesDiffCallBack extends BaseDiffCallBack<MovieDetail> {


    public ChangesDiffCallBack(List<MovieDetail> oldList, List<MovieDetail> newList) {
        super(oldList, newList);
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        MovieDetail oldItem = oldList.get(oldItemPosition);
        MovieDetail newItem = newList.get(newItemPosition);
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        MovieDetail oldItem = oldList.get(oldItemPosition);
        MovieDetail newItem = newList.get(newItemPosition);

        return oldItem.equals(newItem);
    }
    
}
