
package com.vectorcoder.androidwoocommerce.models.seller_detail_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("count")
    @Expose
    private Integer count;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
