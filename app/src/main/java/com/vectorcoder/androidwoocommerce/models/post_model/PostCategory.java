package com.vectorcoder.androidwoocommerce.models.post_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vectorcoder.androidwoocommerce.models.links_model.Links;

/**
 * Created by muneeb.vectorcoder@gmail.com on 28/03/2018.
 */

public class PostCategory {
    
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("taxonomy")
    @Expose
    private String taxonomy;
    @SerializedName("parent")
    @Expose
    private int parent;
    @SerializedName("_links")
    @Expose
    private Links links;
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLink() {
        return link;
    }
    
    public void setLink(String link) {
        this.link = link;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSlug() {
        return slug;
    }
    
    public void setSlug(String slug) {
        this.slug = slug;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getTaxonomy() {
        return taxonomy;
    }
    
    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }
    
    public int getParent() {
        return parent;
    }
    
    public void setParent(int parent) {
        this.parent = parent;
    }
    
    
    public Links getLinks() {
        return links;
    }
    
    public void setLinks(Links links) {
        this.links = links;
    }
    
}
