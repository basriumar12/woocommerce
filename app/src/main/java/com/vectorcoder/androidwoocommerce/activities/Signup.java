package com.vectorcoder.androidwoocommerce.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.vectorcoder.androidwoocommerce.customs.CircularImageView;

import com.vectorcoder.androidwoocommerce.R;

import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.customs.DialogLoader;
import com.vectorcoder.androidwoocommerce.models.user_model.Nonce;
import com.vectorcoder.androidwoocommerce.models.user_model.UserData;
import com.vectorcoder.androidwoocommerce.utils.LocaleHelper;
import com.vectorcoder.androidwoocommerce.network.APIClient;
import com.vectorcoder.androidwoocommerce.utils.CheckPermissions;
import com.vectorcoder.androidwoocommerce.utils.Utilities;
import com.vectorcoder.androidwoocommerce.utils.ImagePicker;
import com.vectorcoder.androidwoocommerce.utils.ValidateInputs;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;


/**
 * SignUp activity handles User's Registration
 **/

public class Signup extends AppCompatActivity {

    View parentView;
    String registerNonce = "";

    Toolbar toolbar;
    ActionBar actionBar;

    DialogLoader dialogLoader;
    
    //AdView mAdView;
    Button signupBtn;
    FrameLayout banner_adView;
    TextView signup_loginText;
    CircularImageView user_photo;
    FloatingActionButton user_photo_edit_fab;
    TextView service_terms, privacy_policy, refund_policy, and_text;
    EditText user_firstname, user_lastname, username, user_email, user_password;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    
      //  MobileAds.initialize(this, ConstantValues.ADMOBE_ID);
        

        // setting Toolbar
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.signup));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        // Binding Layout Views
        user_photo = (CircularImageView) findViewById(R.id.user_photo);
        user_firstname = (EditText) findViewById(R.id.user_firstname);
        user_lastname = (EditText) findViewById(R.id.user_lastname);
        username = (EditText) findViewById(R.id.username);
        user_email = (EditText) findViewById(R.id.user_email);
        user_password = (EditText) findViewById(R.id.user_password);
        signupBtn = (Button) findViewById(R.id.signupBtn);
        and_text = (TextView) findViewById(R.id.and);
        service_terms = (TextView) findViewById(R.id.service_terms);
        privacy_policy = (TextView) findViewById(R.id.privacy_policy);
        refund_policy = (TextView) findViewById(R.id.refund_policy);
        signup_loginText = (TextView) findViewById(R.id.signup_loginText);
        banner_adView = (FrameLayout) findViewById(R.id.banner_adView);
        user_photo_edit_fab = (FloatingActionButton) findViewById(R.id.user_photo_edit_fab);
    
    
        user_photo.setVisibility(View.GONE);
        user_photo_edit_fab.setVisibility(View.GONE);
