package com.vectorcoder.androidwoocommerce.models.coupons_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vectorcoder.androidwoocommerce.models.links_model.Links;


public class CouponDetails implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date_expires")
    @Expose
    private String expiryDate;
    @SerializedName("usage_count")
    @Expose
    private int usageCount;
    @SerializedName("usage_limit")
    @Expose
    private int usageLimit;
    @SerializedName("usage_limit_per_user")
    @Expose
    private int usageLimitPerUser;
    @SerializedName("limit_usage_to_x_items")
    @Expose
    private int limitUsageToXItems;
    @SerializedName("free_shipping")
    @Expose
    private boolean freeShipping;
    @SerializedName("exclude_sale_items")
    @Expose
    private boolean excludeSaleItems;
    @SerializedName("individual_use")
    @Expose
    private boolean individualUse;
    @SerializedName("minimum_amount")
    @Expose
    private String minimumAmount;
    @SerializedName("maximum_amount")
    @Expose
    private String maximumAmount;
    @SerializedName("_links")
    @Expose
    private Links couponLinks = new Links();
    @SerializedName("used_by")
    @Expose
    private List<String> usedBy = new ArrayList<>();
    @SerializedName("email_restrictions")
    @Expose
    private List<String> emailRestrictions = new ArrayList<>();
    @SerializedName("product_ids")
    @Expose
    private List<Integer> productIds = new ArrayList<>();
    @SerializedName("excluded_product_ids")
    @Expose
    private List<Integer> excludedProductIds = new ArrayList<>();
    @SerializedName("product_categories")
    @Expose
    private List<Integer> productCategories = new ArrayList<>();
    @SerializedName("excluded_product_categories")
    @Expose
    private List<Integer> excludedProductCategories = new ArrayList<>();
    
    @SerializedName("valid_items_count")
    @Expose
    private int valid_items_count;
    @SerializedName("valid_items")
    @Expose
    private List<Integer> valid_items = new ArrayList<>();


    public CouponDetails() {
    }
    
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getAmount() {
        return amount;
    }
    
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public String getDiscount() {
        return discount;
    }
    
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    
    public String getDiscountType() {
        return discountType;
    }
    
    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }
    
    public String getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public String getDateModified() {
        return dateModified;
    }
    
    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public int getUsageCount() {
        return usageCount;
    }
    
    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }
    
    public boolean isIndividualUse() {
        return individualUse;
    }
    
    public void setIndividualUse(boolean individualUse) {
        this.individualUse = individualUse;
    }
    
    public int getUsageLimit() {
        return usageLimit;
    }
    
    public void setUsageLimit(int usageLimit) {
        this.usageLimit = usageLimit;
    }
    
    public int getUsageLimitPerUser() {
        return usageLimitPerUser;
    }
    
    public void setUsageLimitPerUser(int usageLimitPerUser) {
        this.usageLimitPerUser = usageLimitPerUser;
    }
    
    public int getLimitUsageToXItems() {
        return limitUsageToXItems;
    }
    
    public void setLimitUsageToXItems(int limitUsageToXItems) {
        this.limitUsageToXItems = limitUsageToXItems;
    }
    
    public boolean isFreeShipping() {
        return freeShipping;
    }
    
    public void setFreeShipping(boolean freeShipping) {
        this.freeShipping = freeShipping;
    }
    
    public boolean isExcludeSaleItems() {
        return excludeSaleItems;
    }
    
    public void setExcludeSaleItems(boolean excludeSaleItems) {
        this.excludeSaleItems = excludeSaleItems;
    }
    
    public String getMinimumAmount() {
        return minimumAmount;
    }
    
    public void setMinimumAmount(String minimumAmount) {
        this.minimumAmount = minimumAmount;
    }
    
    public String getMaximumAmount() {
        return maximumAmount;
    }
    
    public void setMaximumAmount(String maximumAmount) {
        this.maximumAmount = maximumAmount;
    }
    
    public Links getCouponLinks() {
        return couponLinks;
    }
    
    public void setCouponLinks(Links couponLinks) {
        this.couponLinks = couponLinks;
    }
    
    public List<String> getUsedBy() {
        return usedBy;
    }
    
    public void setUsedBy(List<String> usedBy) {
        this.usedBy = usedBy;
    }
    
    public List<String> getEmailRestrictions() {
        return emailRestrictions;
    }
    
    public void setEmailRestrictions(List<String> emailRestrictions) {
        this.emailRestrictions = emailRestrictions;
    }
    
    public List<Integer> getProductIds() {
        return productIds;
    }
    
    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }
    
    public List<Integer> getExcludedProductIds() {
        return excludedProductIds;
    }
    
    public void setExcludedProductIds(List<Integer> excludedProductIds) {
        this.excludedProductIds = excludedProductIds;
    }
    
    public List<Integer> getProductCategories() {
        return productCategories;
    }
    
    public void setProductCategories(List<Integer> productCategories) {
        this.productCategories = productCategories;
    }
    
    public List<Integer> getExcludedProductCategories() {
        return excludedProductCategories;
    }
    
    public void setExcludedProductCategories(List<Integer> excludedProductCategories) {
        this.excludedProductCategories = excludedProductCategories;
    }
    
    
    public int getValid_items_count() {
        return valid_items_count;
    }
    
    public void setValid_items_count(int valid_items_count) {
        this.valid_items_count = valid_items_count;
    }
    
    public List<Integer> getValid_items() {
        return valid_items;
    }
    
    public void setValid_items(List<Integer> valid_items) {
        this.valid_items = valid_items;
    }
    
    
    
    
    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//

    @Override
    public int describeContents() {
        return 0;
    }



    //********** Writes the values to the Parcel *********//

    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeInt(id);
        parcel_out.writeInt(usageCount);
        parcel_out.writeInt(usageLimit);
        parcel_out.writeInt(usageLimitPerUser);
        parcel_out.writeInt(limitUsageToXItems);
        parcel_out.writeValue(code);
        parcel_out.writeValue(amount);
        parcel_out.writeValue(discount);
        parcel_out.writeValue(discountType);
        parcel_out.writeValue(dateCreated);
        parcel_out.writeValue(dateModified);
        parcel_out.writeValue(description);
        parcel_out.writeValue(expiryDate);
        parcel_out.writeValue(minimumAmount);
        parcel_out.writeValue(maximumAmount);
        parcel_out.writeByte((byte) (freeShipping ? 1 : 0));
        parcel_out.writeByte((byte) (individualUse ? 1 : 0));
        parcel_out.writeByte((byte) (excludeSaleItems ? 1 : 0));
    
        parcel_out.writeList(usedBy);
        parcel_out.writeList(emailRestrictions);
        parcel_out.writeList(productIds);
        parcel_out.writeList(excludedProductIds);
        parcel_out.writeList(productCategories);
        parcel_out.writeList(excludedProductCategories);
        
        parcel_out.writeInt(valid_items_count);
        parcel_out.writeList(valid_items);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<CouponDetails> CREATOR = new Creator<CouponDetails>() {

        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public CouponDetails createFromParcel(Parcel parcel_in) {
            return new CouponDetails(parcel_in);
        }

        // Creates a new array of the Parcelable class
        @Override
        public CouponDetails[] newArray(int size) {
            return new CouponDetails[size];
        }
    };



    //********** Retrieves the values from the Parcel *********//

    protected CouponDetails(Parcel parcel_in) {
        this.id = parcel_in.readInt();
        this.usageCount = parcel_in.readInt();
        this.usageLimit = parcel_in.readInt();
        this.usageLimitPerUser = parcel_in.readInt();
        this.limitUsageToXItems = parcel_in.readInt();
        this.code = parcel_in.readString();
        this.amount = parcel_in.readString();
        this.discount = parcel_in.readString();
        this.discountType = parcel_in.readString();
        this.dateCreated = parcel_in.readString();
        this.dateModified = parcel_in.readString();
        this.description = parcel_in.readString();
        this.expiryDate = parcel_in.readString();
        this.minimumAmount = parcel_in.readString();
        this.maximumAmount = parcel_in.readString();
        this.freeShipping = parcel_in.readByte() != 0;
        this.individualUse = parcel_in.readByte() != 0;
        this.excludeSaleItems = parcel_in.readByte() != 0;
        
        this.usedBy = parcel_in.createStringArrayList();
        this.emailRestrictions = parcel_in.createStringArrayList();
    
        this.productIds = new ArrayList<>();
        parcel_in.readList(productIds, Integer.class.getClassLoader());
    
        this.excludedProductIds = new ArrayList<>();
        parcel_in.readList(excludedProductIds, Integer.class.getClassLoader());
    
        this.productCategories = new ArrayList<>();
        parcel_in.readList(productCategories, Integer.class.getClassLoader());
    
        this.excludedProductCategories = new ArrayList<>();
        parcel_in.readList(excludedProductCategories, Integer.class.getClassLoader());
    
    
        this.valid_items_count = parcel_in.readInt();
        
        this.valid_items = new ArrayList<>();
        parcel_in.readList(valid_items, Integer.class.getClassLoader());
        
    }

}
