package com.vectorcoder.androidwoocommerce.models.links_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Links {
    
    @SerializedName("self")
    @Expose
    private List<Links_Self> self = null;
    @SerializedName("collection")
    @Expose
    private List<Links_Collection> collection = null;
    @SerializedName("up")
    @Expose
    private List<Links_Up> up = null;
    
    
    public List<Links_Self> getSelf() {
        return self;
    }
    
    public void setSelf(List<Links_Self> self) {
        this.self = self;
    }
    
    public List<Links_Collection> getCollection() {
        return collection;
    }
    
    public void setCollection(List<Links_Collection> collection) {
        this.collection = collection;
    }
    
    public List<Links_Up> getUp() {
        return up;
    }
    
    public void setUp(List<Links_Up> up) {
        this.up = up;
    }
}
