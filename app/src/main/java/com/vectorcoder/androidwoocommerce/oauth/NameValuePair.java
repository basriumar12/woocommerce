package com.vectorcoder.androidwoocommerce.oauth;

/**
 * Created by muneeb.vectorcoder@gmail.com on 25-Jan-18.
 */

public class NameValuePair {
    
    String key;
    String value;
    
    public NameValuePair(String key, String value) {
        this.key=key;
        this.value=value;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
}
