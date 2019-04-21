package com.test.project24.ui.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Gohar Ali on 18/01/2018.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {


    private ViewDataBinding mBinding;

    public BaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }


    public ViewDataBinding getDataBinding() {
        return mBinding;
    }

    public abstract void initViewHolder(View view);


//    public void setDataBinding(Object obj) {
//        mBinding.setVariable(BR.viewModel, obj);
//        mBinding.executePendingBindings();
//    }
}
