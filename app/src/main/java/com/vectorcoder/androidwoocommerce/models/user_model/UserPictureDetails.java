package com.vectorcoder.androidwoocommerce.models.user_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by muneeb.vectorcoder@gmail.com on 09/04/2018.
 */

public class UserPictureDetails {
    
    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("is_silhouette")
    @Expose
    private boolean isSilhouette;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("width")
    @Expose
    private int width;
    
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public boolean isIsSilhouette() {
        return isSilhouette;
    }
    
    public void setIsSilhouette(boolean isSilhouette) {
        this.isSilhouette = isSilhouette;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
}
