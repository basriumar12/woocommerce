package com.vectorcoder.androidwoocommerce.models.product_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductImages implements Parcelable {
    
    @SerializedName("id")
    @Expose
    private int id;
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
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("alt")
    @Expose
    private String alt;
    @SerializedName("position")
    @Expose
    private int position;
    
    
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
    
    public String getSrc() {
        return src;
    }
    
    public void setSrc(String src) {
        this.src = src;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAlt() {
        return alt;
    }
    
    public void setAlt(String alt) {
        this.alt = alt;
    }
    
    public int getPosition() {
        return position;
    }
    
    public void setPosition(int position) {
        this.position = position;
    }




    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//

    @Override
    public int describeContents() {
        return 0;
    }



    //********** Writes the values to the Parcel *********//

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(dateCreated);
        parcel.writeString(dateCreatedGmt);
        parcel.writeString(dateModified);
        parcel.writeString(dateModifiedGmt);
        parcel.writeString(src);
        parcel.writeString(name);
        parcel.writeString(alt);
        parcel.writeInt(position);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<ProductImages> CREATOR = new Creator<ProductImages>() {
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public ProductImages createFromParcel(Parcel parcel) {
            return new ProductImages(parcel);
        }

        // Creates a new array of the Parcelable class
        @Override
        public ProductImages[] newArray(int size) {
            return new ProductImages[size];
        }
    };



    //********** Retrieves the values from the Parcel *********//

    protected ProductImages(Parcel parcel) {
        this.id = parcel.readInt();
        this.dateCreated = parcel.readString();
        this.dateCreatedGmt = parcel.readString();
        this.dateModified = parcel.readString();
        this.dateModifiedGmt = parcel.readString();
        this.src = parcel.readString();
        this.name = parcel.readString();
        this.alt = parcel.readString();
        this.position = parcel.readInt();
    }

}
