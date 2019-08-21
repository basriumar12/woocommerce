package com.vectorcoder.androidwoocommerce.models.shipping_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by muneeb.vectorcoder@gmail.com on 16/04/2018.
 */

public class ShippingMethodSettings {
    
    @SerializedName("title")
    @Expose
    private ShippingMethodSettingsTitle title;
    @SerializedName("type")
    @Expose
    private ShippingMethodSettingsType type;
    @SerializedName("cost")
    @Expose
    private ShippingMethodSettingsCost cost;
    @SerializedName("min_amount")
    @Expose
    private ShippingMethodSettingsCost min_amount;
    
    
    public ShippingMethodSettingsTitle getTitle() {
        return title;
    }
    
    public void setTitle(ShippingMethodSettingsTitle title) {
        this.title = title;
    }
    
    public ShippingMethodSettingsCost getCost() {
        return cost;
    }
    
    public void setCost(ShippingMethodSettingsCost cost) {
        this.cost = cost;
    }
    
    public ShippingMethodSettingsCost getMin_amount() {
        return min_amount;
    }
    
    public void setMin_amount(ShippingMethodSettingsCost min_amount) {
        this.min_amount = min_amount;
    }
    
    public ShippingMethodSettingsType getType() {
        return type;
    }
    
    public void setType(ShippingMethodSettingsType type) {
        this.type = type;
    }
    
}
