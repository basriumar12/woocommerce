package com.vectorcoder.androidwoocommerce.models.product_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Filters {
    
    @SerializedName("min_price")
    @Expose
    private String min_price;
    @SerializedName("max_price")
    @Expose
    private String max_price;
    @SerializedName("on_sale")
    @Expose
    private Boolean isSale;
    @SerializedName("featured")
    @Expose
    private Boolean isFeature;
    
    
    public String getMin_price() {
        return min_price;
    }
    
    public void setMin_price(String min_price) {
        this.min_price = min_price;
    }
    
    public String getMax_price() {
        return max_price;
    }
    
    public void setMax_price(String max_price) {
        this.max_price = max_price;
    }
    
    public Boolean getSale() {
        return isSale;
    }
    
    public void setSale(Boolean sale) {
        isSale = sale;
    }
    
    public Boolean getFeature() {
        return isFeature;
    }
    
    public void setFeature(Boolean feature) {
        isFeature = feature;
    }
    
}
