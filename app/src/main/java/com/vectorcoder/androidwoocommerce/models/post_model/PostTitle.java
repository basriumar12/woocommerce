package com.vectorcoder.androidwoocommerce.models.post_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PostTitle {

    @SerializedName("rendered")
    @Expose
    private String rendered;
    @SerializedName("protected")
    @Expose
    private boolean _protected;

    
    public String getRendered() {
        return rendered;
    }

    public void setRendered(String rendered) {
        this.rendered = rendered;
    }

    public boolean isProtected() {
        return _protected;
    }

    public void setProtected(boolean _protected) {
        this._protected = _protected;
    }

}
