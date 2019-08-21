package com.vectorcoder.androidwoocommerce.models.cart_model;

import java.util.ArrayList;
import java.util.List;

import com.vectorcoder.androidwoocommerce.models.product_model.ProductAttributes;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductDetails;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductMetaData;


public class CartDetails {
    
    
    private int customersId;
    private int cartID;
    private boolean isProductValidForCoupon;
    
    private ProductDetails cartProduct;
    private List<ProductMetaData> cartProductMetaData = new ArrayList<ProductMetaData>();
    private List<ProductAttributes> cartProductAttributes = new ArrayList<ProductAttributes>();
    
    
    
    public int getCustomersId() {
        return customersId;
    }
    
    public void setCustomersId(int customersId) {
        this.customersId = customersId;
    }
    
    public int getCartID() {
        return cartID;
    }
    
    public void setCartID(int cartID) {
        this.cartID = cartID;
    }
    
    public boolean isProductValidForCoupon() {
        return isProductValidForCoupon;
    }
    
    public void setProductValidForCoupon(boolean productValidForCoupon) {
        isProductValidForCoupon = productValidForCoupon;
    }
    
    public ProductDetails getCartProduct() {
        return cartProduct;
    }
    
    public void setCartProduct(ProductDetails cartProduct) {
        this.cartProduct = cartProduct;
    }
    
    public List<ProductAttributes> getCartProductAttributes() {
        return cartProductAttributes;
    }
    
    public void setCartProductAttributes(List<ProductAttributes> cartProductAttributes) {
        this.cartProductAttributes = cartProductAttributes;
    }
    
    public List<ProductMetaData> getCartProductMetaData() {
        return cartProductMetaData;
    }
    
    public void setCartProductMetaData(List<ProductMetaData> cartProductMetaData) {
        this.cartProductMetaData = cartProductMetaData;
    }
    
}
