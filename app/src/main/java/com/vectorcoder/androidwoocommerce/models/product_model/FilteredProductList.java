package com.vectorcoder.androidwoocommerce.models.product_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class FilteredProductList {
    
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Integer> data = new ArrayList<>();
    
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public List<Integer> getData() {
        return data;
    }
    
    public void setData(List<Integer> data) {
        this.data = data;
    }
    
}
