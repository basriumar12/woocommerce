package com.vectorcoder.androidwoocommerce.models.product_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductDimensions implements Parcelable {
    
    @SerializedName("length")
    @Expose
    private String length;
    @SerializedName("width")
    @Expose
    private String width;
    @SerializedName("height")
    @Expose
    private String height;
    
    
    public String getLength() {
        return length;
    }
    
    public void setLength(String length) {
        this.length = length;
    }
    
    public String getWidth() {
        return width;
    }
    
    public void setWidth(String width) {
        this.width = width;
    }
    
    public String getHeight() {
        return height;
    }
    
    public void setHeight(String height) {
        this.height = height;
    }




    //********** Describes the kinds of Special Objects contained in this Parcelable Instance's marshaled representation *********//

    @Override
    public int describeContents() {
        return 0;
    }



    //********** Writes the values to the Parcel *********//

    @Override
    public void writeToParcel(Parcel parcel_out, int flags) {
        parcel_out.writeString(length);
        parcel_out.writeString(width);
        parcel_out.writeString(height);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<ProductDimensions> CREATOR = new Creator<ProductDimensions>() {
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public ProductDimensions createFromParcel(Parcel parcel_in) {
            return new ProductDimensions(parcel_in);
        }

        // Creates a new array of the Parcelable class
        @Override
        public ProductDimensions[] newArray(int size) {
            return new ProductDimensions[size];
        }
    };



    //********** Retrieves the values from the Parcel *********//

    protected ProductDimensions(Parcel parcel_in) {
        this.length = parcel_in.readString();
        this.width = parcel_in.readString();
        this.height = parcel_in.readString();
    }

}
