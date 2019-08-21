
package com.vectorcoder.androidwoocommerce.models.language_model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageDetails {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<LanguageData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LanguageData> getData() {
        return data;
    }

    public void setData(List<LanguageData> data) {
        this.data = data;
    }

}
