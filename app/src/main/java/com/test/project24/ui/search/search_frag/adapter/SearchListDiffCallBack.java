package com.test.project24.ui.search.search_frag.adapter;

import com.test.project24.data.network.models.MovieModel;
import com.test.project24.ui.base.BaseDiffCallBack;

import java.util.List;

/**
 * @author goharali
 */

public class SearchListDiffCallBack extends BaseDiffCallBack<MovieModel> {


    public SearchListDiffCallBack(List<MovieModel> oldList, List<MovieModel> newList) {
        super(oldList, newList);
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        MovieModel oldItem = oldList.get(oldItemPosition);
        MovieModel newItem = newList.get(newItemPosition);
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        MovieModel oldItem = oldList.get(oldItemPosition);
        MovieModel newItem = newList.get(newItemPosition);

        return oldItem.getId() == newItem.getId()
                && equals(oldItem.getOriginalTitle(), newItem.getOriginalTitle())
                && equals(oldItem.getTitle(), newItem.getTitle());
    }

    private boolean equals(String a, String b) {

        if (a == null && b == null)
            return true;
        else if (a == null) {
            return false;
        } else if (b == null) {
            return false;
        }

        return a.equals(b);
    }
}
