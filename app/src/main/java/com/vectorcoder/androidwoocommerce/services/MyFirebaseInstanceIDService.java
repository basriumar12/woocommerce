package com.vectorcoder.androidwoocommerce.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * FirebaseInstanceIdService Gets FCM instance ID token from Firebase Cloud Messaging Server
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    
    //*********** Called whenever the Token is Generated or Refreshed ********//
    
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
    
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("VC_Shop", "refreshedFCMToken= " + refreshedToken);
    }
    
}
