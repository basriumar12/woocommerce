package com.vectorcoder.androidwoocommerce.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vectorcoder.androidwoocommerce.R;

import com.vectorcoder.androidwoocommerce.activities.Login;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.customs.CircularImageView;
import com.vectorcoder.androidwoocommerce.customs.DialogLoader;
import com.vectorcoder.androidwoocommerce.app.MyAppPrefsManager;
import com.vectorcoder.androidwoocommerce.receivers.AlarmReceiver;
import com.vectorcoder.androidwoocommerce.utils.NotificationScheduler;
import com.vectorcoder.androidwoocommerce.utils.Utilities;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends Fragment {

    View rootView;

    DialogLoader dialogLoader;
    MyAppPrefsManager appPrefs;
    SharedPreferences sharedPreferences;
    
    CircularImageView profile_image;
    Button btn_edit_profile, btn_logout;
    TextView profile_name, profile_email;
    Switch local_notification, push_notification;
    TextView official_web, share_app, rate_app, privacy_policy, refund_policy, service_terms, test_ad_interstitial;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.settings, container, false);

        dialogLoader = new DialogLoader(getContext());
        appPrefs = new MyAppPrefsManager(getContext());
        sharedPreferences = getContext().getSharedPreferences("UserInfo", MODE_PRIVATE);

        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.actionSettings));


        // Binding Layout Views
        rate_app = (TextView) rootView.findViewById(R.id.rate_app);
        share_app = (TextView) rootView.findViewById(R.id.share_app);
        official_web = (TextView) rootView.findViewById(R.id.official_web);
        refund_policy = (TextView) rootView.findViewById(R.id.refund_policy);
        service_terms = (TextView) rootView.findViewById(R.id.service_terms);
        privacy_policy = (TextView) rootView.findViewById(R.id.privacy_policy);
        test_ad_interstitial = (TextView) rootView.findViewById(R.id.test_ad_interstitial);
        push_notification = (Switch) rootView.findViewById(R.id.switch_push_notification);
        local_notification = (Switch) rootView.findViewById(R.id.switch_local_notification);
    
        btn_logout = (Button) rootView.findViewById(R.id.btn_logout);
        btn_edit_profile = (Button) rootView.findViewById(R.id.btn_edit_account);
        profile_name = (TextView) rootView.findViewById(R.id.profile_name);
        profile_email = (TextView) rootView.findViewById(R.id.profile_email);
        profile_image = (CircularImageView) rootView.findViewById(R.id.profile_image);
        
    
        setupAppBarHeader();
    
        if (!ConstantValues.IS_USER_LOGGED_IN) {
            btn_logout.setText(getString(R.string.login));
        }
        
        
        local_notification.setChecked(appPrefs.isLocalNotificationsEnabled());
        push_notification.setChecked(appPrefs.isPushNotificationsEnabled());


        local_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appPrefs.setLocalNotificationsEnabled(isChecked);
                ConstantValues.IS_LOCAL_NOTIFICATIONS_ENABLED = appPrefs.isLocalNotificationsEnabled();
                
                if (isChecked) {
                    NotificationScheduler.setReminder(getContext(), AlarmReceiver.class);
                }
                else {
                    NotificationScheduler.cancelReminder(getContext(), AlarmReceiver.class);
                }
                
            }
        });
    
    
        push_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appPrefs.setPushNotificationsEnabled(isChecked);
                ConstantValues.IS_PUSH_NOTIFICATIONS_ENABLED = appPrefs.isPushNotificationsEnabled();
            
//                TogglePushNotification(ConstantValues.IS_PUSH_NOTIFICATIONS_ENABLED);
            }
        });
    
