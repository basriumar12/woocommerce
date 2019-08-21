package com.vectorcoder.androidwoocommerce.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.vectorcoder.androidwoocommerce.R;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.models.product_filters_model.FilterDetails;
import com.vectorcoder.androidwoocommerce.models.product_filters_model.PostFilters;
import com.vectorcoder.androidwoocommerce.models.product_filters_model.ProductFilters;
import com.vectorcoder.androidwoocommerce.network.APIClient;
import com.vectorcoder.androidwoocommerce.adapters.ProductAdapter;
import com.vectorcoder.androidwoocommerce.customs.FilterDialog;
import com.vectorcoder.androidwoocommerce.customs.EndlessRecyclerViewScroll;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductDetails;
import com.vectorcoder.androidwoocommerce.models.api_response_model.ErrorResponse;

import retrofit2.Call;
import retrofit2.Callback;
import okhttp3.ResponseBody;
import retrofit2.Converter;


public class All_Category_Products extends Fragment {

    View rootView;
    
    int pageNo = 1;
    boolean isVisible;
    boolean isGridView;
    boolean isFilterApplied;
    boolean isSaleApplied = false;
    boolean isFeaturedApplied = false;
    
    int categoryID;
    String customerID;
    String order = "desc";
    String sortBy = "date";

    LinearLayout bottomBar;
    LinearLayout sortList;
    TextView emptyRecord;
    TextView sortListText;
    ProgressBar progressBar;
    ProgressBar loadingProgress;
    ImageButton removeFilterBtn;
    ToggleButton filterButton;
    ToggleButton toggleLayoutView;
    RecyclerView category_products_recycler;
    
    LoadMoreTask loadMoreTask;
    FilterDialog filterDialog;
    PostFilters postFilters = null;
    ProductFilters productFilters = null;

