package com.vectorcoder.androidwoocommerce.adapters;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.fragments.Product_Description;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductDetails;

import java.text.DecimalFormat;
import java.util.List;


/**
 * GroupedProductsAdapter is the adapter class of RecyclerView holding List of Grouped Products in Product_Description
 **/

public class GroupedProductsAdapter extends RecyclerView.Adapter<GroupedProductsAdapter.MyViewHolder> {

    private Context context;
    private List<ProductDetails> groupedProductsList;
    private Product_Description product_desc_Fragment;


    public GroupedProductsAdapter(Context context, List<ProductDetails> groupedProductsList, Product_Description product_desc_Fragment) {
        this.context = context;
        this.groupedProductsList = groupedProductsList;
        this.product_desc_Fragment = product_desc_Fragment;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
    
        View itemView;
    
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grouped_products, parent, false);
        
        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
    
        // Get the data model based on Position
        final ProductDetails productDetails = groupedProductsList.get(position);
        
    
        // Set Product Image on ImageView with Glide Library
        Glide.with(context).load(productDetails.getImages().get(0).getSrc()).into(holder.product_cover);
        
        holder.product_title.setText(productDetails.getName());
    
        double totalPrice = 0.00;
        if (productDetails.getPrice() != null  &&  !TextUtils.isEmpty(productDetails.getPrice())) {
            totalPrice = Double.parseDouble(productDetails.getPrice()) * productDetails.getCustomersBasketQuantity();
        }
        else {
            productDetails.setPrice(String.valueOf(totalPrice));
        }
    
        productDetails.setTotalPrice(String.valueOf(totalPrice));
        
        holder.product_total_price.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(totalPrice));
    
    
        String productPrice = productDetails.getPriceHtml();
        String price_styleSheet =   "<style> " +
                                        "body{margin:0; padding:0} " +
                                        "p{color:#757575;} " +
                                        "img{display:inline; height:auto; max-width:100%;}" +
                                    "</style>";
    
        holder.product_price_webView.setHorizontalScrollBarEnabled(false);
        holder.product_price_webView.setBackgroundColor(Color.TRANSPARENT);
        holder.product_price_webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        holder.product_price_webView.loadDataWithBaseURL(null, price_styleSheet+productPrice, "text/html", "utf-8", null);
        
        
        if (productDetails.isInStock()) {
            holder.product_stock.setVisibility(View.GONE);
            holder.product_quantity.setVisibility(View.VISIBLE);
        }
        else {
            holder.product_stock.setVisibility(View.VISIBLE);
            holder.product_quantity.setVisibility(View.GONE);
        }
    
    
        // Holds Product Quantity
        final int[] number = {1};
        number[0] = productDetails.getCustomersBasketQuantity();
        holder.product_quantity_text.setText(String.valueOf(productDetails.getCustomersBasketQuantity()));
    
    
        // Decrease Product Quantity
        holder.product_quantity_minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the Quantity is greater than the minimum Quantity
                if (number[0] >= 1)
                {
                    // Decrease Quantity by 1
                    number[0] = number[0] - 1;
                    holder.product_quantity_text.setText(""+ number[0]);
                
                    // Calculate Product Price with selected Quantity
                    double price = Double.parseDouble(productDetails.getPrice()) * number[0];
                
                    // Set Final Price and Quantity
                    productDetails.setTotalPrice(""+ price);
                    productDetails.setProductsFinalPrice(""+ price);
                    productDetails.setCustomersBasketQuantity(number[0]);
                
                    notifyItemChanged(holder.getAdapterPosition());
    
                    updateGroupQuantity();
    
                    holder.product_total_price.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(price));
                }
            }
        });
    
    
        // Increase Product Quantity
        holder.product_quantity_plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the Quantity is less than the maximum or stock Quantity
                if (productDetails.getStockQuantity() == null ||  number[0] < Long.parseLong(productDetails.getStockQuantity())) {
                    // Increase Quantity by 1
                    number[0] = number[0] + 1;
                    holder.product_quantity_text.setText(""+ number[0]);
                
                    // Calculate Product Price with selected Quantity
                    double price = Double.parseDouble(productDetails.getPrice()) * number[0];
                
                    // Set Final Price and Quantity
                    productDetails.setTotalPrice(""+ price);
                    productDetails.setProductsFinalPrice(""+ price);
                    productDetails.setCustomersBasketQuantity(number[0]);
                    
                
                    notifyItemChanged(holder.getAdapterPosition());
    
                    updateGroupQuantity();
    
                    holder.product_total_price.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(price));
                }
            }
        });
    
    
        // View Product Details
        holder.product_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                // Get Product Info
                Bundle itemInfo = new Bundle();
                itemInfo.putParcelable("productDetails", productDetails);
    
                Fragment fragment = new Product_Description();;
    
                fragment.setArguments(itemInfo);
                MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
            }
        });
    
    
    
        // Delete relevant Product from Cart
        holder.product_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
    
                // Get Product Info
                Bundle itemInfo = new Bundle();
                itemInfo.putParcelable("productDetails", productDetails);
    
                Fragment fragment = new Product_Description();;
    
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
        return groupedProductsList.size();
    }
    
    
    
    //********** Returns the total number of items in the data set *********//
    
    private void updateGroupQuantity() {
        
        boolean isCartEnabled = false;
        
        for (int i=0;  i<groupedProductsList.size();  i++) {
            if (groupedProductsList.get(i).getCustomersBasketQuantity() > 0) {
                isCartEnabled = true;
                break;
            }
        }
        
        product_desc_Fragment.toggleCartButton(isCartEnabled);
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder {
    
        private LinearLayout product_quantity;
        private ImageView product_cover;
        private WebView product_price_webView;
        private ImageButton product_quantity_minusBtn, product_quantity_plusBtn;
        private TextView product_title, product_quantity_text, product_total_price, product_stock;


        public MyViewHolder(final View itemView) {
            super(itemView);
    
            product_cover = (ImageView) itemView.findViewById(R.id.group_product_cover);
            product_quantity = (LinearLayout) itemView.findViewById(R.id.product_quantity);
            product_stock = (TextView) itemView.findViewById(R.id.product_stock);
            product_title = (TextView) itemView.findViewById(R.id.group_product_title);
            product_quantity_text = (TextView) itemView.findViewById(R.id.group_product_quantity);
            product_total_price = (TextView) itemView.findViewById(R.id.group_product_total_price);
            product_price_webView = (WebView) itemView.findViewById(R.id.group_product_price_webView);
            product_quantity_plusBtn = (ImageButton) itemView.findViewById(R.id.group_product_quantity_plusBtn);
            product_quantity_minusBtn = (ImageButton) itemView.findViewById(R.id.group_product_quantity_minusBtn);
    
            product_stock.setVisibility(View.GONE);
            product_quantity.setVisibility(View.VISIBLE);
        }
        
    }

}

