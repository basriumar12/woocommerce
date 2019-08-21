package com.vectorcoder.androidwoocommerce.fragments;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.models.user_model.UpdateUser;
import com.vectorcoder.androidwoocommerce.models.user_model.UserBilling;
import com.vectorcoder.androidwoocommerce.models.user_model.UserShipping;
import com.vectorcoder.androidwoocommerce.network.APIClient;

import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class Thank_You extends Fragment {
    
    String customerID;
    
    //AdView mAdView;
    FrameLayout banner_adView;
    Button order_status_btn, continue_shopping_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.thank_you, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.order_confirmed));
    
        // Get the customersID and defaultAddressID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");

        
        // Binding Layout Views
        banner_adView = (FrameLayout) rootView.findViewById(R.id.banner_adView);
        order_status_btn = (Button) rootView.findViewById(R.id.order_status_btn);
        continue_shopping_btn = (Button) rootView.findViewById(R.id.continue_shopping_btn);
    
    
        if (!ConstantValues.IS_ONE_PAGE_CHECKOUT_ENABLED)
            updateCustomerInfo();
        
//
//        if (ConstantValues.IS_ADMOBE_ENABLED) {
//            // Initialize Admobe
//            mAdView = new AdView(getContext());
//            mAdView.setAdSize(AdSize.BANNER);
//            mAdView.setAdUnitId(ConstantValues.AD_UNIT_ID_BANNER);
//            AdRequest adRequest = new AdRequest.Builder().build();
//            banner_adView.addView(mAdView);
//            mAdView.loadAd(adRequest);
//        }
//

        // Binding Layout Views
        order_status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    
                // Navigate to My_Orders Fragment
                Fragment fragment = new My_Orders();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                
            }
        });


        // Binding Layout Views
        continue_shopping_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to HomePage Fragment
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack(getString(R.string.actionHome), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        });


        return rootView;
    }
    
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        if (item.getItemId() == android.R.id.home) {
            getFragmentManager().popBackStack(getString(R.string.actionHome), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    
    //*********** Updates User's Personal Information ********//
    
    private void updateCustomerInfo() {
    
        UserBilling userBilling = ((App) getContext().getApplicationContext()).getUserDetails().getBilling();
        UserShipping userShipping = ((App) getContext().getApplicationContext()).getUserDetails().getShipping();
    
        Map<String, String> params = new LinkedHashMap<>();
        params.put("insecure", "cool");
        params.put("user_id", customerID);
        
        params.put("billing_first_name", userBilling.getFirstName());
        params.put("billing_last_name", userBilling.getLastName());
        params.put("billing_address_1", userBilling.getAddress1());
        params.put("billing_address_2", userBilling.getAddress2());
        params.put("billing_company", userBilling.getCompany());
        params.put("billing_country", userBilling.getCountry());
        params.put("billing_state", userBilling.getState());
        params.put("billing_city", userBilling.getCity());
        params.put("billing_postcode", userBilling.getPostcode());
        params.put("billing_email", userBilling.getEmail());
        params.put("billing_phone", userBilling.getPhone());
    
        params.put("shipping_first_name", userShipping.getFirstName());
        params.put("shipping_last_name", userShipping.getLastName());
        params.put("shipping_address_1", userShipping.getAddress1());
        params.put("shipping_address_2", userShipping.getAddress2());
        params.put("shipping_company", userShipping.getCompany());
        params.put("shipping_country", userShipping.getCountry());
        params.put("shipping_state", userShipping.getState());
        params.put("shipping_city", userShipping.getCity());
        params.put("shipping_postcode", userShipping.getPostcode());
        
        
        
        Call<UpdateUser> call = APIClient.getInstance()
                .updateCustomerInfo
                        (
                                params
                        );
        
        call.enqueue(new Callback<UpdateUser>() {
            @Override
            public void onResponse(Call<UpdateUser> call, retrofit2.Response<UpdateUser> response) {}
            @Override
            public void onFailure(Call<UpdateUser> call, Throwable t) {}
        });
    }
    
}

