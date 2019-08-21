package com.vectorcoder.androidwoocommerce.models.post_model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PostDetails {
    
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("modified_gmt")
    @Expose
    private String modifiedGmt;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("title")
    @Expose
    private PostTitle title;
    @SerializedName("content")
    @Expose
    private PostContent content;
    @SerializedName("author")
    @Expose
    private int author;
    @SerializedName("featured_media")
    @Expose
    private int featuredMedia;
    @SerializedName("comment_status")
    @Expose
    private String commentStatus;
    @SerializedName("ping_status")
    @Expose
    private String pingStatus;
    @SerializedName("sticky")
    @Expose
    private boolean sticky;
    @SerializedName("template")
    @Expose
    private String template;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("categories")
    @Expose
    private List<Integer> categories = null;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("_embedded")
    @Expose
    private PostEmbedded embedded = null;
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getDateGmt() {
        return dateGmt;
    }
    
    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }
    
    public String getModified() {
        return modified;
    }
    
    public void setModified(String modified) {
        this.modified = modified;
    }
    
    public String getModifiedGmt() {
        return modifiedGmt;
    }
    
    public void setModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
    }
    
    public String getSlug() {
        return slug;
    }
    
    public void setSlug(String slug) {
        this.slug = slug;
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
    
    public String getLink() {
        return link;
    }
    
    public void setLink(String link) {
        this.link = link;
    }
    
    public PostTitle getTitle() {
        return title;
    }
    
    public void setTitle(PostTitle title) {
        this.title = title;
    }
    
    public PostContent getContent() {
        return content;
    }
    
    public void setContent(PostContent content) {
        this.content = content;
    }
    
    public int getAuthor() {
        return author;
    }
    
    public void setAuthor(int author) {
        this.author = author;
    }
    
    public int getFeaturedMedia() {
        return featuredMedia;
    }
    
    public void setFeaturedMedia(int featuredMedia) {
        this.featuredMedia = featuredMedia;
    }
    
    public String getCommentStatus() {
        return commentStatus;
    }
    
    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }
    
    public String getPingStatus() {
        return pingStatus;
    }
    
    public void setPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
    }
    
    public boolean isSticky() {
        return sticky;
    }
    
    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }
    
    public String getTemplate() {
        return template;
    }
    
    public void setTemplate(String template) {
        this.template = template;
    }
    
    public String getFormat() {
        return format;
    }
    
    public void setFormat(String format) {
        this.format = format;
    }
    
    public List<Integer> getCategories() {
        return categories;
    }
    
    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
    public PostEmbedded getEmbedded() {
        return embedded;
    }
    
    public void setEmbedded(PostEmbedded embedded) {
        this.embedded = embedded;
    }

}
