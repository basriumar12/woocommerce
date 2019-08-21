package com.vectorcoder.androidwoocommerce.adapters;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.customs.FilterDialog;
import com.vectorcoder.androidwoocommerce.models.product_filters_model.FilterDetails;
import com.vectorcoder.androidwoocommerce.models.product_filters_model.FilterTerm;

import java.util.ArrayList;
import java.util.List;



/**
 * FilterProductsAdapter is the adapter class of RecyclerView holding List of Filters in FilterDialog
 **/

public class FiltersAdapter extends RecyclerView.Adapter<FiltersAdapter.MyViewHolder>{

    Context context;
    List<FilterDetails> filtersList;
    FilterTermsAdapter filterTermsAdapter;
    
    FilterDialog filterDialog;


    public FiltersAdapter(Context context, FilterDialog filterDialog) {
        this.context = context;
        this.filterDialog = filterDialog;
        this.filtersList = this.filterDialog.filterDetailsList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_filters, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        FilterDetails filterDetails = filtersList.get(position);
    
        List<FilterTerm> filterTermsList = new ArrayList<>();
        filterTermsList = filterDetails.getAttributeTerms();

        
        holder.filter_name.setText(filterDetails.getAttributeName());


        // Adapter for Attribute Values
        filterTermsAdapter = new FilterTermsAdapter(context, filterDialog, filterTermsList);
        holder.filter_attributes_recycler.setLayoutManager(new LinearLayoutManager(context));

        // Set adapter to AttributeValues RecyclerView
        holder.filter_attributes_recycler.setAdapter(filterTermsAdapter);
        
    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return filtersList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView filter_name;
        public RecyclerView filter_attributes_recycler;


        public MyViewHolder(final View itemView) {
            super(itemView);
            filter_name = (TextView) itemView.findViewById(R.id.filter_name);
            filter_attributes_recycler = (RecyclerView) itemView.findViewById(R.id.filter_attributes_recycler);
        }
    }
}

