
package com.vectorcoder.androidwoocommerce.models.order_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderMetaData {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
