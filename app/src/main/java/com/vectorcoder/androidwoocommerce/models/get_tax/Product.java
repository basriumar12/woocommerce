
package com.vectorcoder.androidwoocommerce.models.get_tax;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("total")
    @Expose
    private String total;
    
    @SerializedName("variation_id")
    @Expose
    private String variation_id;
    
    public String getVariation_id() {
        return variation_id;
    }
    
    public void setVariation_id(String variation_id) {
        this.variation_id = variation_id;
    }
    

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
