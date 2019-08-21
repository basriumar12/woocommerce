package com.vectorcoder.androidwoocommerce.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.R;


import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vectorcoder.androidwoocommerce.adapters.CategoryListAdapter_5;
import com.vectorcoder.androidwoocommerce.adapters.ProductAdapter;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.customs.DialogLoader;
import com.vectorcoder.androidwoocommerce.models.api_response_model.ErrorResponse;
import com.vectorcoder.androidwoocommerce.models.category_model.CategoryDetails;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductDetails;
import com.vectorcoder.androidwoocommerce.network.APIClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;


public class SearchFragment extends Fragment {

    View rootView;
    
    //AdView mAdView;
    SearchView search_editText;
    FrameLayout banner_adView;
    RecyclerView categories_recycler, products_recycler;

    List<CategoryDetails> allCategoriesList;
    List<CategoryDetails> subCategoriesList;
    List<ProductDetails> searchedProductsList;

    DialogLoader dialogLoader;
    ProductAdapter searchProductsAdapter;
    CategoryListAdapter_5 categoryListAdapter;
    
    public static boolean FLAG_SEARCHED;

    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.search_fragment, container, false);

        setHasOptionsMenu(true);

        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.actionSearch));
        
        
        // Binding Layout Views
        banner_adView = (FrameLayout) rootView.findViewById(R.id.banner_adView);
        search_editText = (SearchView) rootView.findViewById(R.id.search_editText);
        products_recycler = (RecyclerView) rootView.findViewById(R.id.products_recycler);
        categories_recycler = (RecyclerView) rootView.findViewById(R.id.categories_recycler);
    
        banner_adView.setVisibility(View.GONE);
//
//        if (ConstantValues.IS_ADMOBE_ENABLED) {
//            // Initialize Admobe
//            mAdView = new AdView(getContext());
//            mAdView.setAdSize(AdSize.BANNER);
//            mAdView.setAdUnitId(ConstantValues.AD_UNIT_ID_BANNER);
//            AdRequest adRequest = new AdRequest.Builder().build();
//            banner_adView.addView(mAdView);
//            mAdView.loadAd(adRequest);
//            banner_adView.setVisibility(View.GONE);
//        }
        
        
        products_recycler.setNestedScrollingEnabled(false);
        categories_recycler.setNestedScrollingEnabled(false);
        
        // Hide some of the Views
        products_recycler.setVisibility(View.GONE);
        categories_recycler.setVisibility(View.GONE);
    
    
        dialogLoader = new DialogLoader(getContext());
        
        
        subCategoriesList = new ArrayList<>();
        searchedProductsList = new ArrayList<>();
        
        
        // Get All CategoriesList from ApplicationContext
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();
        
        for (int i=0;  i<allCategoriesList.size();  i++) {
            if (allCategoriesList.get(i).getId() != 0) {
                subCategoriesList.add(allCategoriesList.get(i));
            }
        }
    
    
        // Initialize the SearchResultsAdapter for RecyclerView
        searchProductsAdapter = new ProductAdapter(getContext(), searchedProductsList, false);
        searchProductsAdapter.toggleLayout(false);
    
        // Set the Adapter, LayoutManager and ItemDecoration to the RecyclerView
        products_recycler.setAdapter(searchProductsAdapter);
        products_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    
