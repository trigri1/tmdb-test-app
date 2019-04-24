package com.test.project24.ui.base;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * @author goharali
 */

public abstract class BaseDiffCallBack<T> extends DiffUtil.Callback {

    public List<T> oldList;
    public List<T> newList;

    public BaseDiffCallBack(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }


}
