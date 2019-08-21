package com.vectorcoder.androidwoocommerce.models.user_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by muneeb.vectorcoder@gmail.com on 09/04/2018.
 */

public class UserPicture {
    
    @SerializedName("data")
    @Expose
    private UserPictureDetails data;
    
    public UserPictureDetails getData() {
        return data;
    }
    
    public void setData(UserPictureDetails data) {
        this.data = data;
    }
    
}
