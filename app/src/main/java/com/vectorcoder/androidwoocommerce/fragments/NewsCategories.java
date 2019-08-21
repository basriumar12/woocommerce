package com.vectorcoder.androidwoocommerce.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vectorcoder.androidwoocommerce.R;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vectorcoder.androidwoocommerce.adapters.NewsCategoriesAdapter;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.customs.EndlessRecyclerViewScroll;
import com.vectorcoder.androidwoocommerce.models.api_response_model.ErrorResponse;
import com.vectorcoder.androidwoocommerce.models.post_model.PostCategory;
import com.vectorcoder.androidwoocommerce.models.post_model.PostDetails;
import com.vectorcoder.androidwoocommerce.network.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import okhttp3.ResponseBody;


public class NewsCategories extends Fragment {

    View rootView;

    int pageNo = 1;
    Boolean isHeaderVisible = false;

    ProgressBar progressBar;
    TextView emptyText, headerText;
    RecyclerView news_recycler;
    
    List<PostCategory> postCategoriesList;
    NewsCategoriesAdapter newsCategoriesAdapter;

    GridLayoutManager gridLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.news_all, container, false);

        if (getArguments() != null) {
            if (getArguments().containsKey("isHeaderVisible")) {
                isHeaderVisible = getArguments().getBoolean("isHeaderVisible");
            }
        }

        // Set the Title of Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.news_categories));


        // Binding Layout Views
        headerText = (TextView) rootView.findViewById(R.id.news_header);
        emptyText = (TextView) rootView.findViewById(R.id.empty_record_text);
        progressBar = (ProgressBar) rootView.findViewById(R.id.loading_bar);
        news_recycler = (RecyclerView) rootView.findViewById(R.id.news_recycler);


        // Hide some of the Views
        emptyText.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        // Check if Header must be Invisible or not
        if (!isHeaderVisible) {
            // Hide the Header of CategoriesList
            headerText.setVisibility(View.GONE);
        } else {
            headerText.setText(getString(R.string.news_categories));
        }


        // Initialize ProductList
        postCategoriesList = new ArrayList<>();

        // Request for Products based on PageNo.
        RequestAllNewsCategories(pageNo);


        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        // Initialize the ProductAdapter for RecyclerView
        newsCategoriesAdapter = new NewsCategoriesAdapter(getContext(), postCategoriesList);

        // Set the Adapter and LayoutManager to the RecyclerView
        news_recycler.setAdapter(newsCategoriesAdapter);
        news_recycler.setLayoutManager(gridLayoutManager);



        // Handle the Scroll event of Product's RecyclerView
        news_recycler.addOnScrollListener(new EndlessRecyclerViewScroll() {
            // Override abstract method onLoadMore() of EndlessRecyclerViewScroll class
            @Override
            public void onLoadMore(int current_page) {
                progressBar.setVisibility(View.VISIBLE);
                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                new LoadMoreTask(current_page).execute();
            }
        });


        newsCategoriesAdapter.notifyDataSetChanged();


        return rootView;
    }
    
    
    
    //*********** Add NewsCategories returned to the NewsCategoriesList ********//
    
    private void addNewsCategories(List<PostCategory> postCategories) {
        
        for (int i = 0; i < postCategories.size(); i++) {
            if (postCategories.get(i).getCount() != 0)
                postCategoriesList.add(postCategories.get(i));
        }
    
        newsCategoriesAdapter.notifyDataSetChanged();
        
        
        for (int i = 0; i < postCategoriesList.size(); i++) {
            RequestAllNewsOfCategory(postCategoriesList.get(i).getId());
        }


        // Change the Visibility of emptyRecord Text based on ProductList's Size
        if (newsCategoriesAdapter.getItemCount() == 0) {
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
        }
    }



    //*********** Request News Categories from the Server based on PageNo. ********//

    public void RequestAllNewsCategories(int pageNumber) {
    
        Map<String, String> params = new LinkedHashMap<>();
        params.put("page", String.valueOf(pageNumber));
        params.put("lang",ConstantValues.LANGUAGE_CODE);
        
        Call<List<PostCategory>> call = APIClient.getInstance()
                .getPostCategories
                        (
                                params
                        );

        call.enqueue(new Callback<List<PostCategory>>() {
            @Override
            public void onResponse(Call<List<PostCategory>> call, retrofit2.Response<List<PostCategory>> response) {
    
                if (response.isSuccessful()) {
    
                    addNewsCategories(response.body());
        
                    // Hide the ProgressBar
                    progressBar.setVisibility(View.GONE);
        
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
            public void onFailure(Call<List<PostCategory>> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    
    //*********** Request News from the Server based on PageNo. ********//
    
    private void RequestAllNewsOfCategory(final int category_ID) {
        
        Map<String, String> params = new LinkedHashMap<>();
        params.put("page", "1");
        params.put("per_page", "100");
        params.put("categories", String.valueOf(category_ID));
        params.put("_embed", "true");
        params.put("lang",ConstantValues.LANGUAGE_CODE);
        
        
        Call<List<PostDetails>> call = APIClient.getInstance()
                .getAllPosts
                        (
                                params
                        );
        
        call.enqueue(new Callback<List<PostDetails>>() {
            @Override
            public void onResponse(Call<List<PostDetails>> call, retrofit2.Response<List<PostDetails>> response) {
                
                if (response.isSuccessful()) {
                    String imageURL = "";
                    List<PostDetails> categoryPosts = response.body();
                    
                    if (categoryPosts.size() > 0) {
                        if (categoryPosts.get(0).getEmbedded() != null  &&  categoryPosts.get(0).getEmbedded().getWpFeaturedmedia() != null) {
                            imageURL = categoryPosts.get(0).getEmbedded().getWpFeaturedmedia().get(0).getSourceUrl();
                        }
                        else if (categoryPosts.size() > 1) {
                            if (categoryPosts.get(1).getEmbedded() != null  &&  categoryPosts.get(1).getEmbedded().getWpFeaturedmedia() != null) {
                                imageURL = categoryPosts.get(1).getEmbedded().getWpFeaturedmedia().get(0).getSourceUrl();
                            }
                        }
                    }
    
                    for (int i = 0; i < postCategoriesList.size(); i++) {
                        if (category_ID == postCategoriesList.get(i).getId()) {
                            postCategoriesList.get(i).setImage(imageURL);
                        }
                    }
    
                    newsCategoriesAdapter.notifyDataSetChanged();
                }
            }
            
            @Override
            public void onFailure(Call<List<PostDetails>> call, Throwable t) {}
        });
    }



    /*********** LoadMoreTask Used to Load more Products from the Server in the Background Thread using AsyncTask ********/

    private class LoadMoreTask extends AsyncTask<String, Void, String> {

        int page_number;


        private LoadMoreTask(int page_number) {
            this.page_number = page_number;
        }


        //*********** Runs on the UI thread before #doInBackground() ********//

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        //*********** Performs some Processes on Background Thread and Returns a specified Result  ********//

        @Override
        protected String doInBackground(String... params) {

            // Request for Products based on PageNo.
            RequestAllNewsCategories(page_number);

            return "All Done!";
        }


        //*********** Runs on the UI thread after #doInBackground() ********//

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

}