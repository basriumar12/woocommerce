package com.vectorcoder.androidwoocommerce.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.R;

import java.text.DecimalFormat;
import java.util.List;

import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.fragments.Order_Details;
import com.vectorcoder.androidwoocommerce.models.order_model.OrderDetails;


/**
 * OrdersListAdapter is the adapter class of RecyclerView holding List of Orders in My_Orders
 **/

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.MyViewHolder> {

    Context context;
    List<OrderDetails> ordersList;


    public OrdersListAdapter(Context context, List<OrderDetails> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_orders, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        
        // Get the data model based on Position
        final OrderDetails orderDetails = ordersList.get(position);
    
        int noOfProducts = 0;
        for (int i=0;  i<orderDetails.getOrderProducts().size();  i++) {
            // Count no of Products
            noOfProducts += orderDetails.getOrderProducts().get(i).getQuantity();
        }

        holder.order_id.setText(String.valueOf(orderDetails.getId()));
        holder.order_status.setText(orderDetails.getStatus());
        holder.order_date.setText(orderDetails.getDateCreated().replaceAll("[a-zA-Z]", " "));
        holder.order_product_count.setText(String.valueOf(noOfProducts));
        holder.order_price.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getTotal())));
    

        // Check Order's status
        if (orderDetails.getStatus().equalsIgnoreCase("processing")) {
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccentBlue));
        } else if (orderDetails.getStatus().equalsIgnoreCase("on-hold")) {
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccentBlue));
        } else if (orderDetails.getStatus().equalsIgnoreCase("completed")) {
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccentGreen));
        } else if (orderDetails.getStatus().equalsIgnoreCase("cancelled")) {
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccentRed));
        } else if (orderDetails.getStatus().equalsIgnoreCase("refunded")) {
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccentGreen));
        } else if (orderDetails.getStatus().equalsIgnoreCase("failed")) {
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccentRed));
        } else {// pending
            holder.order_status.setTextColor(ContextCompat.getColor(context, R.color.colorAccentBlue));
        }
    
        
    
        holder.order_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Order Info
                Bundle itemInfo = new Bundle();
                itemInfo.putInt("orderID", orderDetails.getId());
    
                // Navigate to Order_Details Fragment
                Fragment fragment = new Order_Details();
                fragment.setArguments(itemInfo);
                MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            }
        });

    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return ordersList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private Button order_view_btn;
        private TextView order_id, order_product_count, order_status, order_price, order_date;


        public MyViewHolder(final View itemView) {
            super(itemView);
    
            order_view_btn = (Button) itemView.findViewById(R.id.order_view_btn);
            order_id = (TextView) itemView.findViewById(R.id.order_id);
            order_product_count = (TextView) itemView.findViewById(R.id.order_products_count);
            order_status = (TextView) itemView.findViewById(R.id.order_status);
            order_price = (TextView) itemView.findViewById(R.id.order_price);
            order_date = (TextView) itemView.findViewById(R.id.order_date);
        }
    }
}

