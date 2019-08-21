
package com.vectorcoder.androidwoocommerce.models.order_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderShippingMethod {
    
    @SerializedName("ship_id")
    @Expose
    private String shippingID;
    @SerializedName("method_id")
    @Expose
    private String methodId;
    @SerializedName("method_title")
    @Expose
    private String methodTitle;
    @SerializedName("total")
    @Expose
    private String total;
    
    
    public String getShippingID() {
        return shippingID;
    }
    
    public void setShippingID(String shippingID) {
        this.shippingID = shippingID;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
