package com.vectorcoder.androidwoocommerce.adapters;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.models.cart_model.CartDetails;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductAttributes;

import java.util.ArrayList;
import java.util.List;


/**
 * CheckoutItemsAdapter is the adapter class of RecyclerView holding List of Products to be Ordered in Checkout
 **/

public class CheckoutItemsAdapter extends RecyclerView.Adapter<CheckoutItemsAdapter.MyViewHolder> {

    private Context context;
    private List<CartDetails> checkoutItemsList;
    
    private ProductAttributeValuesAdapter attributesAdapter;


    public CheckoutItemsAdapter(Context context, List<CartDetails> checkoutItemsList) {
        this.context = context;
        this.checkoutItemsList = checkoutItemsList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_checkout_items, parent, false);

        return new MyViewHolder(itemView);
    }
    

    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // Get the data model based on Position
        final CartDetails cartProduct = checkoutItemsList.get(position);

        // Set Product Image on ImageView with Glide Library
        Glide.with(context).load(ConstantValues.WOOCOMMERCE_URL+ cartProduct.getCartProduct().getProductImage()).into(holder.checkout_item_cover);

        holder.checkout_item_title.setText(cartProduct.getCartProduct().getName());
        holder.checkout_item_quantity.setText(String.valueOf(cartProduct.getCartProduct().getCustomersBasketQuantity()));
        holder.checkout_item_price.setText(ConstantValues.CURRENCY_SYMBOL + cartProduct.getCartProduct().getPrice());
        holder.checkout_item_price_final.setText(ConstantValues.CURRENCY_SYMBOL + cartProduct.getCartProduct().getTotalPrice());
        holder.checkout_item_category.setText(cartProduct.getCartProduct().getCategoryNames());
    
    
      //  List<Value> selectedAttributeValues= new ArrayList<>();
        List<ProductAttributes> productAttributes= new ArrayList<>();
    
        /*productAttributes = cartProduct.getCustomersBasketProductAttributes();
    
        for (int i=0;  i<productAttributes.size();  i++) {
            selectedAttributeValues.add(productAttributes.get(i).getValues().get(0));
        }
    
        // Initialize the ProductAttributeValuesAdapter for RecyclerView
        attributesAdapter = new ProductAttributeValuesAdapter(context, selectedAttributeValues);*/
    
        // Set the Adapter and LayoutManager to the RecyclerView
        holder.attributes_recycler.setAdapter(attributesAdapter);
        holder.attributes_recycler.setLayoutManager(new LinearLayoutManager(context));
    
        attributesAdapter.notifyDataSetChanged();
        

    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return checkoutItemsList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {
    
        private ImageView checkout_item_cover;
        private RecyclerView attributes_recycler;
        private TextView checkout_item_title, checkout_item_quantity, checkout_item_price, checkout_item_price_final, checkout_item_category;


        public MyViewHolder(final View itemView) {
            super(itemView);

            checkout_item_cover = (ImageView) itemView.findViewById(R.id.checkout_item_cover);
            checkout_item_title = (TextView) itemView.findViewById(R.id.checkout_item_title);
            checkout_item_quantity = (TextView) itemView.findViewById(R.id.checkout_item_quantity);
            checkout_item_price = (TextView) itemView.findViewById(R.id.checkout_item_price);
            checkout_item_price_final = (TextView) itemView.findViewById(R.id.checkout_item_price_final);
            checkout_item_category = (TextView) itemView.findViewById(R.id.checkout_item_category);
    
            attributes_recycler = (RecyclerView) itemView.findViewById(R.id.order_item_attributes_recycler);
        }
    }

}

