
package com.vectorcoder.androidwoocommerce.models.download;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadsModel {

    @SerializedName("download_id")
    @Expose
    private String downloadId;
    @SerializedName("download_url")
    @Expose
    private String downloadUrl;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("download_name")
    @Expose
    private String downloadName;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("order_key")
    @Expose
    private String orderKey;
    @SerializedName("downloads_remaining")
    @Expose
    private String downloadsRemaining;
    @SerializedName("access_expires")
    @Expose
    private String accessExpires;
    @SerializedName("access_expires_gmt")
    @Expose
    private String accessExpiresGmt;
    @SerializedName("file")
    @Expose
    private File file;
    @SerializedName("_links")
    @Expose
    private Links links;

    public String getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(String downloadId) {
        this.downloadId = downloadId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getDownloadsRemaining() {
        return downloadsRemaining;
    }

    public void setDownloadsRemaining(String downloadsRemaining) {
        this.downloadsRemaining = downloadsRemaining;
    }

    public String getAccessExpires() {
        return accessExpires;
    }

    public void setAccessExpires(String accessExpires) {
        this.accessExpires = accessExpires;
    }

    public String getAccessExpiresGmt() {
        return accessExpiresGmt;
    }

    public void setAccessExpiresGmt(String accessExpiresGmt) {
        this.accessExpiresGmt = accessExpiresGmt;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
