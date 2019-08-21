package com.vectorcoder.androidwoocommerce.models.coupons_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CouponMetaData implements Parcelable {
    
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("values")
    @Expose
    private String value;
    
    
    public CouponMetaData() {
    }
    
    
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
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
        parcel_out.writeString(key);
        parcel_out.writeString(value);
    }
    
    
    
    //********** Generates Instances of Parcelable class from a Parcel *********//
    
    public static final Creator<CouponMetaData> CREATOR = new Creator<CouponMetaData>() {
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public CouponMetaData createFromParcel(Parcel parcel_in) {
            return new CouponMetaData(parcel_in);
        }
        
        // Creates a new array of the Parcelable class
        @Override
        public CouponMetaData[] newArray(int size) {
            return new CouponMetaData[size];
        }
    };
    
    
    
    //********** Retrieves the values from the Parcel *********//
    
    protected CouponMetaData(Parcel parcel_in) {
        this.id = parcel_in.readInt();
        this.key = parcel_in.readString();
        this.value = parcel_in.readString();
    }

}
