package com.vectorcoder.androidwoocommerce.models.shipping_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vectorcoder.androidwoocommerce.models.links_model.Links;


public class ShippingZone {
    
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("_links")
    @Expose
    private Links links;
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getOrder() {
        return order;
    }
    
    public void setOrder(int order) {
        this.order = order;
    }
    
    public Links getLinks() {
        return links;
    }
    
    public void setLinks(Links links) {
        this.links = links;
    }

}
