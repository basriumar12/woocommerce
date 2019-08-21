package com.vectorcoder.androidwoocommerce.models.product_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vectorcoder.androidwoocommerce.models.links_model.Links;

/**
 * Created by muneeb.vectorcoder@gmail.com on 07/05/2018.
 */

public class ProductReviews {
    
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_created_gmt")
    @Expose
    private String dateCreatedGmt;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("rating")
    @Expose
    private int rating;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("verified")
    @Expose
    private boolean verified;
    @SerializedName("_links")
    @Expose
    private Links links;
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
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
    
    public String getReview() {
        return review;
    }
    
    public void setReview(String review) {
        this.review = review;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean isVerified() {
        return verified;
    }
    
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
    
    public Links getLinks() {
        return links;
    }
    
    public void setLinks(Links links) {
        this.links = links;
    }
    
}