    ProductAdapter productAdapter;
    List<ProductDetails> categoryProductsList;
    List<FilterDetails> productFiltersList;

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    
    
    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    
        isVisible = isVisibleToUser;
    }
    
    
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.f_products_vertical, container, false);
    
    
        if (getArguments() != null) {
            if (getArguments().containsKey("sortBy")) {
                sortBy = getArguments().getString("sortBy", "date");
            }
            if (getArguments().containsKey("on_sale")) {
                isSaleApplied = getArguments().getBoolean("on_sale", false);
            }
            if (getArguments().containsKey("featured")) {
                isFeaturedApplied = getArguments().getBoolean("featured", false);
            }
        }
        
        
        // Get CategoryID from bundle arguments
        categoryID = getArguments().getInt("CategoryID");

        // Get the Customer's ID from SharedPreferences
        customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        bottomBar = (LinearLayout) rootView.findViewById(R.id.bottomBar);
        sortList = (LinearLayout) rootView.findViewById(R.id.sort_list);
        sortListText = (TextView) rootView.findViewById(R.id.sort_text);
        emptyRecord = (TextView) rootView.findViewById(R.id.empty_record);
        progressBar = (ProgressBar) rootView.findViewById(R.id.loading_bar);
        loadingProgress = (ProgressBar) rootView.findViewById(R.id.loading_progress);
        removeFilterBtn = (ImageButton) rootView.findViewById(R.id.removeFilterBtn);
        filterButton = (ToggleButton) rootView.findViewById(R.id.filterBtn);
        toggleLayoutView = (ToggleButton) rootView.findViewById(R.id.layout_toggleBtn);
        category_products_recycler = (RecyclerView) rootView.findViewById(R.id.products_recycler);


        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        loadingProgress.setVisibility(View.GONE);
    
    
        sortListText.setText(getString(R.string.Newest));
        
        isGridView = true;
        isFilterApplied = (isSaleApplied || isFeaturedApplied);
    
        toggleLayoutView.setChecked(isGridView);
        filterButton.setChecked(isFilterApplied);
        removeFilterBtn.setVisibility(isFilterApplied? View.VISIBLE : View.GONE);
        

        // Initialize CategoryProductsList
        categoryProductsList = new ArrayList<>();
        productFiltersList = new ArrayList<>();
    
    
        // Initialize GridLayoutManager and LinearLayoutManager
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager = new LinearLayoutManager(getContext());
    
    
        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new ProductAdapter(getContext(), categoryProductsList, false);
    
    
        setRecyclerViewLayoutManager(isGridView);
        category_products_recycler.setAdapter(productAdapter);
        
        
        if (isFilterApplied) {
            postFilters = new PostFilters();
            postFilters.setOnSale(String.valueOf(isSaleApplied));
            postFilters.setFeatured(String.valueOf(isFeaturedApplied));
            postFilters.setMinPrice("0");
            postFilters.setMaxPrice(ConstantValues.FILTER_MAX_PRICE);
        
            loadingProgress.setVisibility(View.VISIBLE);
        
            RequestFilteredProducts(pageNo, postFilters);
        
        }
        else {
            postFilters = new PostFilters();
            loadingProgress.setVisibility(View.VISIBLE);
        
            // Request for Products of given Category based on PageNo.
            RequestCategoryProducts(pageNo);
        }
        
        
        try {
            // Initialize FilterDialog and Override its abstract methods
        
            filterDialog = new FilterDialog(getContext(), productFiltersList, productFilters, postFilters) {
                @Override
                public void clearFilters() {
                    // Clear Filters
                    isFilterApplied = false;
                    filterButton.setChecked(false);
                    removeFilterBtn.setVisibility(View.GONE);
                    postFilters = null;
                    productFilters = null;
                    categoryProductsList.clear();
                    productAdapter.notifyDataSetChanged();
                    loadingProgress.setVisibility(View.VISIBLE);
                    new LoadMoreTask(pageNo, postFilters).execute();
                    RequestFilters(postFilters);
                }
        
                @Override
                public void applyFilters(PostFilters postFilterData) {
                    // Apply Filters
                    isFilterApplied = true;
                    filterButton.setChecked(true);
                    removeFilterBtn.setVisibility(View.VISIBLE);
                    postFilters = postFilterData;
                    categoryProductsList.clear();
                    productAdapter.notifyDataSetChanged();
                    loadingProgress.setVisibility(View.VISIBLE);
                    new LoadMoreTask(pageNo, postFilters).execute();
                    RequestFilters(postFilters);
                }
            };
        }
       catch (Exception e){
            e.getCause();
       }
        
        
        // Request Server for Product Filters
        RequestFilters(postFilters);
        

        // Handle the Scroll event of Product's RecyclerView
        category_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar) {
            @Override
            public void onLoadMore(final int current_page) {
                
                progressBar.setVisibility(View.VISIBLE);

                if (isFilterApplied) {
                    // Initialize LoadMoreTask to Load More Products from Server against some Filters
                    loadMoreTask = new LoadMoreTask(current_page, postFilters);
                } else {
                    // Initialize LoadMoreTask to Load More Products from Server without Filters
                    loadMoreTask = new LoadMoreTask(current_page, null);
                }

                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                loadMoreTask.execute();
            }
        });

        productAdapter.notifyDataSetChanged();
    
    
        // Toggle RecyclerView's LayoutManager
        toggleLayoutView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isGridView = isChecked;
                setRecyclerViewLayoutManager(isGridView);
            }
        });
    
    
        // Handle the Click event of Filter Button
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            
                if (isFilterApplied) {
                    filterButton.setChecked(true);
                    filterDialog.show();
                
                } else {
                    filterButton.setChecked(false);
                    filterDialog.show();
                }
            }
        });



        sortList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] sortArray = getResources().getStringArray(R.array.sortBy_array);

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(true);
                
                dialog.setItems(sortArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        
                        String selectedText = sortArray[which];
                        sortListText.setText(selectedText);
    
    
                        if (selectedText.equalsIgnoreCase(sortArray[0])) {
                            order = "asc";
                            sortBy = "title";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[1])) {
                            order = "desc";
                            sortBy = "title";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[2])) {
                            order = "desc";
                            sortBy = "date";
                        }
                        else {
                            order = "desc";
                            sortBy = "date";
                        }
    
    
                        categoryProductsList.clear();
                        productAdapter.notifyDataSetChanged();
                        loadingProgress.setVisibility(View.VISIBLE);
                        
                        if(isFilterApplied){
                            // Initialize LoadMoreTask to Load More Products from Server against some Filters
                            RequestFilteredProducts(pageNo, postFilters);
                        }
                        else {
                            // Initialize LoadMoreTask to Load More Products from Server without Filters
                            RequestCategoryProducts(pageNo);
                        }
                        dialog.dismiss();


                        // Handle the Scroll event of Product's RecyclerView
                        category_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar) {
                            @Override
                            public void onLoadMore(final int current_page) {
                                
                                progressBar.setVisibility(View.VISIBLE);
    
                                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                                loadMoreTask = new LoadMoreTask(current_page, postFilters);
                                loadMoreTask.execute();
                            }
                        });

                    }
                });
                dialog.show();
            }
        });
    
    
        removeFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFilterApplied = false;
                filterButton.setChecked(false);
                removeFilterBtn.setVisibility(View.GONE);
                postFilters = null;
                productFilters = null;
                categoryProductsList.clear();
                productAdapter.notifyDataSetChanged();
                loadingProgress.setVisibility(View.VISIBLE);
                new LoadMoreTask(pageNo, postFilters).execute();
                RequestFilters(postFilters);
            }
        });


        return rootView;
    }
    
    
    
    //*********** Switch RecyclerView's LayoutManager ********//
    
    public void setRecyclerViewLayoutManager(Boolean isGridView) {
        int scrollPosition = 0;
        
        // If a LayoutManager has already been set, get current Scroll Position
        if (category_products_recycler.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) category_products_recycler.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }
    
        productAdapter.toggleLayout(isGridView);
        
        category_products_recycler.setLayoutManager(isGridView ? gridLayoutManager : linearLayoutManager);
        category_products_recycler.setAdapter(productAdapter);
        
        category_products_recycler.scrollToPosition(scrollPosition);
    }
    
    
    
    //*********** Adds Products returned from the Server to the ProductsList ********//
    
    private void addProducts(List<ProductDetails> products) {
        
        // Add Products to CategoryProductsList
        if (products.size() > 0) {
            categoryProductsList.addAll(products);
    
            for (int i=0;  i<products.size();  i++) {
                if (products.get(i).getStatus() != null  &&  !"publish".equalsIgnoreCase(products.get(i).getStatus())) {
                    for (int j=0;  j<categoryProductsList.size();  j++) {
                        if (products.get(i).getId() == categoryProductsList.get(j).getId()) {
                            categoryProductsList.remove(j);
                        }
                    }
                }
            }
        }
        
        productAdapter.notifyDataSetChanged();
    
    
        // Change the Visibility of emptyRecord Text based on CategoryProductsList's Size
        if (productAdapter.getItemCount() == 0) {
            emptyRecord.setVisibility(View.VISIBLE);
        
        } else {
            emptyRecord.setVisibility(View.GONE);
        }
        
    }
    
    
    
    //*********** Request all of the Product's Filters from the Server ********//
    
    public void updateFilters(final ProductFilters updatedProductFilters, final PostFilters updatedPostFilters) {
    
        List<FilterDetails> updatedAttributesList = new ArrayList<>();
    
        if (updatedProductFilters.getAttributes() != null && updatedProductFilters.getAttributes().size() > 0)
            updatedAttributesList = updatedProductFilters.getAttributes();
    
        productFilters = updatedProductFilters;
    
        productFiltersList = new ArrayList<>();
        productFiltersList = updatedAttributesList;
    
        try {
            filterDialog = new FilterDialog(getContext(), productFiltersList, updatedProductFilters, updatedPostFilters) {
                @Override
                public void clearFilters() {
                    // Clear Filters
                    isFilterApplied = false;
                    filterButton.setChecked(false);
                    removeFilterBtn.setVisibility(View.GONE);
                    postFilters = null;
                    productFilters = null;
                    categoryProductsList.clear();
                    productAdapter.notifyDataSetChanged();
                    loadingProgress.setVisibility(View.VISIBLE);
                    new LoadMoreTask(pageNo, postFilters).execute();
                    RequestFilters(postFilters);
                }
            
                @Override
                public void applyFilters(PostFilters postFilterData) {
                    // Apply Filters
                    isFilterApplied = true;
                    filterButton.setChecked(true);
                    removeFilterBtn.setVisibility(View.VISIBLE);
                    postFilters = postFilterData;
                    categoryProductsList.clear();
                    productAdapter.notifyDataSetChanged();
                    loadingProgress.setVisibility(View.VISIBLE);
                    new LoadMoreTask(pageNo, postFilters).execute();
                    RequestFilters(postFilters);
                }
            };
        }
        catch (Exception e){
            e.getCause();
        }
    }
    
    
    
    
    //*********** Request all of the Product's Filters from the Server ********//
    
    public void RequestFilters(final PostFilters postFilters) {
        
        Map<String, String> params = new LinkedHashMap<>();
        params.put("insecure", "cool");
       params.put("lang",ConstantValues.LANGUAGE_CODE);
        
        if (postFilters != null) {
            if (postFilters.getMaxPrice() != null  &&  !TextUtils.isEmpty(postFilters.getMaxPrice()))
                params.put("max_price", ""+postFilters.getMaxPrice());
            
            if (postFilters.getMinPrice() != null  &&  !TextUtils.isEmpty(postFilters.getMinPrice()))
                params.put("min_price", ""+postFilters.getMinPrice());
            
            if (postFilters.getMinPrice() != null  &&  postFilters.getOnSale().equalsIgnoreCase("true"))
                params.put("on_sale", ""+postFilters.getOnSale());
            
            if (postFilters.getMinPrice() != null  &&  postFilters.getFeatured().equalsIgnoreCase("true"))
                params.put("featured", ""+postFilters.getFeatured());
            
            
            if (postFilters.getSelectedAttributes().size() > 0) {
                for (int i=0;  i<postFilters.getSelectedAttributes().size();  i++) {
                    
                    String attributeSlug = postFilters.getSelectedAttributes().get(i).getAttributeSlug();
                    String attributeTerms = "";
                    
                    if (postFilters.getSelectedAttributes().get(i).getAttributeTerms().size() > 0) {
                        
                        String[] attrTermsArray = new String[postFilters.getSelectedAttributes().get(i).getAttributeTerms().size()];
                        
                        for (int j=0;  j<postFilters.getSelectedAttributes().get(i).getAttributeTerms().size();  j++) {
                            attrTermsArray[j] = postFilters.getSelectedAttributes().get(i).getAttributeTerms().get(j).getSlug();
                        }
                        
                        attributeTerms = TextUtils.join(",", attrTermsArray);
                    }
                    
                    
                    if (!attributeTerms.equalsIgnoreCase(""))
                        params.put(attributeSlug, ""+attributeTerms);
                }
            }
        }
        
        
        Call<ProductFilters> call = APIClient.getInstance()
                .getFilters
                        (
                                params
                        );
        
        call.enqueue(new Callback<ProductFilters>() {
            @Override
            public void onResponse(Call<ProductFilters> call, retrofit2.Response<ProductFilters> response) {
                
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("ok")) {
                        
                        updateFilters(response.body(), postFilters);
                        
                    }
                    else if (response.body().getMessage() != null) {
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                        
                    }
                    else {
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Error : "+response.message(), Toast.LENGTH_SHORT).show();
                }
                
            }
            
            @Override
            public void onFailure(Call<ProductFilters> call, Throwable t) {
                Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    
    //*********** Request Products of given Category from the Server based on PageNo. ********//
    
    public void RequestCategoryProducts(final int pageNumber) {
    
        emptyRecord.setVisibility(View.GONE);
        
        Map<String, String> params = new LinkedHashMap<>();
        params.put("category", String.valueOf(categoryID));
        params.put("page", String.valueOf(pageNumber));
        params.put("order", order);
        params.put("orderby", sortBy);
        params.put("lang",ConstantValues.LANGUAGE_CODE);

        Call<List<ProductDetails>> call = APIClient.getInstance()
                .getAllProducts
                        (
                                params
                        );

        call.enqueue(new Callback<List<ProductDetails>>() {
            @Override
            public void onResponse(Call<List<ProductDetails>> call, retrofit2.Response<List<ProductDetails>> response) {
    
                // Hide the ProgressBar
                progressBar.setVisibility(View.GONE);
                loadingProgress.setVisibility(View.GONE);
                
                
                if (response.isSuccessful()) {
    
                    // checkoutTax = response.body();
                    String URL = response.raw().request().url().toString();
        
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
        
                    Toast.makeText(getContext(), "Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
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
    


    //*********** Request Products of given Category from the Server based on PageNo. against some Filters ********//
    
    public void RequestFilteredProducts(final int pageNumber, PostFilters filters) {
    
        emptyRecord.setVisibility(View.GONE);
    
        Map<String, String> params = new LinkedHashMap<>();
        params.put("category", String.valueOf(categoryID));
        params.put("page", String.valueOf(pageNumber));
        params.put("orderby", sortBy);
        params.put("order", order);
        params.put("lang",ConstantValues.LANGUAGE_CODE);
    
        if (filters.getMaxPrice() != null  &&  !TextUtils.isEmpty(filters.getMaxPrice())  &&  !"0".equalsIgnoreCase(filters.getMaxPrice()))
            params.put("max_price", ""+filters.getMaxPrice());
    
        if (filters.getMinPrice() != null  &&  !TextUtils.isEmpty(filters.getMinPrice()))
            params.put("min_price", ""+filters.getMinPrice());
    
        if (filters.getOnSale().equalsIgnoreCase("true"))
            params.put("on_sale", ""+filters.getOnSale());
    
        if (filters.getFeatured().equalsIgnoreCase("true"))
            params.put("featured", ""+filters.getFeatured());
    
    
        if (filters.getSelectedAttributes().size() > 0) {
            for (int i=0;  i<filters.getSelectedAttributes().size();  i++) {
            
                String attributeSlug = filters.getSelectedAttributes().get(i).getAttributeSlug();
                String attributeTerms = "";
            
                if (filters.getSelectedAttributes().get(i).getAttributeTerms().size() > 0) {
                
                    String[] attrTermsArray = new String[filters.getSelectedAttributes().get(i).getAttributeTerms().size()];
                
                    for (int j=0;  j<filters.getSelectedAttributes().get(i).getAttributeTerms().size();  j++) {
                        attrTermsArray[j] = filters.getSelectedAttributes().get(i).getAttributeTerms().get(j).getSlug();
                    }
                
                    attributeTerms = TextUtils.join(",", attrTermsArray);
                }
            
            
                if (!attributeTerms.equalsIgnoreCase(""))
                    params.put(attributeSlug, ""+attributeTerms);
            }
        }
        
    
        Call<List<ProductDetails>> call = APIClient.getInstance()
                .getAllProducts
                        (
                                params
                        );

        call.enqueue(new Callback<List<ProductDetails>>() {
            @Override
            public void onResponse(Call<List<ProductDetails>> call, retrofit2.Response<List<ProductDetails>> response) {
    
                // Hide the ProgressBar
                progressBar.setVisibility(View.GONE);
                loadingProgress.setVisibility(View.GONE);
                
                
                if (response.isSuccessful()) {
                
                    addProducts(response.body());
                    
                }
                else {
                    Toast.makeText(getContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
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
            if (isFilterApplied) {
                // Request for Products against specified Filters, based on PageNo.
                RequestFilteredProducts(page_number, filters);
            }
            else {
                // Request for Products of given Category, based on PageNo.
                RequestCategoryProducts(page_number);
            }

            return "All Done!";
        }


        //*********** Runs on the UI thread after #doInBackground() ********//

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

}
