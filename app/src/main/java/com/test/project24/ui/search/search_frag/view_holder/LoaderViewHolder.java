package com.test.project24.ui.search.search_frag.view_holder;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.project24.R;
import com.test.project24.databinding.ProgressLoaderBinding;
import com.test.project24.ui.base.BaseViewHolder;

/**
 * @author Gohar Ali
 */

public class LoaderViewHolder extends BaseViewHolder {


    private ProgressLoaderBinding binding;
    private LoaderViewModel viewModel;

    public static LoaderViewHolder newInstance(ViewGroup root) {

        ProgressLoaderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(root.getContext())
                , R.layout.progress_loader, root, false);

        return new LoaderViewHolder(binding);
    }


    private LoaderViewHolder(ProgressLoaderBinding binding) {
        super(binding);
        this.binding = binding;

        initViewHolder(itemView);
    }

    @Override
    public void initViewHolder(View view) {
        viewModel = new LoaderViewModel();
        binding.setItemViewHolder(viewModel);
    }


    public void showLoader(boolean show) {
        viewModel.showLoader.set(show);
    }
}
