package com.vectorcoder.androidwoocommerce.models.post_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by muneeb.vectorcoder@gmail.com on 30/03/2018.
 */

public class PostMedia {
    
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("post_id")
    @Expose
    private int postID;
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
    @SerializedName("author")
    @Expose
    private int author;
    @SerializedName("comment_status")
    @Expose
    private String commentStatus;
    @SerializedName("ping_status")
    @Expose
    private String pingStatus;
    @SerializedName("template")
    @Expose
    private String template;
    @SerializedName("alt_text")
    @Expose
    private String altText;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("mime_type")
    @Expose
    private String mimeType;
    @SerializedName("post")
    @Expose
    private int post;
    @SerializedName("source_url")
    @Expose
    private String sourceUrl;
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getPostID() {
        return postID;
    }
    
    public void setPostID(int postID) {
        this.postID = postID;
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
    
    public int getAuthor() {
        return author;
    }
    
    public void setAuthor(int author) {
        this.author = author;
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
    
    public String getTemplate() {
        return template;
    }
    
    public void setTemplate(String template) {
        this.template = template;
    }
    
    public String getAltText() {
        return altText;
    }
    
    public void setAltText(String altText) {
        this.altText = altText;
    }
    
    public String getMediaType() {
        return mediaType;
    }
    
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
    
    public String getMimeType() {
        return mimeType;
    }
    
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    
    public int getPost() {
        return post;
    }
    
    public void setPost(int post) {
        this.post = post;
    }
    
    public String getSourceUrl() {
        return sourceUrl;
    }
    
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
    
}
