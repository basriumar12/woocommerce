package com.vectorcoder.androidwoocommerce.models.product_filters_model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductFilters {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("min_price")
    @Expose
    private String minPrice;
    @SerializedName("max_price")
    @Expose
    private String maxPrice;
    @SerializedName("on_sale")
    @Expose
    private String onSale;
    @SerializedName("featured")
    @Expose
    private String featured;
    @SerializedName("attributes")
    @Expose
    private List<FilterDetails> attributes = new ArrayList<>();
    
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getMinPrice() {
        return minPrice;
    }
    
    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }
    
    public String getMaxPrice() {
        return maxPrice;
    }
    
    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }
    
    public String getOnSale() {
        return onSale;
    }
    
    public void setOnSale(String onSale) {
        this.onSale = onSale;
    }
    
    public String getFeatured() {
        return featured;
    }
    
    public void setFeatured(String featured) {
        this.featured = featured;
    }
    
    public List<FilterDetails> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(List<FilterDetails> attributes) {
        this.attributes = attributes;
    }
    
}
