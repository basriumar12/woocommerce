package com.vectorcoder.androidwoocommerce.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.adapters.ProductAdapter;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.customs.CircularImageView;
import com.vectorcoder.androidwoocommerce.customs.EndlessRecyclerViewScroll;
import com.vectorcoder.androidwoocommerce.models.api_response_model.ErrorResponse;
import com.vectorcoder.androidwoocommerce.models.product_filters_model.PostFilters;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductDetails;
import com.vectorcoder.androidwoocommerce.models.seller_detail_model.SellerDetailModel;
import com.vectorcoder.androidwoocommerce.models.seller_detail_model.SellerProductsIDS;
import com.vectorcoder.androidwoocommerce.network.APIClient;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hyogeun.github.com.colorratingbarlib.ColorRatingBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by Muhammad Nabeel on 10/12/2018.
 */
public class SellerDetail extends Fragment {
    
    ProductAdapter productAdapter;
    SellerDetailModel sellerDetailModel;
    List<ProductDetails> productsList;
    List<Integer> productsIDS;
    RecyclerView all_products_recycler;
    int sellerID;
    int pageNo = 1;
    TextView seller_name,seller_email;
    ColorRatingBar product_rating_bar;
    Button product_cart_btn;
    CircularImageView seller_photo;
    ProgressBar progressBar;
    ProgressBar loadingProgress;
    TextView emptyRecord;
    LoadMoreTask loadMoreTask;
    boolean isGridView;
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.seller_detail, container, false);
    
        // Set the Title of Toolbar
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.product_description));
        
        //initialize product arraylist
        productsList = new ArrayList<>();
        
        //Getting seller id from bundle passing from desc fragment
    
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            sellerID = bundle.getInt(ConstantValues.SELLER_ID, 0);
        }
        
        RequestSellerInfo();
        
        // Request for Products of given Category based on PageNo.
        RequestAllProductsIds(pageNo);
        initUI(view);
        
        return view;
    }
    
    private void initUI(View view){
    
        seller_name = view.findViewById(R.id.seller_name);
        seller_email = view.findViewById(R.id.seller_email);
        product_rating_bar = view.findViewById(R.id.product_rating_bar);
        product_cart_btn = view.findViewById(R.id.product_cart_btn);
        seller_photo = view.findViewById(R.id.seller_photo);
        all_products_recycler = (RecyclerView) view.findViewById(R.id.related_products_recycler);
        progressBar = (ProgressBar) view.findViewById(R.id.loading_bar);
        loadingProgress = (ProgressBar) view.findViewById(R.id.loading_progress);
        emptyRecord = (TextView) view.findViewById(R.id.empty_record);
    
        // Email to seller listener
        product_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{sellerDetailModel.getEmail()});
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    
        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);
    
        // Request for gridLayout
        isGridView = true;
        // Initialize GridLayoutManager and LinearLayoutManager
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager = new LinearLayoutManager(getContext());
    
       
        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new ProductAdapter(getContext(), productsList, false);
        all_products_recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        setRecyclerViewLayoutManager(isGridView);
        all_products_recycler.setAdapter(productAdapter);
    
        // Change the Visibility of emptyRecord Text based on ProductList's Size
        if (productAdapter.getItemCount() == 0) {
            emptyRecord.setVisibility(View.VISIBLE);
        }
        else {
            emptyRecord.setVisibility(View.GONE);
        }
        
    
        // Handle the Scroll event of Product's RecyclerView
        all_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll() {
            @Override
            public void onLoadMore(final int current_page) {
            
                progressBar.setVisibility(View.VISIBLE);
                
                    // Initialize LoadMoreTask to Load More Products from Server without Filters
                    loadMoreTask = new LoadMoreTask(current_page, null);
                
            
                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                loadMoreTask.execute();
            }
        });
    
        productAdapter.notifyDataSetChanged();
        
        
    }
    
    private void RequestSellerInfo(){
        
        Call<SellerDetailModel> call = APIClient.getInstance().getSellerInfo(""+sellerID);
        
        call.enqueue(new Callback<SellerDetailModel>() {
            @Override
            public void onResponse(Call<SellerDetailModel> call, Response<SellerDetailModel> response) {
    
    
                if (response.isSuccessful()) {
                
                    sellerDetailModel = response.body();
    
                    // Setting values to place holders
    
                    String sellerName = sellerDetailModel.getFirstName()+" "+sellerDetailModel.getLastName();
                    seller_name.setText(sellerName);
                    seller_email.setText(sellerDetailModel.getEmail());
                    product_rating_bar.setRating(sellerDetailModel.getRating().getCount());
                    // Set Product Image on ImageView with Glide Library
                    Glide.with(getContext())
                            .load(sellerDetailModel.getGravatar())
                            .into(seller_photo);
    
    
                }
                else {
                    Toast.makeText(getContext(), "Error : "+response.message(), Toast.LENGTH_SHORT).show();
                }
                
            }
    
            @Override
            public void onFailure(Call<SellerDetailModel> call, Throwable t) {
                Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
    
    }
    
    //*********** Request Products from the Server based on PageNo. ********//
    
    public void RequestAllProductsIds(final int pageNumber) {
    
        productsIDS = new ArrayList<>();
        Map<String, Integer> params = new LinkedHashMap<>();
        params.put("post_author", sellerID);
        params.put("page",pageNumber);
        
        Call<SellerProductsIDS> call = APIClient.getInstance().getAllSellerProductsIDS(params);
        call.enqueue(new Callback<SellerProductsIDS>() {
            @Override
            public void onResponse(Call<SellerProductsIDS> call, Response<SellerProductsIDS> response) {
                
                if(response.isSuccessful()){
                    
                    if(response.body().getStatus().equalsIgnoreCase("ok")){
                      
                        productsIDS.addAll(response.body().getData());
                        int size = productsIDS.size();
                        RequestAllVendorProducts(productsIDS);
                        
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        loadingProgress.setVisibility(View.GONE);
                        Toast.makeText(App.getContext(), "Error : "+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                    
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    loadingProgress.setVisibility(View.GONE);
                    Toast.makeText(App.getContext(), "Error : "+response, Toast.LENGTH_SHORT).show();
                }
        
            }
    
            @Override
            public void onFailure(Call<SellerProductsIDS> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                loadingProgress.setVisibility(View.GONE);
                Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    //*********** Request Products from the Server based on PageNo. ********//
    
    public void RequestAllVendorProducts(List<Integer> sellerProductIds) {
    
        Map<String, String> params = new LinkedHashMap<>();
        params.put("include", ""+sellerProductIds);
        params.put("status","publish");
        params.put("lang",ConstantValues.LANGUAGE_CODE);
      
        
        Call<List<ProductDetails>> call = APIClient.getInstance()
                .getAllSellerProducts
                        (
                                params
                        );
        
        call.enqueue(new Callback<List<ProductDetails>>() {
            @Override
            public void onResponse(Call<List<ProductDetails>> call, retrofit2.Response<List<ProductDetails>> response) {
                
                // Hide the ProgressBar
                progressBar.setVisibility(View.GONE);
                loadingProgress.setVisibility(View.GONE);
                
                String str = new Gson().toJson(response.body());
                
                
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
                    
                    Toast.makeText(App.getContext(), "Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<List<ProductDetails>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                loadingProgress.setVisibility(View.GONE);
                Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    //*********** Adds Products returned from the Server to the ProductsList ********//
    
    private void addProducts(List<ProductDetails> products) {
        
        // Add Products to ProductsList from the List of ProductData
        if (products.size() > 0) {
            productsList.addAll(products);
            
            for (int i=0;  i<products.size();  i++) {
                if (products.get(i).getStatus() != null  &&  !"publish".equalsIgnoreCase(products.get(i).getStatus())) {
                    for (int j=0;  j<productsList.size();  j++) {
                        if (products.get(i).getId() == productsList.get(j).getId()) {
                            productsList.remove(j);
                        }
                    }
                }
            }
        }
        
        productAdapter.notifyDataSetChanged();
        
        
        // Change the Visibility of emptyRecord Text based on ProductList's Size
        if (productAdapter.getItemCount() == 0) {
            emptyRecord.setVisibility(View.VISIBLE);
        }
        else {
            emptyRecord.setVisibility(View.GONE);
        }
        
    }
    
    
    /*********** LoadMoreTask Used to Load more Products from the Server in the Background Thread using AsyncTask ********/
    
    private class LoadMoreTask extends AsyncTask<String, Void, String> {
        
        int page_number;
        PostFilters filters;
        
        
        private LoadMoreTask(int page_number, PostFilters filters) {
            this.page_number = page_number;
            this.filters = filters;
        }
        
        
        //*********** Runs on the UI thread before #doInBackground() ********//
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        
        
        //*********** Performs some Processes on Background Thread and Returns a specified Result  ********//
        
        @Override
        protected String doInBackground(String... params) {
            
            // Check if any of the Filter is applied
          
                // Request for Products based on PageNo.
                RequestAllProductsIds(page_number);
            
            
            return "All Done!";
        }
        
        
        //*********** Runs on the UI thread after #doInBackground() ********//
        
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
    
    
    //*********** Switch RecyclerView's LayoutManager ********//
    
    public void setRecyclerViewLayoutManager(Boolean isGridView) {
        int scrollPosition = 0;
        
        // If a LayoutManager has already been set, get current Scroll Position
        if (all_products_recycler.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) all_products_recycler.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }
        
        productAdapter.toggleLayout(isGridView);
        
        all_products_recycler.setLayoutManager(isGridView ? gridLayoutManager : linearLayoutManager);
        all_products_recycler.setAdapter(productAdapter);
        
        all_products_recycler.scrollToPosition(scrollPosition);
    }
    
}
