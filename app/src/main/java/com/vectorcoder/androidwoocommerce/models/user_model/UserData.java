package com.vectorcoder.androidwoocommerce.models.user_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserData {
    
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cookie")
    @Expose
    private String cookie;
    @SerializedName("user_login")
    @Expose
    private String user_login;
    @SerializedName("user")
    @Expose
    private UserDetails userDetails;
    
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
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
    
    public String getUser_login() {
        return user_login;
    }
    
    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }
    
    public UserDetails getUserDetails() {
        return userDetails;
    }
    
    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
