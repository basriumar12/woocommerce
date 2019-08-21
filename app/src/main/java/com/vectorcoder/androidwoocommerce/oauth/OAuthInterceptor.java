package com.vectorcoder.androidwoocommerce.oauth;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by muneeb.vectorcoder@gmail.com on 30-Jan-18.
 */

public class OAuthInterceptor implements Interceptor {
    
    private static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    private static final String OAUTH_NONCE = "oauth_nonce";
    private static final String OAUTH_SIGNATURE = "oauth_signature";
    private static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
    private static final String OAUTH_SIGNATURE_METHOD_VALUE = "HMAC-SHA1";
    private static final String OAUTH_TIMESTAMP = "oauth_timestamp";
    private static final String OAUTH_VERSION = "oauth_version";
    private static final String OAUTH_VERSION_VALUE = "1.0";
    
    private final String consumerKey;
    private final String consumerSecret;
    
    
    private OAuthInterceptor(String consumerKey, String consumerSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }
    
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
    
        Log.i("request_method", ""+original.method());
        Log.i("request_URL", original.url().toString());
        Log.i("request_host", ""+original.url().host());
        Log.i("request_encodedPath", original.url().encodedPath());
        Log.i("request_query", ""+original.url().query());
        Log.i("request_encodedQuery", ""+original.url().encodedQuery());
        
        ////////////////////////////////////////////////////////////
        
        final String nonce = new TimestampService().getNonce();
        final String timestamp = new TimestampService().getTimestampInSeconds();
        Log.i("request_nonce", nonce);
        Log.i("request_time", timestamp);
        
        String dynamicStructureUrl = original.url().scheme() + "://" + original.url().host() + original.url().encodedPath();
        
        String firstBaseString = original.method() + "&" + urlEncoded(dynamicStructureUrl);
        Log.i("firstBaseString", firstBaseString);
        
        
        String generatedBaseString = "";
        if(original.url().encodedQuery()!=null) {
            generatedBaseString = original.url().encodedQuery() + "&oauth_consumer_key=" + consumerKey + "&oauth_nonce=" + nonce + "&oauth_signature_method=HMAC-SHA1&oauth_timestamp=" + timestamp + "&oauth_version=1.0";
        }
        else {
            generatedBaseString = "oauth_consumer_key=" + consumerKey + "&oauth_nonce=" + nonce + "&oauth_signature_method=HMAC-SHA1&oauth_timestamp=" + timestamp + "&oauth_version=1.0";
        }
        
        ParametersList result = new ParametersList();
        result.addQuerystring(generatedBaseString);
        generatedBaseString=result.sort().asOauthBaseString();
        
        String secondBaseString = "&" + generatedBaseString;
        
        if (firstBaseString.contains("%3F")) {
            secondBaseString = "%26" + urlEncoded(generatedBaseString);
        }
        Log.i("secondBaseString", secondBaseString);
        
        
        String baseString = firstBaseString + secondBaseString;
        
        
        String signature = new HmacSha1SignatureService().getSignature(baseString, consumerSecret, "");
        Log.i("Signature", signature);
        
        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(OAUTH_SIGNATURE_METHOD, OAUTH_SIGNATURE_METHOD_VALUE)
                .addQueryParameter(OAUTH_CONSUMER_KEY, consumerKey)
                .addQueryParameter(OAUTH_VERSION, OAUTH_VERSION_VALUE)
                .addQueryParameter(OAUTH_TIMESTAMP, timestamp)
                .addQueryParameter(OAUTH_NONCE, nonce)
                .addQueryParameter(OAUTH_SIGNATURE, signature)
                .build();
        
        
        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .addHeader("Content-Type", "application/json")
                .url(url);
        
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
    
    
    public static final class Builder {
        
        private String consumerKey;
        private String consumerSecret;
        private int type;
        
        public Builder consumerKey(String consumerKey) {
            if (consumerKey == null) throw new NullPointerException("consumerKey = null");
            this.consumerKey = consumerKey;
            return this;
        }
        
        public Builder consumerSecret(String consumerSecret) {
            if (consumerSecret == null) throw new NullPointerException("consumerSecret = null");
            this.consumerSecret = consumerSecret;
            return this;
        }
        
        
        
        public OAuthInterceptor build() {
            
            if (consumerKey == null) throw new IllegalStateException("consumerKey not set");
            if (consumerSecret == null) throw new IllegalStateException("consumerSecret not set");
            
            return new OAuthInterceptor(consumerKey, consumerSecret);
        }
    }
    
    public String urlEncoded(String url) {
        String encodedurl = "";
        try {
            
            encodedurl = URLEncoder.encode(url, "UTF-8");
            Log.i("encodedURL", encodedurl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        return encodedurl;
    }
}
