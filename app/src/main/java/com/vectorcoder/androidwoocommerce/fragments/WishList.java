package com.vectorcoder.androidwoocommerce.fragments;


import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.adapters.ProductAdapterRemovable;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.databases.User_Favorites_DB;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductDetails;
import com.vectorcoder.androidwoocommerce.network.APIClient;

import retrofit2.Call;
import retrofit2.Callback;


public class WishList extends Fragment {

    View rootView;
    String customerID;

    LinearLayout bottomBar;
    TextView emptyRecord;
    TextView sortListText;
    ProgressBar progressBar;
    ProgressBar loadingProgress;
    ToggleButton filterButton;
    ToggleButton toggleLayoutView;
    RecyclerView favourites_recycler;

    User_Favorites_DB user_favorites;
    GridLayoutManager gridLayoutManager;
    ProductAdapterRemovable productAdapter;
    
    ArrayList<Integer> favorites;
    List<ProductDetails> favouriteProductsList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.f_products_vertical, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.actionFavourites));
    
    
        favorites = new ArrayList<>();
        favouriteProductsList  = new ArrayList<>();
        
        user_favorites = new User_Favorites_DB();
    
        // Get the List of Favorite Product's IDs from the Local Databases User_Favorites_DB
        favorites = user_favorites.getUserFavorites();
        
        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        bottomBar = (LinearLayout) rootView.findViewById(R.id.bottomBar);
        sortListText = (TextView) rootView.findViewById(R.id.sort_text);
        emptyRecord = (TextView) rootView.findViewById(R.id.empty_record);
        progressBar = (ProgressBar) rootView.findViewById(R.id.loading_bar);
        loadingProgress = (ProgressBar) rootView.findViewById(R.id.loading_progress);
        filterButton = (ToggleButton) rootView.findViewById(R.id.filterBtn);
        toggleLayoutView = (ToggleButton) rootView.findViewById(R.id.layout_toggleBtn);
        favourites_recycler = (RecyclerView) rootView.findViewById(R.id.products_recycler);


        // Hide some of the Views
        bottomBar.setVisibility(View.GONE);
        emptyRecord.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        loadingProgress.setVisibility(View.GONE);
        filterButton.setVisibility(View.GONE);


        // Initialize the ProductAdapter and GridLayoutManager for RecyclerView
        productAdapter = new ProductAdapterRemovable(getContext(), favouriteProductsList, false, false, emptyRecord);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        
        // Set the Adapter and LayoutManager to the RecyclerView
        favourites_recycler.setAdapter(productAdapter);
        favourites_recycler.setLayoutManager(gridLayoutManager);
    
    
        // Check if the recents List isn't empty
        if (favorites.size() < 1) {
            emptyRecord.setVisibility(View.VISIBLE);
        }
        else {
            emptyRecord.setVisibility(View.GONE);
        
            for (int i=0;  i<favorites.size();  i++) {
                // Request current Product's Details
                RequestProductDetails(favorites.get(i));
            }
        }


        return rootView;
    }



    //*********** Adds Products returned from the Server to the FavouriteProductsList ********//
    
    private void addFavouriteProducts(ProductDetails product) {
        
        // Add Products to recentViewedList
        favouriteProductsList.add(product);
        
        // Notify the Adapter
        productAdapter.notifyDataSetChanged();
    
    
        if (productAdapter.getItemCount() < 1)
            emptyRecord.setVisibility(View.VISIBLE);
    }



    //*********** Request Product's Details from the Server based on Product's ID ********//

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
    
                    addFavouriteProducts(response.body());
                }
            }
        
            @Override
            public void onFailure(Call<ProductDetails> call, Throwable t) {
                Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
}