package com.vectorcoder.androidwoocommerce.models.product_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductDownloads implements Parcelable {
    
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("file")
    @Expose
    private String file;
    
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getFile() {
        return file;
    }
    
    public void setFile(String file) {
        this.file = file;
    }
    
    
    
    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//

    @Override
    public int describeContents() {
        return 0;
    }



    //********** Writes the values to the Parcel *********//

    @Override
    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeString(id);
        parcel_out.writeString(name);
        parcel_out.writeString(file);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<ProductDownloads> CREATOR = new Creator<ProductDownloads>() {
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public ProductDownloads createFromParcel(Parcel parcel_in) {
            return new ProductDownloads(parcel_in);
        }

        // Creates a new array of the Parcelable class
        @Override
        public ProductDownloads[] newArray(int size) {
            return new ProductDownloads[size];
        }
    };



    //********** Retrieves the values from the Parcel *********//

    protected ProductDownloads(Parcel parcel_in) {
        this.id = parcel_in.readString();
        this.name = parcel_in.readString();
        this.file = parcel_in.readString();
    }
}
