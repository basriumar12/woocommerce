
package com.vectorcoder.androidwoocommerce.models.download;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Links {

    @SerializedName("collection")
    @Expose
    private List<Collection> collection = null;
    @SerializedName("product")
    @Expose
    private List<Product> product = null;
    @SerializedName("order")
    @Expose
    private List<Order> order = null;

    public List<Collection> getCollection() {
        return collection;
    }

    public void setCollection(List<Collection> collection) {
        this.collection = collection;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

}
