package com.vectorcoder.androidwoocommerce.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vectorcoder.androidwoocommerce.R;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vectorcoder.androidwoocommerce.adapters.ProductAdapter;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductDetails;
import com.vectorcoder.androidwoocommerce.models.api_response_model.ErrorResponse;
import com.vectorcoder.androidwoocommerce.network.APIClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;


public class ProductsNewest extends Fragment {

    String customerID;
    Boolean isHeaderVisible;
    Call<List<ProductDetails>> networkCall;
    
    ProgressBar loadingProgress;
    TextView emptyRecord, headerText;
    RecyclerView newest_recycler;

    ProductAdapter productAdapter;

    List<ProductDetails> newestProductList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.f_products_horizontal, container, false);

        // Get the Boolean from Bundle Arguments
        isHeaderVisible = getArguments().getBoolean("isHeaderVisible");

        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        emptyRecord = (TextView) rootView.findViewById(R.id.empty_record_text);
        headerText = (TextView) rootView.findViewById(R.id.products_horizontal_header);
        loadingProgress = (ProgressBar) rootView.findViewById(R.id.loading_progress);
        newest_recycler = (RecyclerView) rootView.findViewById(R.id.products_horizontal_recycler);
    
        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);
    
        // Check if Header must be Invisible or not
        if (!isHeaderVisible) {
            headerText.setVisibility(View.GONE);
        } else {
            headerText.setText(getString(R.string.newest));
        }
    
    
        newestProductList = new ArrayList<>();
    
    
        // RecyclerView has fixed Size
        newest_recycler.setHasFixedSize(true);
        newest_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    
        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new ProductAdapter(getContext(), newestProductList, true);
    
        // Set the Adapter and LayoutManager to the RecyclerView
        newest_recycler.setAdapter(productAdapter);
    
    
        // Request for Most Sold Products
        RequestNewestItems();


        return rootView;
    }



    //*********** Adds Products returned from the Server to the MostLikedProductList ********//

    private void addProducts(List<ProductDetails> productList) {
    
        // Add Products to mostLikedProductList
        if (productList.size() > 0) {
            newestProductList.addAll(productList);
        }
    
        for (int i=0;  i<productList.size();  i++) {
            if (productList.get(i).getStatus() != null  &&  !"publish".equalsIgnoreCase(productList.get(i).getStatus())) {
                for (int j=0;  j<newestProductList.size();  j++) {
                    if (productList.get(i).getId() == newestProductList.get(j).getId()) {
                        newestProductList.remove(j);
                    }
                }
            }
        }
        
        productAdapter.notifyDataSetChanged();
    
        
        // Change the Visibility of emptyRecord Text based on ProductList's Size
        if (productAdapter.getItemCount() == 0) {
            emptyRecord.setVisibility(View.VISIBLE);
        } else {
            emptyRecord.setVisibility(View.GONE);
        }
        
    }



    //*********** Request all the Products from the Server based on Product's Total Likes ********//

    public void RequestNewestItems() {
    
        loadingProgress.setVisibility(View.VISIBLE);
        
        Map<String, String> params = new LinkedHashMap<>();
        params.put("orderby", "date");
        params.put("lang",ConstantValues.LANGUAGE_CODE);
    
        networkCall = APIClient.getInstance()
                .getAllProducts
                        (
                                params
                        );
    
        networkCall.enqueue(new Callback<List<ProductDetails>>() {
            @Override
            public void onResponse(Call<List<ProductDetails>> call, retrofit2.Response<List<ProductDetails>> response) {
    
                loadingProgress.setVisibility(View.GONE);
                
                
                if (response.isSuccessful()) {
                
                    addProducts(response.body());
                
                }
                else {
                    Converter<ResponseBody, ErrorResponse> converter = APIClient.retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    ErrorResponse error;
                    try {
                        error = converter.convert(response.errorBody());
                    } catch (IOException e) {
                        error = new ErrorResponse();
                    }
                
//                    Toast.makeText(getContext(), "Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        
            @Override
            public void onFailure(Call<List<ProductDetails>> call, Throwable t) {
                loadingProgress.setVisibility(View.GONE);
                if (!call.isCanceled())
                    Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
    }



    @Override
    public void onPause() {

        // Check if NetworkCall is being executed
        if (networkCall.isExecuted()){
            // Cancel the NetworkCall
            networkCall.cancel();
        }

        super.onPause();
    }

}