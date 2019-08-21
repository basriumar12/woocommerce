package com.vectorcoder.androidwoocommerce.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.FrameLayout;
//
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;
import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.vectorcoder.androidwoocommerce.network.APIClient;
import com.vectorcoder.androidwoocommerce.customs.DialogLoader;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.adapters.OrdersListAdapter;
import com.vectorcoder.androidwoocommerce.models.order_model.OrderDetails;

import retrofit2.Call;
import retrofit2.Callback;


public class My_Orders extends Fragment {

    View rootView;
    String customerID;
    
    //AdView mAdView;
    TextView emptyRecord;
    FrameLayout banner_adView;
    RecyclerView orders_recycler;

    DialogLoader dialogLoader;
    OrdersListAdapter ordersListAdapter;

    List<OrderDetails> ordersList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.my_orders, container, false);

        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.actionOrders));

        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        
        
        // Binding Layout Views
        emptyRecord = (TextView) rootView.findViewById(R.id.empty_record);
        banner_adView = (FrameLayout) rootView.findViewById(R.id.banner_adView);
        orders_recycler = (RecyclerView) rootView.findViewById(R.id.orders_recycler);
    
    
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
        

        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);
        dialogLoader = new DialogLoader(getContext());
    
        
        ordersList = new ArrayList<>();
    
    
        ordersListAdapter = new OrdersListAdapter(getContext(), ordersList);
    
        // Set the Adapter and LayoutManager to the RecyclerView
        orders_recycler.setAdapter(ordersListAdapter);
        orders_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        
        
        // Request for User's Orders
        if (!ConstantValues.IS_GUEST_LOGGED_IN) {
            RequestMyOrders();
        }
        else {
            emptyRecord.setVisibility(View.VISIBLE);
        }


        return rootView;
    }



    //*********** Request User's Orders from the Server ********//

    public void RequestMyOrders() {

        dialogLoader.showProgressDialog();
    
        Map<String, String> params = new LinkedHashMap<>();
        params.put("per_page", String.valueOf(100));
        params.put("customer", String.valueOf(customerID));
        params.put("lang",ConstantValues.LANGUAGE_CODE);
        

        Call<List<OrderDetails>> call = APIClient.getInstance()
                .getAllOrders
                        (
                                params
                        );

        call.enqueue(new Callback<List<OrderDetails>>() {
            @Override
            public void onResponse(Call<List<OrderDetails>> call, retrofit2.Response<List<OrderDetails>> response) {

                dialogLoader.hideProgressDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {
    
                    ordersList.addAll(response.body());
                    ordersListAdapter.notifyDataSetChanged();
    
    
                    if (ordersListAdapter.getItemCount() < 1)
                        emptyRecord.setVisibility(View.VISIBLE);
                    
                }
                else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrderDetails>> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }

}

