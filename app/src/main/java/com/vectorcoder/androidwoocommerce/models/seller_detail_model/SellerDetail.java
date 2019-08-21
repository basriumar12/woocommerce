
package com.vectorcoder.androidwoocommerce.models.seller_detail_model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SellerDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("social")
    @Expose
    private List<Object> social = null;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("show_email")
    @Expose
    private Boolean showEmail;
    @SerializedName("address")
    @Expose
    private List<Object> address = null;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("banner")
    @Expose
    private Boolean banner;
    @SerializedName("gravatar")
    @Expose
    private String gravatar;
    @SerializedName("shop_url")
    @Expose
    private String shopUrl;
    @SerializedName("products_per_page")
    @Expose
    private Integer productsPerPage;
    @SerializedName("show_more_product_tab")
    @Expose
    private Boolean showMoreProductTab;
    @SerializedName("toc_enabled")
    @Expose
    private Boolean tocEnabled;
    @SerializedName("store_toc")
    @Expose
    private String storeToc;
    @SerializedName("featured")
    @Expose
    private Boolean featured;
    @SerializedName("rating")
    @Expose
    private Rating rating;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("registered")
    @Expose
    private String registered;
    @SerializedName("payment")
    @Expose
    private Payment payment;
    @SerializedName("trusted")
    @Expose
    private Boolean trusted;
    @SerializedName("_links")
    @Expose
    private Links links;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Object> getSocial() {
        return social;
    }

    public void setSocial(List<Object> social) {
        this.social = social;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getShowEmail() {
        return showEmail;
    }

    public void setShowEmail(Boolean showEmail) {
        this.showEmail = showEmail;
    }

    public List<Object> getAddress() {
        return address;
    }

    public void setAddress(List<Object> address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getBanner() {
        return banner;
    }

    public void setBanner(Boolean banner) {
        this.banner = banner;
    }

    public String getGravatar() {
        return gravatar;
    }

    public void setGravatar(String gravatar) {
        this.gravatar = gravatar;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public Integer getProductsPerPage() {
        return productsPerPage;
    }

    public void setProductsPerPage(Integer productsPerPage) {
        this.productsPerPage = productsPerPage;
    }

    public Boolean getShowMoreProductTab() {
        return showMoreProductTab;
    }

    public void setShowMoreProductTab(Boolean showMoreProductTab) {
        this.showMoreProductTab = showMoreProductTab;
    }

    public Boolean getTocEnabled() {
        return tocEnabled;
    }

    public void setTocEnabled(Boolean tocEnabled) {
        this.tocEnabled = tocEnabled;
    }

    public String getStoreToc() {
        return storeToc;
    }

    public void setStoreToc(String storeToc) {
        this.storeToc = storeToc;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Boolean getTrusted() {
        return trusted;
    }

    public void setTrusted(Boolean trusted) {
        this.trusted = trusted;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
