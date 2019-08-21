
package com.vectorcoder.androidwoocommerce.models.seller_detail_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payment {

    @SerializedName("paypal")
    @Expose
    private Paypal paypal;

    public Paypal getPaypal() {
        return paypal;
    }

    public void setPaypal(Paypal paypal) {
        this.paypal = paypal;
    }

}
