package com.vectorcoder.androidwoocommerce.models.product_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vectorcoder.androidwoocommerce.models.links_model.Links;


public class ProductDetails implements Parcelable {
    
    
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_created_gmt")
    @Expose
    private String dateCreatedGmt;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("date_modified_gmt")
    @Expose
    private String dateModifiedGmt;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("featured")
    @Expose
    private boolean featured;
    @SerializedName("catalog_visibility")
    @Expose
    private String catalogVisibility;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("regular_price")
    @Expose
    private String regularPrice;
    @SerializedName("sale_price")
    @Expose
    private String salePrice;
    @SerializedName("date_on_sale_from")
    @Expose
    private String dateOnSaleFrom;
    @SerializedName("date_on_sale_from_gmt")
    @Expose
    private String dateOnSaleFromGmt;
    @SerializedName("date_on_sale_to")
    @Expose
    private String dateOnSaleTo;
    @SerializedName("date_on_sale_to_gmt")
    @Expose
    private String dateOnSaleToGmt;
    @SerializedName("price_html")
    @Expose
    private String priceHtml;
    @SerializedName("on_sale")
    @Expose
    private boolean onSale;
    @SerializedName("purchasable")
    @Expose
    private boolean purchasable;
    @SerializedName("total_sales")
    @Expose
    private int totalSales;
    @SerializedName("virtual")
    @Expose
    private boolean virtual;
    @SerializedName("downloadable")
    @Expose
    private boolean downloadable;
    @SerializedName("downloads")
    @Expose
    private List<ProductDownloads> downloads = new ArrayList<>();
    @SerializedName("download_limit")
    @Expose
    private int downloadLimit;
    @SerializedName("download_expiry")
    @Expose
    private int downloadExpiry;
    @SerializedName("external_url")
    @Expose
    private String externalUrl;
    @SerializedName("button_text")
    @Expose
    private String buttonText;
    @SerializedName("tax_status")
    @Expose
    private String taxStatus;
    @SerializedName("tax_class")
    @Expose
    private String taxClass;
    @SerializedName("manage_stock")
    @Expose
    private boolean manageStock;
    @SerializedName("stock_quantity")
    @Expose
    private String stockQuantity;
    @SerializedName("in_stock")
    @Expose
    private boolean inStock;
    @SerializedName("backorders")
    @Expose
    private String backorders;
    @SerializedName("backorders_allowed")
    @Expose
    private boolean backordersAllowed;
    @SerializedName("backordered")
    @Expose
    private boolean backordered;
    @SerializedName("sold_individually")
    @Expose
    private boolean soldIndividually;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("dimensions")
    @Expose
    private ProductDimensions dimensions;
    @SerializedName("shipping_required")
    @Expose
    private boolean shippingRequired;
    @SerializedName("shipping_taxable")
    @Expose
    private boolean shippingTaxable;
    @SerializedName("shipping_class")
    @Expose
    private String shippingClass;
    @SerializedName("shipping_class_id")
    @Expose
    private int shippingClassId;
    @SerializedName("reviews_allowed")
    @Expose
    private boolean reviewsAllowed;
    @SerializedName("average_rating")
    @Expose
    private String averageRating;
    @SerializedName("rating_count")
    @Expose
    private int ratingCount;
    @SerializedName("related_ids")
    @Expose
    private List<Integer> relatedIds = new ArrayList<>();
    @SerializedName("upsell_ids")
    @Expose
    private List<Integer> upsellIds = new ArrayList<>();
    @SerializedName("cross_sell_ids")
    @Expose
    private List<Integer> crossSellIds = new ArrayList<>();
    @SerializedName("parent_id")
    @Expose
    private int parentId;
    @SerializedName("purchase_note")
    @Expose
    private String purchaseNote;
    @SerializedName("categories")
    @Expose
    private List<ProductCategories> categories = new ArrayList<>();
    @SerializedName("tags")
    @Expose
    private List<ProductTags> tags = new ArrayList<>();
    @SerializedName("images")
    @Expose
    private List<ProductImages> images = new ArrayList<>();
    @SerializedName("attributes")
    @Expose
    private List<ProductAttributes> attributes = new ArrayList<>();
    @SerializedName("default_attributes")
    @Expose
    private List<ProductDefaultAttributes> defaultAttributes = new ArrayList<>();
    @SerializedName("variations")
    @Expose
    private List<Integer> variations = new ArrayList<>();
    @SerializedName("grouped_products")
    @Expose
    private List<Integer> groupedProducts = new ArrayList<>();
    @SerializedName("menu_order")
    @Expose
    private int menuOrder;
    @SerializedName("_links")
    @Expose
    private Links links;
    
