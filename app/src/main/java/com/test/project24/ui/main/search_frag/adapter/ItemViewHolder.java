package com.test.project24.ui.main.search_frag.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.project24.R;
import com.test.project24.data.network.models.detail.MovieDetail;
import com.test.project24.databinding.MovieItemBinding;
import com.test.project24.ui.base.BaseViewHolder;
import com.test.project24.ui.detail.DetailActivity;

public class ItemViewHolder extends BaseViewHolder {


    private Context context;
    private ItemViewModel viewModel;
    private MovieItemBinding binding;

    public static ItemViewHolder newInstance(ViewGroup root) {

        MovieItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(root.getContext())
                , R.layout.movie_item, root, false);

        return new ItemViewHolder(binding, root.getContext());
    }


    private ItemViewHolder(MovieItemBinding binding, Context context) {
        super(binding);

        this.context = context;
        this.binding = binding;

        initViewHolder(itemView);
    }

    @Override
    public void initViewHolder(View view) {
        viewModel = new ItemViewModel();
        binding.setItemViewHolder(viewModel);
    }

    public void loadData(String title, String image) {
        viewModel.setMovieData(title, image);
    }


    public void setClickListener(MovieDetail movieDetail) {
        itemView.setOnClickListener(v -> DetailActivity.toDetailActivity(context, movieDetail.getId()));
    }


}
