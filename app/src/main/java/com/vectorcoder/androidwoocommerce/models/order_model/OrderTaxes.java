package com.vectorcoder.androidwoocommerce.models.order_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderTaxes {
    
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("rate_code")
    @Expose
    private String rateCode;
    @SerializedName("rate_id")
    @Expose
    private int rateId;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("compound")
    @Expose
    private boolean compound;
    @SerializedName("tax_total")
    @Expose
    private String taxTotal;
    @SerializedName("shipping_tax_total")
    @Expose
    private String shippingTaxTotal;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getRateCode() {
        return rateCode;
    }
    
    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }
    
    public int getRateId() {
        return rateId;
    }
    
    public void setRateId(int rateId) {
        this.rateId = rateId;
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public boolean isCompound() {
        return compound;
    }
    
    public void setCompound(boolean compound) {
        this.compound = compound;
    }
    
    public String getTaxTotal() {
        return taxTotal;
    }
    
    public void setTaxTotal(String taxTotal) {
        this.taxTotal = taxTotal;
    }
    
    public String getShippingTaxTotal() {
        return shippingTaxTotal;
    }
    
    public void setShippingTaxTotal(String shippingTaxTotal) {
        this.shippingTaxTotal = shippingTaxTotal;
    }

}
