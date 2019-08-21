package com.vectorcoder.androidwoocommerce.models.post_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PostEmbedded {
    
    @SerializedName("author")
    @Expose
    private List<Object> author = null;
    @SerializedName("wp:featuredmedia")
    @Expose
    private List<PostMedia> wpFeaturedmedia = null;
    @SerializedName("wp:term")
    @Expose
    private List<List<Object>> wpTerm = null;
    
    
    public List<Object> getAuthor() {
        return author;
    }
    
    public void setAuthor(List<Object> author) {
        this.author = author;
    }
    
    public List<PostMedia> getWpFeaturedmedia() {
        return wpFeaturedmedia;
    }
    
    public void setWpFeaturedmedia(List<PostMedia> wpFeaturedmedia) {
        this.wpFeaturedmedia = wpFeaturedmedia;
    }
    
    public List<List<Object>> getWpTerm() {
        return wpTerm;
    }
    
    public void setWpTerm(List<List<Object>> wpTerm) {
        this.wpTerm = wpTerm;
    }
    
}
