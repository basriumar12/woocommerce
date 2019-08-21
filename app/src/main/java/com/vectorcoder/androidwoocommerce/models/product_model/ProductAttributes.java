package com.vectorcoder.androidwoocommerce.models.product_model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductAttributes implements Parcelable {
    
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("position")
    @Expose
    private int position;
    @SerializedName("visible")
    @Expose
    private boolean visible;
    @SerializedName("variation")
    @Expose
    private boolean variation;
    @SerializedName("option")
    @Expose
    private String option;
    @SerializedName("options")
    @Expose
    private List<String> options = new ArrayList<>();
    
    
    
    public ProductAttributes() {
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
    
    public int getPosition() {
        return position;
    }
    
    public void setPosition(int position) {
        this.position = position;
    }
    
    public boolean isVisible() {
        return visible;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public boolean isVariation() {
        return variation;
    }
    
    public void setVariation(boolean variation) {
        this.variation = variation;
    }
    
    public String getOption() {
        return option;
    }
    
    public void setOption(String option) {
        this.option = option;
    }
    
    public List<String> getOptions() {
        return options;
    }
    
    public void setOptions(List<String> options) {
        this.options = options;
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
        parcel_out.writeInt(position);
        parcel_out.writeByte((byte) (visible ? 1 : 0));
        parcel_out.writeByte((byte) (variation ? 1 : 0));
        parcel_out.writeString(option);
        parcel_out.writeList(options);
    }



    //********** Generates Instances of Parcelable class from a Parcel *********//

    public static final Creator<ProductAttributes> CREATOR = new Creator<ProductAttributes>() {
        // Creates a new Instance of the Parcelable class, Instantiating it from the given Parcel
        @Override
        public ProductAttributes createFromParcel(Parcel parcel_in) {
            return new ProductAttributes(parcel_in);
        }

        // Creates a new array of the Parcelable class
        @Override
        public ProductAttributes[] newArray(int size) {
            return new ProductAttributes[size];
        }
    };



    //********** Retrieves the values from the Parcel *********//

    protected ProductAttributes(Parcel parcel_in) {

        this.id = parcel_in.readInt();
        this.name = parcel_in.readString();
        this.position = parcel_in.readInt();
        this.visible = parcel_in.readByte() != 0;
        this.variation = parcel_in.readByte() != 0;
        this.option = parcel_in.readString();
        this.options = parcel_in.createStringArrayList();

    }

}
