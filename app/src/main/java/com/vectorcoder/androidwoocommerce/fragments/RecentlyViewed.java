package com.vectorcoder.androidwoocommerce.fragments;


import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vectorcoder.androidwoocommerce.R;

import java.util.List;
import java.util.ArrayList;

import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.network.APIClient;
import com.vectorcoder.androidwoocommerce.databases.User_Recents_DB;
import com.vectorcoder.androidwoocommerce.adapters.ProductAdapterRemovable;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductDetails;

import retrofit2.Call;
import retrofit2.Callback;


public class RecentlyViewed extends Fragment {

    String customerID;
    
    ProgressBar loadingProgress;
    TextView emptyRecord, headerText;
    RecyclerView recents_recycler;
    
    User_Recents_DB recents_db;
    ProductAdapterRemovable productAdapter;

    ArrayList<Integer> recents;
    List<ProductDetails> recentViewedList;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f_products_horizontal, container, false);

        recents = new ArrayList<>();
        recentViewedList  = new ArrayList<>();
        
        recents_db = new User_Recents_DB();

        // Get the List of RecentlyViewed Product's IDs from the Local Databases User_Recents_DB
        recents = recents_db.getUserRecents();

        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        emptyRecord = (TextView) rootView.findViewById(R.id.empty_record_text);
        headerText = (TextView) rootView.findViewById(R.id.products_horizontal_header);
        loadingProgress = (ProgressBar) rootView.findViewById(R.id.loading_progress);
        recents_recycler = (RecyclerView) rootView.findViewById(R.id.products_horizontal_recycler);
        

        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);
        loadingProgress.setVisibility(View.GONE);

        // Set text of Header
        headerText.setText(getString(R.string.recentlyViewed));
    
    
        // Initialize the ProductAdapterRemovable and LayoutManager for RecyclerView
        productAdapter = new ProductAdapterRemovable(getContext(), recentViewedList, true, true, headerText);
        recents_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Set the Adapter and LayoutManager to the RecyclerView
        recents_recycler.setAdapter(productAdapter);
        

        // Check if the recents List isn't empty
        if (recents.size() < 1) {
            headerText.setVisibility(View.GONE);
        }
        else {
            headerText.setVisibility(View.VISIBLE);

            for (int i=0;  i<recents.size();  i++) {
                // Request current Product's Details
                RequestProductDetails(recents.get(i));
            }
        }


        return rootView;
    }



    //*********** Adds Products returned from the Server to the RecentViewedList ********//

    private void addRecentProducts(ProductDetails product) {

        // Add Products to recentViewedList
        recentViewedList.add(product);

        // Notify the Adapter
        productAdapter.notifyDataSetChanged();
        
        
        if (productAdapter.getItemCount() < 1)
            headerText.setVisibility(View.GONE);
    }



    //*********** Request the Product's Details from the Server based on Product's ID ********//

    public void RequestProductDetails(final int products_id) {

        Call<ProductDetails> call = APIClient.getInstance()
                .getSingleProduct
                        (
                                String.valueOf(products_id)
                                
                        );

        call.enqueue(new Callback<ProductDetails>() {
            @Override
            public void onResponse(Call<ProductDetails> call, retrofit2.Response<ProductDetails> response) {
                
                if (response.isSuccessful()) {
                    
                    addRecentProducts(response.body());
                }
            }

            @Override
            public void onFailure(Call<ProductDetails> call, Throwable t) {
                Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
    }

}