    @SerializedName("isLiked")
    @Expose
    private String isLiked;
    @SerializedName("attributes_price")
    @Expose
    private String attributesPrice;
    @SerializedName("final_price")
    @Expose
    private String productsFinalPrice;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("customers_basket_quantity")
    @Expose
    private int customersBasketQuantity;
    @SerializedName("category_ids")
    @Expose
    private String categoryIDs;
    @SerializedName("category_names")
    @Expose
    private String categoryNames;
    @SerializedName("product_image")
    @Expose
    private String image;
    @SerializedName("store")
    @Expose
    private Store store;
    @SerializedName("image")
    @Expose
    private ProductImages productImage;
    @SerializedName("selected_variation")
    @Expose
    private int selectedVariationID;
    
    public Store getStore() {
        return store;
    }
    
    public void setStore(Store store) {
        this.store = store;
    }
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
    
    public String getPermalink() {
        return permalink;
    }
    
    public void setPermalink(String permalink) {
        this.permalink = permalink;
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
    
    public String getDateModified() {
        return dateModified;
    }
    
    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
    
    public String getDateModifiedGmt() {
        return dateModifiedGmt;
    }
    
    public void setDateModifiedGmt(String dateModifiedGmt) {
        this.dateModifiedGmt = dateModifiedGmt;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public boolean isFeatured() {
        return featured;
    }
    
    public void setFeatured(boolean featured) {
        this.featured = featured;
    }
    
    public String getCatalogVisibility() {
        return catalogVisibility;
    }
    
    public void setCatalogVisibility(String catalogVisibility) {
        this.catalogVisibility = catalogVisibility;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getShortDescription() {
        return shortDescription;
    }
    
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    
    public String getSku() {
        return sku;
    }
    
    public void setSku(String sku) {
        this.sku = sku;
    }
    
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getRegularPrice() {
        return regularPrice;
    }
    
    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }
    
    public String getSalePrice() {
        return salePrice;
    }
    
    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }
    
    public String getDateOnSaleFrom() {
        return dateOnSaleFrom;
    }
    
    public void setDateOnSaleFrom(String dateOnSaleFrom) {
        this.dateOnSaleFrom = dateOnSaleFrom;
    }
    
    public String getDateOnSaleFromGmt() {
        return dateOnSaleFromGmt;
    }
    
    public void setDateOnSaleFromGmt(String dateOnSaleFromGmt) {
        this.dateOnSaleFromGmt = dateOnSaleFromGmt;
    }
    
    public String getDateOnSaleTo() {
        return dateOnSaleTo;
    }
    
    public void setDateOnSaleTo(String dateOnSaleTo) {
        this.dateOnSaleTo = dateOnSaleTo;
    }
    
    public String getDateOnSaleToGmt() {
        return dateOnSaleToGmt;
    }
    
    public void setDateOnSaleToGmt(String dateOnSaleToGmt) {
        this.dateOnSaleToGmt = dateOnSaleToGmt;
    }
    
    public String getPriceHtml() {
        return priceHtml;
    }
    
    public void setPriceHtml(String priceHtml) {
        this.priceHtml = priceHtml;
    }
    
    public boolean isOnSale() {
        return onSale;
    }
    
    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }
    
    public boolean isPurchasable() {
        return purchasable;
    }
    
    public void setPurchasable(boolean purchasable) {
        this.purchasable = purchasable;
    }
    
