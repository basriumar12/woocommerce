package com.vectorcoder.androidwoocommerce.models.banner_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class BannerDetails {
    
    @SerializedName("banners_id")
    @Expose
    private String bannersId;
    @SerializedName("banners_title")
    @Expose
    private String bannersTitle;
    @SerializedName("banners_url")
    @Expose
    private String bannersUrl;
    @SerializedName("banners_image")
    @Expose
    private String bannersImage;
    @SerializedName("banners_group")
    @Expose
    private String bannersGroup;
    @SerializedName("banners_html_text")
    @Expose
    private String bannersHtmlText;
    @SerializedName("expires_impressions")
    @Expose
    private String expiresImpressions;
    @SerializedName("expires_date")
    @Expose
    private String expiresDate;
    @SerializedName("date_scheduled")
    @Expose
    private String dateScheduled;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("date_status_change")
    @Expose
    private String dateStatusChange;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    
    
    public String getBannersId() {
        return bannersId;
    }
    
    public void setBannersId(String bannersId) {
        this.bannersId = bannersId;
    }
    
    public String getBannersTitle() {
        return bannersTitle;
    }
    
    public void setBannersTitle(String bannersTitle) {
        this.bannersTitle = bannersTitle;
    }
    
    public String getBannersUrl() {
        return bannersUrl;
    }
    
    public void setBannersUrl(String bannersUrl) {
        this.bannersUrl = bannersUrl;
    }
    
    public String getBannersImage() {
        return bannersImage;
    }
    
    public void setBannersImage(String bannersImage) {
        this.bannersImage = bannersImage;
    }
    
    public String getBannersGroup() {
        return bannersGroup;
    }
    
    public void setBannersGroup(String bannersGroup) {
        this.bannersGroup = bannersGroup;
    }
    
    public String getBannersHtmlText() {
        return bannersHtmlText;
    }
    
    public void setBannersHtmlText(String bannersHtmlText) {
        this.bannersHtmlText = bannersHtmlText;
    }
    
    public String getExpiresImpressions() {
        return expiresImpressions;
    }
    
    public void setExpiresImpressions(String expiresImpressions) {
        this.expiresImpressions = expiresImpressions;
    }
    
    public String getExpiresDate() {
        return expiresDate;
    }
    
    public void setExpiresDate(String expiresDate) {
        this.expiresDate = expiresDate;
    }
    
    public String getDateScheduled() {
        return dateScheduled;
    }
    
    public void setDateScheduled(String dateScheduled) {
        this.dateScheduled = dateScheduled;
    }
    
    public String getDateAdded() {
        return dateAdded;
    }
    
    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
    
    public String getDateStatusChange() {
        return dateStatusChange;
    }
    
    public void setDateStatusChange(String dateStatusChange) {
        this.dateStatusChange = dateStatusChange;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

}
