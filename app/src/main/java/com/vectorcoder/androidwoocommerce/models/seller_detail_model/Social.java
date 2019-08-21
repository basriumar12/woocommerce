
package com.vectorcoder.androidwoocommerce.models.seller_detail_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Social {

    @SerializedName("fb")
    @Expose
    private String fb;
    @SerializedName("gplus")
    @Expose
    private Boolean gplus;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("pinterest")
    @Expose
    private Boolean pinterest;
    @SerializedName("linkedin")
    @Expose
    private Boolean linkedin;
    @SerializedName("youtube")
    @Expose
    private Boolean youtube;
    @SerializedName("instagram")
    @Expose
    private Boolean instagram;
    @SerializedName("flickr")
    @Expose
    private Boolean flickr;

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public Boolean getGplus() {
        return gplus;
    }

    public void setGplus(Boolean gplus) {
        this.gplus = gplus;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public Boolean getPinterest() {
        return pinterest;
    }

    public void setPinterest(Boolean pinterest) {
        this.pinterest = pinterest;
    }

    public Boolean getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(Boolean linkedin) {
        this.linkedin = linkedin;
    }

    public Boolean getYoutube() {
        return youtube;
    }

    public void setYoutube(Boolean youtube) {
        this.youtube = youtube;
    }

    public Boolean getInstagram() {
        return instagram;
    }

    public void setInstagram(Boolean instagram) {
        this.instagram = instagram;
    }

    public Boolean getFlickr() {
        return flickr;
    }

    public void setFlickr(Boolean flickr) {
        this.flickr = flickr;
    }

}
