package com.vectorcoder.androidwoocommerce.models.order_model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vectorcoder.androidwoocommerce.models.coupons_model.CouponDetails;
import com.vectorcoder.androidwoocommerce.models.user_model.UserBilling;
import com.vectorcoder.androidwoocommerce.models.user_model.UserShipping;


public class OrderDetails {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("order_key")
    @Expose
    private String orderKey;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("discount_total")
    @Expose
    private String discountTotal;
    @SerializedName("discount_tax")
    @Expose
    private String discountTax;
    @SerializedName("shipping_total")
    @Expose
    private String shippingTotal;
    @SerializedName("shipping_tax")
    @Expose
    private String shippingTax;
    @SerializedName("cart_tax")
    @Expose
    private String cartTax;
    @SerializedName("total_tax")
    @Expose
    private String totalTax;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("customer_note")
    @Expose
    private String customerNote;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("payment_method_title")
    @Expose
    private String paymentMethodTitle;
    @SerializedName("set_paid")
    @Expose
    private boolean setPaid;
    @SerializedName("sameAddress")
    @Expose
    private boolean sameAddress;
    @SerializedName("billing")
    @Expose
    private UserBilling billing = null;
    @SerializedName("shipping")
    @Expose
    private UserShipping shipping = null;
    @SerializedName("line_items")
    @Expose
    private List<OrderProducts> orderProducts = new ArrayList<>();
    @SerializedName("coupon_lines")
    @Expose
    private List<CouponDetails> orderCoupons = new ArrayList<>();
    @SerializedName("shipping_lines")
    @Expose
    private List<OrderShippingMethod> orderShippingMethods = new ArrayList<>();
    @SerializedName("tax_lines")
    @Expose
    private List<OrderTaxes> orderTaxesList = new ArrayList<>();

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(String discountTotal) {
        this.discountTotal = discountTotal;
    }

    public String getDiscountTax() {
        return discountTax;
    }

    public void setDiscountTax(String discountTax) {
        this.discountTax = discountTax;
    }

    public String getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(String shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public String getShippingTax() {
        return shippingTax;
    }

    public void setShippingTax(String shippingTax) {
        this.shippingTax = shippingTax;
    }

    public String getCartTax() {
        return cartTax;
    }

    public void setCartTax(String cartTax) {
        this.cartTax = cartTax;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public void setCustomerNote(String customerNote) {
        this.customerNote = customerNote;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethodTitle() {
        return paymentMethodTitle;
    }

    public void setPaymentMethodTitle(String paymentMethodTitle) {
        this.paymentMethodTitle = paymentMethodTitle;
    }

    public boolean isSetPaid() {
        return setPaid;
    }

    public void setSetPaid(boolean setPaid) {
        this.setPaid = setPaid;
    }
    
    public boolean isSameAddress() {
        return sameAddress;
    }
    
    public void setSameAddress(boolean sameAddress) {
        this.sameAddress = sameAddress;
    }
    
    public UserBilling getBilling() {
        return billing;
    }

    public void setBilling(UserBilling billing) {
        this.billing = billing;
    }

    public UserShipping getShipping() {
        return shipping;
    }

    public void setShipping(UserShipping shipping) {
        this.shipping = shipping;
    }

    public List<OrderProducts> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProducts> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public List<CouponDetails> getOrderCoupons() {
        return orderCoupons;
    }

    public void setOrderCoupons(List<CouponDetails> orderCoupons) {
        this.orderCoupons = orderCoupons;
    }

    public List<OrderShippingMethod> getOrderShippingMethods() {
        return orderShippingMethods;
    }

    public void setOrderShippingMethods(List<OrderShippingMethod> orderShippingMethods) {
        this.orderShippingMethods = orderShippingMethods;
    }
    
    public List<OrderTaxes> getOrderTaxesList() {
        return orderTaxesList;
    }
    
    public void setOrderTaxesList(List<OrderTaxes> orderTaxesList) {
        this.orderTaxesList = orderTaxesList;
    }
}
