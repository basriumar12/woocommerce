package com.vectorcoder.androidwoocommerce.models.product_model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductCategories implements Parcelable {
    
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    
    
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
        parcel_out.writeString(slug);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<ProductCategories> CREATOR = new Creator<ProductCategories>() {
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public ProductCategories createFromParcel(Parcel parcel_in) {
            return new ProductCategories(parcel_in);
        }

        // Creates a new array of the Parcelable class
        @Override
        public ProductCategories[] newArray(int size) {
            return new ProductCategories[size];
        }
    };



    //********** Retrieves the values from the Parcel *********//

    protected ProductCategories(Parcel parcel_in) {
        this.id = parcel_in.readInt();
        this.name = parcel_in.readString();
        this.slug = parcel_in.readString();
    }
}
