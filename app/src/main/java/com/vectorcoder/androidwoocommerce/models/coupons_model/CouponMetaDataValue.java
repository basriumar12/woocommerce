package com.vectorcoder.androidwoocommerce.models.coupons_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by muneeb.vectorcoder@gmail.com on 22/05/2018.
 */

public class CouponMetaDataValue implements Parcelable {
    
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("date_expiry")
    @Expose
    private String dateExpires;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("usage_count")
    @Expose
    private int usageCount;
    @SerializedName("individual_use")
    @Expose
    private boolean individualUse;
    @SerializedName("free_shipping")
    @Expose
    private boolean freeShipping;
    @SerializedName("exclude_sale_items")
    @Expose
    private boolean excludeSaleItems;
    
    
    public CouponMetaDataValue() {
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
    
    public String getDateExpires() {
        return dateExpires;
    }
    
    public void setDateExpires(String dateExpires) {
        this.dateExpires = dateExpires;
    }
    
    public String getDiscountType() {
        return discountType;
    }
    
    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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
    
    
    
    
    
    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    
    
    //********** Writes the values to the Parcel *********//
    
    @Override
    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeInt(id);
        parcel_out.writeInt(usageCount);
        parcel_out.writeString(code);
        parcel_out.writeString(amount);
        parcel_out.writeString(discountType);
        parcel_out.writeString(description);
        parcel_out.writeString(dateExpires);
        parcel_out.writeByte((byte) (freeShipping ? 1 : 0));
        parcel_out.writeByte((byte) (individualUse ? 1 : 0));
        parcel_out.writeByte((byte) (excludeSaleItems ? 1 : 0));
    }
    
    
    
    //********** Generates Instances of Parcelable class from a Parcel *********//
    
    public static final Creator<CouponMetaDataValue> CREATOR = new Creator<CouponMetaDataValue>() {
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public CouponMetaDataValue createFromParcel(Parcel parcel_in) {
            return new CouponMetaDataValue(parcel_in);
        }
        
        // Creates a new array of the Parcelable class
        @Override
        public CouponMetaDataValue[] newArray(int size) {
            return new CouponMetaDataValue[size];
        }
    };
    
    
    
    //********** Retrieves the values from the Parcel *********//
    
    protected CouponMetaDataValue(Parcel parcel_in) {
        this.id = parcel_in.readInt();
        this.usageCount = parcel_in.readInt();
        this.code = parcel_in.readString();
        this.amount = parcel_in.readString();
        this.discountType = parcel_in.readString();
        this.description = parcel_in.readString();
        this.dateExpires = parcel_in.readString();
        this.freeShipping = parcel_in.readByte() != 0;
        this.individualUse = parcel_in.readByte() != 0;
        this.excludeSaleItems = parcel_in.readByte() != 0;
    }
    
}