    public int getTotalSales() {
        return totalSales;
    }
    
    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }
    
    public boolean isVirtual() {
        return virtual;
    }
    
    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }
    
    public boolean isDownloadable() {
        return downloadable;
    }
    
    public void setDownloadable(boolean downloadable) {
        this.downloadable = downloadable;
    }
    
    public List<ProductDownloads> getDownloads() {
        return downloads;
    }
    
    public void setDownloads(List<ProductDownloads> downloads) {
        this.downloads = downloads;
    }
    
    public int getDownloadLimit() {
        return downloadLimit;
    }
    
    public void setDownloadLimit(int downloadLimit) {
        this.downloadLimit = downloadLimit;
    }
    
    public int getDownloadExpiry() {
        return downloadExpiry;
    }
    
    public void setDownloadExpiry(int downloadExpiry) {
        this.downloadExpiry = downloadExpiry;
    }
    
    public String getExternalUrl() {
        return externalUrl;
    }
    
    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }
    
    public String getButtonText() {
        return buttonText;
    }
    
    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }
    
    public String getTaxStatus() {
        return taxStatus;
    }
    
    public void setTaxStatus(String taxStatus) {
        this.taxStatus = taxStatus;
    }
    
    public String getTaxClass() {
        return taxClass;
    }
    
    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }
    
    public boolean isManageStock() {
        return manageStock;
    }
    
    public void setManageStock(boolean manageStock) {
        this.manageStock = manageStock;
    }
    
    public String  getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(String stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    public boolean isInStock() {
        return inStock;
    }
    
    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
    
    public String getBackorders() {
        return backorders;
    }
    
    public void setBackorders(String backorders) {
        this.backorders = backorders;
    }
    
    public boolean isBackordersAllowed() {
        return backordersAllowed;
    }
    
    public void setBackordersAllowed(boolean backordersAllowed) {
        this.backordersAllowed = backordersAllowed;
    }
    
    public boolean isBackordered() {
        return backordered;
    }
    
    public void setBackordered(boolean backordered) {
        this.backordered = backordered;
    }
    
    public boolean isSoldIndividually() {
        return soldIndividually;
    }
    
    public void setSoldIndividually(boolean soldIndividually) {
        this.soldIndividually = soldIndividually;
    }
    
    public String getWeight() {
        return weight;
    }
    
    public void setWeight(String weight) {
        this.weight = weight;
    }
    
    public ProductDimensions getDimensions() {
        return dimensions;
    }
    
    public void setDimensions(ProductDimensions dimensions) {
        this.dimensions = dimensions;
    }
    
    public boolean isShippingRequired() {
        return shippingRequired;
    }
    
    public void setShippingRequired(boolean shippingRequired) {
        this.shippingRequired = shippingRequired;
    }
    
    public boolean isShippingTaxable() {
        return shippingTaxable;
    }
    
    public void setShippingTaxable(boolean shippingTaxable) {
        this.shippingTaxable = shippingTaxable;
    }
    
    public String getShippingClass() {
        return shippingClass;
    }
    
    public void setShippingClass(String shippingClass) {
        this.shippingClass = shippingClass;
    }
    
    public int getShippingClassId() {
        return shippingClassId;
    }
    
    public void setShippingClassId(int shippingClassId) {
        this.shippingClassId = shippingClassId;
    }
    
    public boolean isReviewsAllowed() {
        return reviewsAllowed;
    }
    
    public void setReviewsAllowed(boolean reviewsAllowed) {
        this.reviewsAllowed = reviewsAllowed;
    }
    
    public String getAverageRating() {
        return averageRating;
    }
    
    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }
    
    public int getRatingCount() {
        return ratingCount;
    }
    
    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
    
    public List<Integer> getRelatedIds() {
        return relatedIds;
    }
    
    public void setRelatedIds(List<Integer> relatedIds) {
        this.relatedIds = relatedIds;
    }
    
    public List<Integer> getUpsellIds() {
        return upsellIds;
    }
    
    public void setUpsellIds(List<Integer> upsellIds) {
        this.upsellIds = upsellIds;
    }
    
    public List<Integer> getCrossSellIds() {
        return crossSellIds;
    }
    
    public void setCrossSellIds(List<Integer> crossSellIds) {
        this.crossSellIds = crossSellIds;
    }
    
    public int getParentId() {
        return parentId;
    }
    
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
    
    public String getPurchaseNote() {
        return purchaseNote;
    }
    
    public void setPurchaseNote(String purchaseNote) {
        this.purchaseNote = purchaseNote;
    }
    
    public List<ProductCategories> getCategories() {
        return categories;
    }
    
    public void setCategories(List<ProductCategories> categories) {
        this.categories = categories;
    }
    
    public List<ProductTags> getTags() {
        return tags;
    }
    
    public void setTags(List<ProductTags> tags) {
        this.tags = tags;
    }
    
    public List<ProductImages> getImages() {
        return images;
    }
    
    public void setImages(List<ProductImages> images) {
        this.images = images;
    }
    
    public List<ProductAttributes> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(List<ProductAttributes> attributes) {
        this.attributes = attributes;
    }
    
    public List<ProductDefaultAttributes> getDefaultAttributes() {
        return defaultAttributes;
    }
    
    public void setDefaultAttributes(List<ProductDefaultAttributes> defaultAttributes) {
        this.defaultAttributes = defaultAttributes;
    }
    
    public List<Integer> getVariations() {
        return variations;
    }
    
    public void setVariations(List<Integer> variations) {
        this.variations = variations;
    }
    
    public List<Integer> getGroupedProducts() {
        return groupedProducts;
    }
    
    public void setGroupedProducts(List<Integer> groupedProducts) {
        this.groupedProducts = groupedProducts;
    }
    
    public int getMenuOrder() {
        return menuOrder;
    }
    
    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }
    
    
    public String getIsLiked() {
        return isLiked;
    }
    
    public void setIsLiked(String isLiked) {
        this.isLiked = isLiked;
    }
    
    public String getAttributesPrice() {
        return attributesPrice;
    }
    
    public void setAttributesPrice(String attributesPrice) {
        this.attributesPrice = attributesPrice;
    }
    
    public String getProductsFinalPrice() {
        return productsFinalPrice;
    }
    
    public void setProductsFinalPrice(String productsFinalPrice) {
        this.productsFinalPrice = productsFinalPrice;
    }
    
    public String getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public int getCustomersBasketQuantity() {
        return customersBasketQuantity;
    }
    
    public void setCustomersBasketQuantity(int customersBasketQuantity) {
        this.customersBasketQuantity = customersBasketQuantity;
    }
    
    public Links getLinks() {
        return links;
    }
    
    public void setLinks(Links links) {
        this.links = links;
    }
    
    public String getCategoryIDs() {
        return categoryIDs;
    }
    
    public void setCategoryIDs(String categoryIDs) {
        this.categoryIDs = categoryIDs;
    }
    
    public String getCategoryNames() {
        return categoryNames;
    }
    
    public void setCategoryNames(String categoryNames) {
        this.categoryNames = categoryNames;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public ProductImages getProductImage() {
        return productImage;
    }
    
    public void setProductImage(ProductImages productImage) {
        this.productImage = productImage;
    }
    
    public int getSelectedVariationID() {
        return selectedVariationID;
    }
    
    public void setSelectedVariationID(int selectedVariationID) {
        this.selectedVariationID = selectedVariationID;
    }
    
    
    
    
    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//

    @Override
    public int describeContents() {
        return 0;
    }
    
    
    public ProductDetails() {
    }
    
    
    
    //********** Writes the values to the Parcel *********//

    @Override
    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeInt(id);
        parcel_out.writeInt(totalSales);
        parcel_out.writeInt(downloadLimit);
        parcel_out.writeInt(downloadExpiry);
        parcel_out.writeInt(customersBasketQuantity);
        parcel_out.writeInt(shippingClassId);
        parcel_out.writeInt(ratingCount);
        parcel_out.writeInt(parentId);
        parcel_out.writeInt(menuOrder);
        parcel_out.writeInt(selectedVariationID);
        parcel_out.writeByte((byte) (featured ? 1 : 0));
        parcel_out.writeByte((byte) (onSale ? 1 : 0));
        parcel_out.writeByte((byte) (purchasable ? 1 : 0));
        parcel_out.writeByte((byte) (virtual ? 1 : 0));
        parcel_out.writeByte((byte) (downloadable ? 1 : 0));
        parcel_out.writeByte((byte) (manageStock ? 1 : 0));
        parcel_out.writeByte((byte) (inStock ? 1 : 0));
        parcel_out.writeByte((byte) (backordersAllowed ? 1 : 0));
        parcel_out.writeByte((byte) (backordered ? 1 : 0));
        parcel_out.writeByte((byte) (soldIndividually ? 1 : 0));
        parcel_out.writeByte((byte) (shippingRequired ? 1 : 0));
        parcel_out.writeByte((byte) (shippingTaxable ? 1 : 0));
        parcel_out.writeByte((byte) (reviewsAllowed ? 1 : 0));
        parcel_out.writeString(name);
        parcel_out.writeString(slug);
        parcel_out.writeString(permalink);
        parcel_out.writeString(dateCreated);
        parcel_out.writeString(dateCreatedGmt);
        parcel_out.writeString(dateModified);
        parcel_out.writeString(dateModifiedGmt);
        parcel_out.writeString(dateOnSaleFrom);
        parcel_out.writeString(dateOnSaleFromGmt);
        parcel_out.writeString(dateOnSaleTo);
        parcel_out.writeString(dateOnSaleToGmt);
        parcel_out.writeString(stockQuantity);
        parcel_out.writeString(type);
        parcel_out.writeString(status);
        parcel_out.writeString(catalogVisibility);
        parcel_out.writeString(description);
        parcel_out.writeString(shortDescription);
        parcel_out.writeString(sku);
        parcel_out.writeString(price);
        parcel_out.writeString(regularPrice);
        parcel_out.writeString(salePrice);
        parcel_out.writeString(priceHtml);
        parcel_out.writeString(attributesPrice);
        parcel_out.writeString(productsFinalPrice);
        parcel_out.writeString(totalPrice);
        parcel_out.writeString(externalUrl);
        parcel_out.writeString(buttonText);
        parcel_out.writeString(taxStatus);
        parcel_out.writeString(taxClass);
        parcel_out.writeString(backorders);
        parcel_out.writeString(weight);
        parcel_out.writeString(shippingClass);
        parcel_out.writeString(averageRating);
        parcel_out.writeString(purchaseNote);
        parcel_out.writeString(isLiked);
        parcel_out.writeString(categoryIDs);
        parcel_out.writeString(categoryNames);
        parcel_out.writeString(image);
        
        parcel_out.writeList(relatedIds);
        parcel_out.writeList(upsellIds);
        parcel_out.writeList(crossSellIds);
        parcel_out.writeList(variations);
        parcel_out.writeList(groupedProducts);
        
        parcel_out.writeList(downloads);
        parcel_out.writeList(categories);
        parcel_out.writeList(tags);
        parcel_out.writeList(images);
        parcel_out.writeList(attributes);
        parcel_out.writeList(defaultAttributes);
        
        parcel_out.writeParcelable(productImage, flags);
        parcel_out.writeParcelable(store,flags);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<ProductDetails> CREATOR = new Creator<ProductDetails>() {
        
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public ProductDetails createFromParcel(Parcel parcel_in) {
            return new ProductDetails(parcel_in);
        }

        // Creates a new array of the Parcelable class
        @Override
        public ProductDetails[] newArray(int size) {
            return new ProductDetails[size];
        }
    };
    
    
    public static Creator<ProductDetails> getCREATOR() {
        return CREATOR;
    }



    //********** Retrieves the values from the Parcel *********//

    protected ProductDetails(Parcel parcel_in) {
        this.id = parcel_in.readInt();
        this.totalSales = parcel_in.readInt();
        this.downloadLimit = parcel_in.readInt();
        this.downloadExpiry = parcel_in.readInt();
        this.customersBasketQuantity = parcel_in.readInt();
        this.shippingClassId = parcel_in.readInt();
        this.ratingCount = parcel_in.readInt();
        this.parentId = parcel_in.readInt();
        this.menuOrder = parcel_in.readInt();
        this.selectedVariationID = parcel_in.readInt();
        this.featured = parcel_in.readByte() != 0;
        this.onSale = parcel_in.readByte() != 0;
        this.purchasable = parcel_in.readByte() != 0;
        this.virtual = parcel_in.readByte() != 0;
        this.downloadable = parcel_in.readByte() != 0;
        this.manageStock = parcel_in.readByte() != 0;
        this.inStock = parcel_in.readByte() != 0;
        this.backordersAllowed = parcel_in.readByte() != 0;
        this.backordered = parcel_in.readByte() != 0;
        this.soldIndividually = parcel_in.readByte() != 0;
        this.shippingRequired = parcel_in.readByte() != 0;
        this.shippingTaxable = parcel_in.readByte() != 0;
        this.reviewsAllowed = parcel_in.readByte() != 0;
        this.name = parcel_in.readString();
        this.slug = parcel_in.readString();
        this.permalink = parcel_in.readString();
        this.dateCreated = parcel_in.readString();
        this.dateCreatedGmt = parcel_in.readString();
        this.dateModified = parcel_in.readString();
        this.dateModifiedGmt = parcel_in.readString();
        this.dateOnSaleFrom = parcel_in.readString();
        this.dateOnSaleFromGmt = parcel_in.readString();
        this.dateOnSaleTo = parcel_in.readString();
        this.dateOnSaleToGmt = parcel_in.readString();
        this.stockQuantity = parcel_in.readString();
        this.type = parcel_in.readString();
        this.status = parcel_in.readString();
        this.catalogVisibility = parcel_in.readString();
        this.description = parcel_in.readString();
        this.shortDescription = parcel_in.readString();
        this.sku = parcel_in.readString();
        this.price = parcel_in.readString();
        this.regularPrice = parcel_in.readString();
        this.salePrice = parcel_in.readString();
        this.priceHtml = parcel_in.readString();
        this.attributesPrice = parcel_in.readString();
        this.productsFinalPrice = parcel_in.readString();
        this.totalPrice = parcel_in.readString();
        this.externalUrl = parcel_in.readString();
        this.buttonText = parcel_in.readString();
        this.taxStatus = parcel_in.readString();
        this.taxClass = parcel_in.readString();
        this.backorders = parcel_in.readString();
        this.weight = parcel_in.readString();
        this.shippingClass = parcel_in.readString();
        this.averageRating = parcel_in.readString();
        this.purchaseNote = parcel_in.readString();
        this.isLiked = parcel_in.readString();
        this.categoryIDs = parcel_in.readString();
        this.categoryNames = parcel_in.readString();
        this.image = parcel_in.readString();
        
        this.relatedIds = new ArrayList<>();
        parcel_in.readList(relatedIds, Integer.class.getClassLoader());
    
        this.upsellIds = new ArrayList<>();
        parcel_in.readList(upsellIds, Integer.class.getClassLoader());
    
        this.crossSellIds = new ArrayList<>();
        parcel_in.readList(crossSellIds, Integer.class.getClassLoader());
    
        this.variations = new ArrayList<>();
        parcel_in.readList(variations, Integer.class.getClassLoader());
    
        this.groupedProducts = new ArrayList<>();
        parcel_in.readList(groupedProducts, Integer.class.getClassLoader());
        

        this.downloads = new ArrayList<>();
        parcel_in.readList(downloads, ProductDownloads.class.getClassLoader());

        this.categories = new ArrayList<>();
        parcel_in.readList(categories, ProductCategories.class.getClassLoader());
    
        this.tags = new ArrayList<>();
        parcel_in.readList(tags, ProductTags.class.getClassLoader());
    
        this.images = new ArrayList<>();
        parcel_in.readList(images, ProductImages.class.getClassLoader());
    
        this.attributes = new ArrayList<>();
        parcel_in.readList(attributes, ProductAttributes.class.getClassLoader());
    
        this.defaultAttributes = new ArrayList<>();
        parcel_in.readList(defaultAttributes, ProductDefaultAttributes.class.getClassLoader());
    
    
        this.productImage = parcel_in.readParcelable(ProductImages.class.getClassLoader());
        this.store = parcel_in.readParcelable(Store.class.getClassLoader());
    }

}
