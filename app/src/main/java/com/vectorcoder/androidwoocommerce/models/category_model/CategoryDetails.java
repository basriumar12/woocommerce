package com.vectorcoder.androidwoocommerce.models.category_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vectorcoder.androidwoocommerce.models.links_model.Links;


public class CategoryDetails {
    
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("parent")
    @Expose
    private int parent;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("display")
    @Expose
    private String display;
    @SerializedName("image")
    @Expose
    private CategoryImage image;
    @SerializedName("menu_order")
    @Expose
    private int menuOrder;
    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("sub_categories")
    @Expose
    private int sub_categories;
    @SerializedName("_links")
    @Expose
    private Links links;
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
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
    
    public int getParent() {
        return parent;
    }
    
    public void setParent(int parent) {
        this.parent = parent;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDisplay() {
        return display;
    }
    
    public void setDisplay(String display) {
        this.display = display;
    }
    
    public CategoryImage getImage() {
        return image;
    }
    
    public void setImage(CategoryImage image) {
        this.image = image;
    }
    
    public int getMenuOrder() {
        return menuOrder;
    }
    
    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    public int getSub_categories() {
        return sub_categories;
    }
    
    public void setSub_categories(int sub_categories) {
        this.sub_categories = sub_categories;
    }
    
    public Links getLinks() {
        return links;
    }
    
    public void setLinks(Links links) {
        this.links = links;
    }
    
}
