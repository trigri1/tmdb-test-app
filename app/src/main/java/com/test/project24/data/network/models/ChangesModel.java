package com.test.project24.data.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.test.project24.data.network.models.detail.MovieDetail;

import java.util.List;

public class ChangesModel {

    @SerializedName("results")
    @Expose
    private List<MovieDetail> results;

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("total_pages")
    @Expose
    private int total_pages;

    @SerializedName("total_results")
    @Expose
    private int total_results;

    public ChangesModel() {
    }

    public List<MovieDetail> getResults() {
        return results;
    }

    public void setResults(List<MovieDetail> results) {
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }
}
