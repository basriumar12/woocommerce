package com.vectorcoder.androidwoocommerce.models.shipping_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vectorcoder.androidwoocommerce.models.links_model.Links;


public class ShippingMethods {
    
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("instance_id")
    @Expose
    private int instanceId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("enabled")
    @Expose
    private boolean enabled;
    @SerializedName("method_id")
    @Expose
    private String methodId;
    @SerializedName("method_title")
    @Expose
    private String methodTitle;
    @SerializedName("method_description")
    @Expose
    private String methodDescription;
    @SerializedName("settings")
    @Expose
    private ShippingMethodSettings settings;
    @SerializedName("_links")
    @Expose
    private Links links;
    
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public int getInstanceId() {
        return instanceId;
    }
    
    public void setInstanceId(int instanceId) {
        this.instanceId = instanceId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getCost() {
        return cost;
    }
    
    public void setCost(String cost) {
        this.cost = cost;
    }
    
    public int getOrder() {
        return order;
    }
    
    public void setOrder(int order) {
        this.order = order;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getMethodId() {
        return methodId;
    }
    
    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }
    
    public String getMethodTitle() {
        return methodTitle;
    }
    
    public void setMethodTitle(String methodTitle) {
        this.methodTitle = methodTitle;
    }
    
    public String getMethodDescription() {
        return methodDescription;
    }
    
    public void setMethodDescription(String methodDescription) {
        this.methodDescription = methodDescription;
    }
    
    public ShippingMethodSettings getSettings() {
        return settings;
    }
    
    public void setSettings(ShippingMethodSettings settings) {
        this.settings = settings;
    }
    
    public Links getLinks() {
        return links;
    }
    
    public void setLinks(Links links) {
        this.links = links;
    }

}
