package com.test.project24.ui.main.search_frag.adapter;

import android.databinding.ObservableField;

import com.test.project24.utils.Consts;

public class ItemViewModel {

    private ObservableField<String> title = new ObservableField<>();
    private ObservableField<String> image = new ObservableField<>();
    private ObservableField<String> imageSize = new ObservableField<>();

    public ObservableField<String> getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public ObservableField<String> getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image.set(image);
    }

    public void setImageSize(String image) {
        this.imageSize.set(image);
    }

    public ObservableField<String> getImageSize() {
        return imageSize;
    }

    public void setMovieData(String title, String image) {
        setTitle(title);
        setImage(image);
        setImageSize(Consts.SIZE_POSTER);
    }


}
