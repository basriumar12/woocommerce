package com.vectorcoder.androidwoocommerce.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductMetaData;

import java.util.List;


/**
 * ProductAdditionalValuesAdapter is the adapter class of RecyclerView holding List of Product Additional Values in Product_Description
 **/

public class ProductAdditionalValuesAdapter extends RecyclerView.Adapter<ProductAdditionalValuesAdapter.MyViewHolder> {

    Context context;
    private List<ProductMetaData> metaDataList;


    public ProductAdditionalValuesAdapter(Context context, List<ProductMetaData> metaDataList) {
        this.context = context;
        this.metaDataList = metaDataList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_meta_data, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        ProductMetaData metaData = metaDataList.get(position);
    
        holder.meta_data_key.setText(metaData.getKey());
        holder.meta_data_value.setText(metaData.getValue());
        
    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return metaDataList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView meta_data_key;
        TextView meta_data_value;


        public MyViewHolder(final View itemView) {
            super(itemView);
    
            meta_data_key = (TextView) itemView.findViewById(R.id.meta_data_key);
            meta_data_value = (TextView) itemView.findViewById(R.id.meta_data_value);
        }
    }

}