//        searchProductsAdapter.toggleLayout(false);
        searchProductsAdapter.notifyDataSetChanged();
        
    
        // Initialize the CategoryListAdapter for RecyclerView
        categoryListAdapter = new CategoryListAdapter_5(getContext(), subCategoriesList, false);
    
        // Set the Adapter and LayoutManager to the RecyclerView
        categories_recycler.setAdapter(categoryListAdapter);
        categories_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    
        categoryListAdapter.notifyDataSetChanged();
        
        // Show Categories
        showCategories();
        
        
        search_editText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
               // search_editText.clearFocus();
                RequestSearchData(s);
                FLAG_SEARCHED = true;
                return false;
            }
    
            @Override
            public boolean onQueryTextChange(String s) {
    
                if(s.isEmpty()){
                    showCategories();
                    FLAG_SEARCHED = false;
                }
                else if (!s.isEmpty() && s.length() > 2) {
                    if(FLAG_SEARCHED) {
                        RequestSearchData(s);
                    }
                    //return true;
                }
                
                return false;
            }
        });
    
      /*  search_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               
                if (search_editText.getText().toString().isEmpty()) {
                    // Show Categories
                    showCategories();
                    FLAG_SEARCHED = false;
                }
                else {
                    if(FLAG_SEARCHED) {
                        if (!search_editText.getText().toString().isEmpty() || search_editText.getText().toString().length() > 0) {
                            RequestSearchData(search_editText.getText().toString());
                          
                            //return true;
                        }
                    }
                }
                
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        
        // Set listener to be called when an action is performed on the search_editText
        search_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        FLAG_SEARCHED = true;
                        if (!search_editText.getText().toString().isEmpty() || search_editText.getText().toString().length() > 0) {
                            RequestSearchData(search_editText.getText().toString());
                           // FLAG_SEARCHED = true;
                            return true;
                        }
                }
                return false;
            }
        });
*/

        return rootView;
    }



    //*********** Show Main Categories in the SearchList ********//

    private void showCategories() {

        // Make CategoriesList Visible
        categories_recycler.setVisibility(View.VISIBLE);
        products_recycler.setVisibility(View.GONE);
    
        searchedProductsList.clear();
        
        categoryListAdapter.notifyDataSetChanged();
        searchProductsAdapter.notifyDataSetChanged();

    }



    //*********** Adds SearchResults returned from the Server to the resultsList ********//

    private void addResults(List<ProductDetails> productsList) {
        
        if (productsList.size() > 0) {
            
            searchedProductsList.clear();
            searchedProductsList.addAll(productsList);
    
            for (int i=0;  i<productsList.size();  i++) {
                if (productsList.get(i).getStatus() != null  &&  !"publish".equalsIgnoreCase(productsList.get(i).getStatus())) {
                    for (int j=0;  j<searchedProductsList.size();  j++) {
                        if (productsList.get(i).getId() == searchedProductsList.get(j).getId()) {
                            searchedProductsList.remove(j);
                        }
                    }
                }
            }
    
            searchProductsAdapter.notifyDataSetChanged();
    
            dialogLoader.hideProgressDialog();
            // Make CategoriesList Visible
            categories_recycler.setVisibility(View.GONE);
            products_recycler.setVisibility(View.VISIBLE);
            
            
        }
        else {
            showCategories();
            dialogLoader.hideProgressDialog();
            Snackbar.make(rootView, getString(R.string.record_not_found), Snackbar.LENGTH_SHORT).show();
        }
    }



    //*********** Request Search Results from the Server based on the given Query ********//

    public void RequestSearchData(String searchValue) {
    
        dialogLoader.showProgressDialog();
        
        Map<String, String> params = new LinkedHashMap<>();
        params.put("per_page", "100");
        params.put("search", searchValue);
       params.put("lang",ConstantValues.LANGUAGE_CODE);
    
        Call<List<ProductDetails>> call = APIClient.getInstance()
                .getAllProducts
                        (
                                params
                        );
    
        call.enqueue(new Callback<List<ProductDetails>>() {
            @Override
            public void onResponse(Call<List<ProductDetails>> call, retrofit2.Response<List<ProductDetails>> response) {
                
                if (response.isSuccessful()) {
                    
                    addResults(response.body());
                
                }
                else {
                    Converter<ResponseBody, ErrorResponse> converter = APIClient.retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                    ErrorResponse error;
                    try {
                        error = converter.convert(response.errorBody());
                    } catch (IOException e) {
                        error = new ErrorResponse();
                    }
    
                    dialogLoader.hideProgressDialog();
                
                    if (error.getMessage() != null) {
                        Toast.makeText(getContext(), "Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Snackbar.make(rootView, getString(R.string.record_not_found), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        
            @Override
            public void onFailure(Call<List<ProductDetails>> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Hide Search Icon in the Toolbar
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        cartItem.setVisible(true);
        searchItem.setVisible(false);
    }

}



