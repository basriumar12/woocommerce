package com.vectorcoder.androidwoocommerce.models.user_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vectorcoder.androidwoocommerce.models.links_model.Links;


public class UserDetails {
    
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cookie")
    @Expose
    private String cookie;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("nicename")
    @Expose
    private String nice_name;
    @SerializedName("nickname")
    @Expose
    private String nick_name;
    @SerializedName("display_name")
    @Expose
    private String display_name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_created_gmt")
    @Expose
    private String dateCreatedGmt;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("date_modified_gmt")
    @Expose
    private String dateModifiedGmt;
    @SerializedName("billing")
    @Expose
    private UserBilling billing;
    @SerializedName("shipping")
    @Expose
    private UserShipping shipping;
    @SerializedName("is_paying_customer")
    @Expose
    private Boolean isPayingCustomer;
    @SerializedName("orders_count")
    @Expose
    private int ordersCount;
    @SerializedName("total_spent")
    @Expose
    private String totalSpent;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("user_picture")
    @Expose
    private String userPicture;
    @SerializedName("picture")
    @Expose
    private UserPicture picture;
    @SerializedName("_links")
    @Expose
    private Links links;
    
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getCookie() {
        return cookie;
    }
    
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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
    
    public String getNice_name() {
        return nice_name;
    }
    
    public void setNice_name(String nice_name) {
        this.nice_name = nice_name;
    }
    
    public String getNick_name() {
        return nick_name;
    }
    
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }
    
    public String getDisplay_name() {
        return display_name;
    }
    
    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public String getDateCreatedGmt() {
        return dateCreatedGmt;
    }
    
    public void setDateCreatedGmt(String dateCreatedGmt) {
        this.dateCreatedGmt = dateCreatedGmt;
    }
    
    public String getDateModified() {
        return dateModified;
    }
    
    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
    
    public String getDateModifiedGmt() {
        return dateModifiedGmt;
    }
    
    public void setDateModifiedGmt(String dateModifiedGmt) {
        this.dateModifiedGmt = dateModifiedGmt;
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
    
    public Boolean getPayingCustomer() {
        return isPayingCustomer;
    }
    
    public void setPayingCustomer(Boolean payingCustomer) {
        isPayingCustomer = payingCustomer;
    }
    
    public int getOrdersCount() {
        return ordersCount;
    }
    
    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }
    
    public String getTotalSpent() {
        return totalSpent;
    }
    
    public void setTotalSpent(String totalSpent) {
        this.totalSpent = totalSpent;
    }
    
    public String getAvatarUrl() {
        return avatarUrl;
    }
    
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    
    public String getUserPicture() {
        return userPicture;
    }
    
    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }
    
    public UserPicture getPicture() {
        return picture;
    }
    
    public void setPicture(UserPicture picture) {
        this.picture = picture;
    }
    
    public Links getLinks() {
        return links;
    }
    
    public void setLinks(Links links) {
        this.links = links;
    }
    
}
