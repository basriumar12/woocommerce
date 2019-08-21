package com.vectorcoder.androidwoocommerce.models.product_filters_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class PostFilters {
    
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
    private List<FilterDetails> selectedAttributes = new ArrayList<>();
    
    
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
    
    public List<FilterDetails> getSelectedAttributes() {
        return selectedAttributes;
    }
    
    public void setSelectedAttributes(List<FilterDetails> selectedAttributes) {
        this.selectedAttributes = selectedAttributes;
    }
    
}
