package com.vectorcoder.androidwoocommerce.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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

import com.vectorcoder.androidwoocommerce.adapters.NewsListAdapter;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.models.api_response_model.ErrorResponse;
import com.vectorcoder.androidwoocommerce.models.post_model.PostDetails;
import com.vectorcoder.androidwoocommerce.network.APIClient;
import com.vectorcoder.androidwoocommerce.customs.DividerItemDecoration;
import com.vectorcoder.androidwoocommerce.customs.EndlessRecyclerViewScroll;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;


public class News_of_Category extends Fragment {

    View rootView;

    int pageNo = 1;
    Boolean isHeaderVisible = false;

    int postCategoryID;

    ProgressBar progressBar;
    TextView emptyText, headerText;
    RecyclerView news_recycler;

    NewsListAdapter newsAdapter;
    List<PostDetails> postsList;

    GridLayoutManager gridLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.news_all, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

        // Set the Title of Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getArguments().getString("postCategoryName", getString(R.string.actionNews)));


        // Get CategoryID from Bundle arguments
        postCategoryID = getArguments().getInt("postCategoryID");

        if (getArguments() != null) {
            if (getArguments().containsKey("isHeaderVisible")) {
                isHeaderVisible = getArguments().getBoolean("isHeaderVisible");
            }
        }


        // Binding Layout Views
        headerText = (TextView) rootView.findViewById(R.id.news_header);
        emptyText = (TextView) rootView.findViewById(R.id.empty_record_text);
        progressBar = (ProgressBar) rootView.findViewById(R.id.loading_bar);
        news_recycler = (RecyclerView) rootView.findViewById(R.id.news_recycler);


        // Hide some of the Views
        emptyText.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        // Check if Header must be Invisible or not
        if (isHeaderVisible) {
            headerText.setText(getString(R.string.news_all));

        } else {
            // Hide the Header of CategoriesList
            headerText.setVisibility(View.GONE);
        }


        // Initialize PostsList
        postsList = new ArrayList<>();

        // Request for Products based on PageNo.
        RequestAllNews(pageNo);


        gridLayoutManager = new GridLayoutManager(getContext(), 1);

        // Initialize the ProductAdapter for RecyclerView
        newsAdapter = new NewsListAdapter(getContext(), postsList);

        // Set the Adapter and LayoutManager to the RecyclerView
        news_recycler.setAdapter(newsAdapter);
        news_recycler.setLayoutManager(gridLayoutManager);
        news_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));



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


        newsAdapter.notifyDataSetChanged();


        return rootView;
    }
    
    
    
    //*********** Adds News to the NewsList ********//
    
    private void addNews(List<PostDetails> posts) {
        
        for (int i = 0; i < posts.size(); i++) {
            postsList.add(posts.get(i));
        }
        
        
        newsAdapter.notifyDataSetChanged();
        
        
        // Change the Visibility of emptyRecord Text based on ProductList's Size
        if (newsAdapter.getItemCount() == 0) {
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
        }
    }



    //*********** Request News from the Server based on PageNo. ********//

    public void RequestAllNews(int pageNumber) {
    
        Map<String, String> params = new LinkedHashMap<>();
        params.put("page", String.valueOf(pageNumber));
        params.put("categories", String.valueOf(postCategoryID));
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
    
                    addNews(response.body());
                
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
            public void onFailure(Call<List<PostDetails>> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
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
            RequestAllNews(page_number);

            return "All Done!";
        }


        //*********** Runs on the UI thread after #doInBackground() ********//

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

}