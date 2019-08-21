package com.vectorcoder.androidwoocommerce.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.support.design.widget.Snackbar;

import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.app.MyAppPrefsManager;
import com.vectorcoder.androidwoocommerce.models.device_model.AppSettingsDetails;

import com.vectorcoder.androidwoocommerce.R;

import com.vectorcoder.androidwoocommerce.utils.Utilities;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.network.StartAppRequests;

import java.util.Locale;


/**
 * SplashScreen activity, appears on App Startup
 **/

public class SplashScreen extends Activity {
    
    View rootView;
    ProgressBar progressBar;
    
    MyTask myTask;
    StartAppRequests startAppRequests;
    MyAppPrefsManager myAppPrefsManager;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        
        Log.i("VC_Shop", "AndroidWoocommerce_Version= "+ConstantValues.CODE_VERSION);
        
        // Bind Layout Views
        progressBar = (ProgressBar) findViewById(R.id.splash_loadingBar);
        rootView = progressBar;
        
        
        // Initializing StartAppRequests and PreferencesManager
        startAppRequests = new StartAppRequests(this);
        myAppPrefsManager = new MyAppPrefsManager(this);
        
        
        ConstantValues.LANGUAGE_CODE = myAppPrefsManager.getUserLanguageCode();
        ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();
        ConstantValues.IS_PUSH_NOTIFICATIONS_ENABLED = myAppPrefsManager.isPushNotificationsEnabled();
        ConstantValues.IS_LOCAL_NOTIFICATIONS_ENABLED = myAppPrefsManager.isLocalNotificationsEnabled();
        
        
        // Start MyTask after 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myTask = new MyTask();
                myTask.execute();
            }
        }, 3000);
        
    }
    
    
    
    //*********** Sets App configuration ********//
    
    private void setAppConfig() {
        
        AppSettingsDetails appSettings = ((App) getApplicationContext()).getAppSettingsDetails();
        
        if (appSettings != null) {
            
            if (appSettings.getAppName() != null  &&  !TextUtils.isEmpty(appSettings.getAppName())) {
                ConstantValues.APP_HEADER = appSettings.getAppName();
            } else {
                ConstantValues.APP_HEADER = getString(R.string.app_name);
            }
            
            
            if (appSettings.getCurrencySymbol() != null  &&  !TextUtils.isEmpty(appSettings.getCurrencySymbol())) {
                ConstantValues.CURRENCY_SYMBOL = appSettings.getCurrencySymbol();
            } else {
                ConstantValues.CURRENCY_SYMBOL = "$";
            }
            
            
            if (appSettings.getFilterMaxPrice() != null  &&  !TextUtils.isEmpty(appSettings.getFilterMaxPrice())) {
                ConstantValues.FILTER_MAX_PRICE = appSettings.getFilterMaxPrice();
            } else {
                ConstantValues.FILTER_MAX_PRICE = "10000";
            }
            
            
            if (appSettings.getNewProductDuration() != null  &&  !TextUtils.isEmpty(appSettings.getNewProductDuration())) {
                ConstantValues.NEW_PRODUCT_DURATION = Long.parseLong(appSettings.getNewProductDuration());
            } else {
                ConstantValues.NEW_PRODUCT_DURATION = 30;
            }
            
            
            ConstantValues.DEFAULT_HOME_STYLE = getString(R.string.actionHome) +" "+ appSettings.getHomeStyle();
            ConstantValues.DEFAULT_CATEGORY_STYLE = getString(R.string.actionCategory) +" "+ appSettings.getCategoryStyle();
            
            ConstantValues.IS_GUEST_CHECKOUT_ENABLED = ("yes".equalsIgnoreCase(appSettings.getGuestCheckout()));
            ConstantValues.IS_ONE_PAGE_CHECKOUT_ENABLED = ("1".equalsIgnoreCase(appSettings.getOnePageCheckout()));
            
            ConstantValues.IS_GOOGLE_LOGIN_ENABLED = ("1".equalsIgnoreCase(appSettings.getGoogleLogin()));
            ConstantValues.IS_FACEBOOK_LOGIN_ENABLED = ("1".equalsIgnoreCase(appSettings.getFacebookLogin()));
            ConstantValues.IS_ADD_TO_CART_BUTTON_ENABLED = ("1".equalsIgnoreCase(appSettings.getCartButton()));
            
            ConstantValues.IS_ADMOBE_ENABLED = ("1".equalsIgnoreCase(appSettings.getAdmob()));
            ConstantValues.ADMOBE_ID = appSettings.getAdmobId();
            ConstantValues.AD_UNIT_ID_BANNER = appSettings.getAdUnitIdBanner();
            ConstantValues.AD_UNIT_ID_INTERSTITIAL = appSettings.getAdUnitIdInterstitial();
            
            ConstantValues.ABOUT_US = appSettings.getAboutPage();
            ConstantValues.TERMS_SERVICES = appSettings.getTermsPage();
            ConstantValues.PRIVACY_POLICY = appSettings.getPrivacyPage();
            ConstantValues.REFUND_POLICY = appSettings.getRefundPage();
            
            myAppPrefsManager.setLocalNotificationsTitle(appSettings.getNotificationTitle());
            myAppPrefsManager.setLocalNotificationsDuration(appSettings.getNotificationDuration());
            myAppPrefsManager.setLocalNotificationsDescription(appSettings.getNotificationText());
            
        }
        else {
            ConstantValues.APP_HEADER = getString(R.string.app_name);
            
            ConstantValues.CURRENCY_SYMBOL = "$";
            ConstantValues.FILTER_MAX_PRICE = "10000";
            ConstantValues.NEW_PRODUCT_DURATION = 30;
            
            ConstantValues.IS_GUEST_CHECKOUT_ENABLED =  false;
            ConstantValues.IS_ONE_PAGE_CHECKOUT_ENABLED = false;
            
            ConstantValues.IS_ADMOBE_ENABLED = false;
            ConstantValues.IS_GOOGLE_LOGIN_ENABLED = false;
            ConstantValues.IS_FACEBOOK_LOGIN_ENABLED = false;
            ConstantValues.IS_ADD_TO_CART_BUTTON_ENABLED = true;
            
            ConstantValues.DEFAULT_HOME_STYLE = getString(R.string.actionHome) +" "+ 1;
            ConstantValues.DEFAULT_CATEGORY_STYLE = getString(R.string.actionCategory) +" "+ 1;
            
            ConstantValues.ABOUT_US = getString(R.string.lorem_ipsum);
            ConstantValues.TERMS_SERVICES = getString(R.string.lorem_ipsum);
            ConstantValues.PRIVACY_POLICY = getString(R.string.lorem_ipsum);
            ConstantValues.REFUND_POLICY = getString(R.string.lorem_ipsum);
        }
        
    }
    
    
    
    /************* MyTask is Inner Class, that handles StartAppRequests on Background Thread *************/
    
    private class MyTask extends AsyncTask<String, Void, String> {
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        
        @Override
        protected String doInBackground(String... params) {
            
            // Check for Internet Connection from the static method of Helper class
            if (Utilities.isNetworkAvailable(SplashScreen.this)) {
                
                // Call the method of StartAppRequests class to process App Startup Requests
                startAppRequests.StartRequests();
                
                return "1";
            } else {
                return "0";
            }
        }
        
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            
            if (result.equalsIgnoreCase("0")) {
                
                progressBar.setVisibility(View.GONE);
                
                // No Internet Connection
                Snackbar.make(rootView, getString(R.string.no_internet), Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.retry), new View.OnClickListener() {
                            
                            // Handle the Retry Button Click
                            @Override
                            public void onClick(View v) {
                                
                                progressBar.setVisibility(View.VISIBLE);
                                
                                // Restart MyTask after 3 seconds
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        myTask = new MyTask();
                                        myTask.execute();
                                    }
                                }, 3000);
                            }
                        })
                        .show();
                
            }
            else {
                setAppConfig();
                
                
                if (myAppPrefsManager.isFirstTimeLaunch()) {
                    // Navigate to IntroScreen
                    startActivity(new Intent(getBaseContext(), IntroScreen.class));
                    finish();
                }
                else {
                    
                    // Navigate to MainActivity
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    
                    
                    
                    finish();
                }
            }
        }
        
    }
    
}


