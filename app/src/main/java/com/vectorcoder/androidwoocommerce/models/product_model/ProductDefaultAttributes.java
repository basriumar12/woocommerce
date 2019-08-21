package com.vectorcoder.androidwoocommerce.models.product_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductDefaultAttributes implements Parcelable {
    
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("option")
    @Expose
    private String option;
    
    
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
    
    public String getOption() {
        return option;
    }
    
    public void setOption(String option) {
        this.option = option;
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
        parcel_out.writeString(name);
        parcel_out.writeString(option);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<ProductDefaultAttributes> CREATOR = new Creator<ProductDefaultAttributes>() {
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public ProductDefaultAttributes createFromParcel(Parcel parcel_in) {
            return new ProductDefaultAttributes(parcel_in);
        }

        // Creates a new array of the Parcelable class
        @Override
        public ProductDefaultAttributes[] newArray(int size) {
            return new ProductDefaultAttributes[size];
        }
    };



    //********** Retrieves the values from the Parcel *********//

    protected ProductDefaultAttributes(Parcel parcel_in) {
        this.id = parcel_in.readInt();
        this.name = parcel_in.readString();
        this.option = parcel_in.readString();
    }
}