//
//
//        if (ConstantValues.IS_ADMOBE_ENABLED) {
//            // Initialize Admobe
//            mAdView = new AdView(Signup.this);
//            mAdView.setAdSize(AdSize.BANNER);
//            mAdView.setAdUnitId(ConstantValues.AD_UNIT_ID_BANNER);
//            AdRequest adRequest = new AdRequest.Builder().build();
//            banner_adView.addView(mAdView);
//            mAdView.loadAd(adRequest);
//            mAdView.setAdListener(new AdListener(){
//                @Override
//                public void onAdLoaded() {
//                    super.onAdLoaded();
//                    banner_adView.setVisibility(View.VISIBLE);
//                }
//                @Override
//                public void onAdFailedToLoad(int i) {
//                    super.onAdFailedToLoad(i);
//                    banner_adView.setVisibility(View.GONE);
//                }
//            });
//        }
//
    
        and_text.setText(" "+getString(R.string.and)+" ");
        
        dialogLoader = new DialogLoader(Signup.this);
    
    
        getRegisterationNonce();
    
        
        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Signup.this, android.R.style.Theme_NoTitleBar);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
    
                final ImageButton dialog_button = (ImageButton) dialogView.findViewById(R.id.dialog_button);
                final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                final WebView dialog_webView = (WebView) dialogView.findViewById(R.id.dialog_webView);
            
                dialog_title.setText(getString(R.string.privacy_policy));
            
            
                String description = ConstantValues.PRIVACY_POLICY;
                String styleSheet = "<style> " +
                                        "body{background:#eeeeee; margin:10; padding:10} " +
                                        "p{color:#757575;} " +
                                        "img{display:inline; height:auto; max-width:100%;}" +
                                    "</style>";
    
                dialog_webView.setVerticalScrollBarEnabled(true);
                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.setBackgroundColor(Color.TRANSPARENT);
                dialog_webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                dialog_webView.loadDataWithBaseURL(null, styleSheet+description, "text/html", "utf-8", null);
            
            
                final AlertDialog alertDialog = dialog.create();
    
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(Signup.this, R.color.colorPrimaryDark));
                }
            
                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            
                alertDialog.show();
            
            }
        });
    
        refund_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Signup.this, android.R.style.Theme_NoTitleBar);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
    
                final ImageButton dialog_button = (ImageButton) dialogView.findViewById(R.id.dialog_button);
                final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                final WebView dialog_webView = (WebView) dialogView.findViewById(R.id.dialog_webView);
            
                dialog_title.setText(getString(R.string.refund_policy));
            
            
                String description = ConstantValues.REFUND_POLICY;
                String styleSheet = "<style> " +
                                        "body{background:#eeeeee; margin:10; padding:10} " +
                                        "p{color:#757575;} " +
                                        "img{display:inline; height:auto; max-width:100%;}" +
                                    "</style>";
    
                dialog_webView.setVerticalScrollBarEnabled(true);
                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.setBackgroundColor(Color.TRANSPARENT);
                dialog_webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                dialog_webView.loadDataWithBaseURL(null, styleSheet+description, "text/html", "utf-8", null);
            
            
                final AlertDialog alertDialog = dialog.create();
    
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(Signup.this, R.color.colorPrimaryDark));
                }
            
                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            
                alertDialog.show();
            }
        });
    
        service_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Signup.this, android.R.style.Theme_NoTitleBar);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
    
                final ImageButton dialog_button = (ImageButton) dialogView.findViewById(R.id.dialog_button);
                final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                final WebView dialog_webView = (WebView) dialogView.findViewById(R.id.dialog_webView);
            
                dialog_title.setText(getString(R.string.service_terms));
            
            
                String description = ConstantValues.TERMS_SERVICES;
                String styleSheet = "<style> " +
                                        "body{background:#eeeeee; margin:10; padding:10} " +
                                        "p{color:#757575;} " +
                                        "img{display:inline; height:auto; max-width:100%;}" +
                                    "</style>";
    
                dialog_webView.setVerticalScrollBarEnabled(true);
                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.setBackgroundColor(Color.TRANSPARENT);
                dialog_webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                dialog_webView.loadDataWithBaseURL(null, styleSheet+description, "text/html", "utf-8", null);
            
            
                final AlertDialog alertDialog = dialog.create();
    
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(Signup.this, R.color.colorPrimaryDark));
                }
            
                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            
                alertDialog.show();
            }
        });


        // Handle Click event of signup_loginText TextView
        signup_loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish SignUpActivity to goto the LoginActivity
                finish();
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
            }
        });


        // Handle Click event of signupBtn Button
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate Login Form Inputs
                boolean isValidData = validateForm();

                if (isValidData) {
                    parentView = v;
                    
                    if (!TextUtils.isEmpty(registerNonce))
                        processRegistration();
                    
                }
            }
        });
    }
    
    
    
    //*********** Proceed User Registration Request ********//
    
    private void getRegisterationNonce() {
    
        Map<String, String> params = new LinkedHashMap<>();
        params.put("controller", "AndroidAppUsers");
        params.put("method", "android_register");
        
        Call<Nonce> call = APIClient.getInstance()
                .getNonce
                        (
                                params
                        );
        
        call.enqueue(new Callback<Nonce>() {
            @Override
            public void onResponse(Call<Nonce> call, retrofit2.Response<Nonce> response) {
                
                // Check if the Response is successful
                if (response.isSuccessful()) {
                    
                    if (!TextUtils.isEmpty(response.body().getNonce())) {
                        registerNonce = response.body().getNonce();
                    }
                    else {
                        Toast.makeText(Signup.this, "Nonce is Empty", Toast.LENGTH_SHORT).show();
                    }
                    
                }
                else {
                    Toast.makeText(Signup.this, "Nonce is Empty", Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<Nonce> call, Throwable t) {
                Toast.makeText(Signup.this, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    
    //*********** Proceed User Registration Request ********//

    private void processRegistration() {
    
        dialogLoader.showProgressDialog();
        
        Call<UserData> call = APIClient.getInstance()
                .processRegistration
                        (
                                "cool",
                                user_firstname.getText().toString().trim() +" "+ user_lastname.getText().toString().trim(),
                                username.getText().toString().trim(),
                                user_email.getText().toString().trim(),
                                user_password.getText().toString().trim(),
                                registerNonce
                        );

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, retrofit2.Response<UserData> response) {

                dialogLoader.hideProgressDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    
                    if ("ok".equalsIgnoreCase(response.body().getStatus())) {
                        
                        // Finish SignUpActivity to goto the LoginActivity
                        finish();
                        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                        
                    }
                    else if ("error".equalsIgnoreCase(response.body().getStatus())) {
                        Toast.makeText(Signup.this, response.body().getError(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(Signup.this, getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Converter<ResponseBody, UserData> converter = APIClient.retrofit.responseBodyConverter(UserData.class, new Annotation[0]);
                    UserData userData;
                    try {
                        userData = converter.convert(response.errorBody());
                    } catch (IOException e) {
                        userData = new UserData();
                    }
    
                    Toast.makeText(Signup.this, "Error : "+userData.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Signup.this, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Validate SignUp Form Inputs ********//

    private boolean validateForm() {
        if (!ValidateInputs.isValidName(user_firstname.getText().toString().trim())) {
            user_firstname.setError(getString(R.string.invalid_first_name));
            return false;
        } else if (!ValidateInputs.isValidName(user_lastname.getText().toString().trim())) {
            user_lastname.setError(getString(R.string.invalid_last_name));
            return false;
        } else if (!ValidateInputs.isValidInput(username.getText().toString().trim())) {
            username.setError(getString(R.string.invalid_contact));
            return false;
        } else if (!ValidateInputs.isValidEmail(user_email.getText().toString().trim())) {
            user_email.setError(getString(R.string.invalid_email));
            return false;
        } else if (!ValidateInputs.isValidPassword(user_password.getText().toString().trim())) {
            user_password.setError(getString(R.string.invalid_password));
            return false;
        } else {
            return true;
        }
    }
    
    
    
    //*********** Set the Base Context for the ContextWrapper ********//
    
    @Override
    protected void attachBaseContext(Context newBase) {
    
        String languageCode = ConstantValues.LANGUAGE_CODE;
        if ("".equalsIgnoreCase(languageCode))
            languageCode = ConstantValues.LANGUAGE_CODE = "en";
    
        super.attachBaseContext(LocaleHelper.wrapLocale(newBase, languageCode));
    }
    
    
    
    //*********** Called when the Activity has detected the User pressed the Back key ********//
    
    @Override
    public void onBackPressed() {
        // Finish SignUpActivity to goto the LoginActivity
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
    }
    
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            
            case android.R.id.home:
    
                // Finish SignUpActivity to goto the LoginActivity
                finish();
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                
                return true;
            
            default:
                return super.onOptionsItemSelected(item);
            
        }
    }
    
}

