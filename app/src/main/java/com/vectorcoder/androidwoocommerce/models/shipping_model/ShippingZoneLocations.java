package com.vectorcoder.androidwoocommerce.models.shipping_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vectorcoder.androidwoocommerce.models.links_model.Links;


public class ShippingZoneLocations {
    
    @SerializedName("zoneID")
    @Expose
    private int zoneID;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("_links")
    @Expose
    private Links links;
    
    
    public int getZoneID() {
        return zoneID;
    }
    
    public void setZoneID(int zoneID) {
        this.zoneID = zoneID;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Links getLinks() {
        return links;
    }
    
    public void setLinks(Links links) {
        this.links = links;
    }

}