//
//        if (!ConstantValues.IS_CLIENT_ACTIVE)
//            if (ConstantValues.IS_ADMOBE_ENABLED) {
//                // Initialize Admobe
//                final AdView mAdView = rootView.findViewById(R.id.adView);
//                AdRequest adRequest = new AdRequest.Builder().build();
//                mAdView.loadAd(adRequest);
//                mAdView.setAdListener(new AdListener() {
//                    @Override
//                    public void onAdLoaded() {
//                        super.onAdLoaded();
//                        mAdView.setVisibility(View.VISIBLE);
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(int i) {
//                        super.onAdFailedToLoad(i);
//                        mAdView.setVisibility(View.GONE);
//                        Toast.makeText(getContext(), "Failed to Load BannerAd", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//                test_ad_interstitial.setVisibility(View.VISIBLE);
//                test_ad_interstitial.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // Initialize InterstitialAd
//                        final InterstitialAd mInterstitialAd = new InterstitialAd(getActivity());
//                        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//                        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//                        mInterstitialAd.setAdListener(new AdListener() {
//                            @Override
//                            public void onAdLoaded() {
//                                mInterstitialAd.show();
//                            }
//
//                            @Override
//                            public void onAdFailedToLoad(int i) {
//                                super.onAdFailedToLoad(i);
//                                Toast.makeText(getContext(), "Failed to Load InterstitialAd", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                });
//            }
//

        official_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String web_url = ((App) getActivity().getApplicationContext()).getAppSettingsDetails().getSiteUrl();
                if (!web_url.startsWith("https://")  &&  !web_url.startsWith("http://"))
                    web_url = "http://" + web_url;
                
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(web_url)));
            }
        });
    
        share_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.shareMyApp(getContext());
            }
        });
    
    
        rate_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.rateMyApp(getContext());
            }
        });
    
    
        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), android.R.style.Theme_NoTitleBar);
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
            
                final ImageButton dialog_button = (ImageButton) dialogView.findViewById(R.id.dialog_button);
                final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                final WebView dialog_webView = (WebView) dialogView.findViewById(R.id.dialog_webView);
            
                dialog_title.setText(getString(R.string.privacy_policy));
            
            
                String description = ConstantValues.PRIVACY_POLICY;
                String styleSheet = "<style> " +
                                        "body{background:#ffffff; margin:0; padding:0} " +
                                        "p{color:#757575;} " +
                                        "img{display:inline; height:auto; max-width:100%;}" +
                                    "</style>";
            
                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.loadDataWithBaseURL(null, styleSheet+description, "text/html", "utf-8", null);
            
            
                final AlertDialog alertDialog = dialog.create();
                
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), android.R.style.Theme_NoTitleBar);
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
    
                final ImageButton dialog_button = (ImageButton) dialogView.findViewById(R.id.dialog_button);
                final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                final WebView dialog_webView = (WebView) dialogView.findViewById(R.id.dialog_webView);
            
                dialog_title.setText(getString(R.string.refund_policy));
            
            
                String description = ConstantValues.REFUND_POLICY;
                String styleSheet = "<style> " +
                                        "body{background:#ffffff; margin:0; padding:0} " +
                                        "p{color:#757575;} " +
                                        "img{display:inline; height:auto; max-width:100%;}" +
                                    "</style>";
            
                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.loadDataWithBaseURL(null, styleSheet+description, "text/html", "utf-8", null);
            
            
                final AlertDialog alertDialog = dialog.create();
    
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), android.R.style.Theme_NoTitleBar);
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
    
                final ImageButton dialog_button = (ImageButton) dialogView.findViewById(R.id.dialog_button);
                final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                final WebView dialog_webView = (WebView) dialogView.findViewById(R.id.dialog_webView);
            
                dialog_title.setText(getString(R.string.service_terms));
            
            
                String description = ConstantValues.TERMS_SERVICES;
                String styleSheet = "<style> " +
                                        "body{background:#ffffff; margin:0; padding:0} " +
                                        "p{color:#757575;} " +
                                        "img{display:inline; height:auto; max-width:100%;}" +
                                    "</style>";
            
                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.loadDataWithBaseURL(null, styleSheet+description, "text/html", "utf-8", null);
            
            
                final AlertDialog alertDialog = dialog.create();
    
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
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
    
    
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    
                // Check if the User is Authenticated
                if (ConstantValues.IS_USER_LOGGED_IN) {
    
                    // Edit UserID in SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userID", "");
                    editor.putString("userCookie", "");
                    editor.putString("userPicture", "");
                    editor.putString("userName", "");
                    editor.putString("userDisplayName", "");
                    editor.apply();
    
                    // Set UserLoggedIn in MyAppPrefsManager
                    MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(getContext());
                    myAppPrefsManager.setUserLoggedIn(false);
    
                    // Set isLogged_in of ConstantValues
                    ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();
    
                    setupAppBarHeader();
                    ((MainActivity) getContext()).setupExpandableDrawerList();
                    ((MainActivity) getContext()).setupExpandableDrawerHeader();
    
    
                    btn_logout.setText(getString(R.string.login));
        
                }
                else {
                    // Navigate to Login Activity
                    startActivity(new Intent(getContext(), Login.class));
                    ((MainActivity) getContext()).finish();
                    ((MainActivity) getContext()).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                }
            }
        });


        return rootView;
    }
    
    
    
    //*********** Setup Header of Navigation Drawer ********//
    
    public void setupAppBarHeader() {
        
        // Check if the User is Authenticated
        if (ConstantValues.IS_USER_LOGGED_IN) {
            // Check User's Info from SharedPreferences
            if (!TextUtils.isEmpty(sharedPreferences.getString("userEmail", ""))) {
    
                // Set User's Name, Email and Photo
                profile_email.setText(sharedPreferences.getString("userEmail", ""));
                
                if (!TextUtils.isEmpty(sharedPreferences.getString("userDisplayName", ""))) {
                    profile_name.setText(sharedPreferences.getString("userDisplayName", ""));
                }
                else {
                    profile_name.setText(sharedPreferences.getString("userName", ""));
                }
    
                if (!TextUtils.isEmpty(sharedPreferences.getString("userPicture", "")))
                    Glide.with(this)
                            .load(sharedPreferences.getString("userPicture", "")).asBitmap()
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.profile)
                            .into(profile_image);
    
                btn_edit_profile.setText(getString(R.string.edit_profile));
                btn_edit_profile.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button_green));
                
            }
            else {
                // Set Default Name, Email and Photo
                profile_image.setImageResource(R.drawable.profile);
                profile_name.setText(getString(R.string.login_or_signup));
                profile_email.setText(getString(R.string.login_or_create_account));
                btn_edit_profile.setText(getString(R.string.login));
                btn_edit_profile.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button_red));
            }
            
        }
        else {
            // Set Default Name, Email and Photo
            profile_image.setImageResource(R.drawable.profile);
            profile_name.setText(getString(R.string.login_or_signup));
            profile_email.setText(getString(R.string.login_or_create_account));
            btn_edit_profile.setText(getString(R.string.login));
            btn_edit_profile.setBackground(getResources().getDrawable(R.drawable.rounded_corners_button_red));
        }
        
        
        // Handle DrawerHeader Click Listener
        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                // Check if the User is Authenticated
                if (ConstantValues.IS_USER_LOGGED_IN) {
                    
                    // Navigate to Update_Account Fragment
                    Fragment fragment = new Update_Account();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionCart)).commit();
                    
                }
                else {
                    // Navigate to Login Activity
                    startActivity(new Intent(getContext(), Login.class));
                    ((MainActivity) getContext()).finish();
                    ((MainActivity) getContext()).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                }
            }
        });
    }

}
