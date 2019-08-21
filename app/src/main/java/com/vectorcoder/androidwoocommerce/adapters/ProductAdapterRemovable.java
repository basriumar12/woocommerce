package com.vectorcoder.androidwoocommerce.adapters;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.R;

import java.util.List;

import com.vectorcoder.androidwoocommerce.databases.User_Favorites_DB;
import com.vectorcoder.androidwoocommerce.utils.Utilities;
import com.vectorcoder.androidwoocommerce.databases.User_Recents_DB;
import com.vectorcoder.androidwoocommerce.fragments.Product_Description;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductDetails;


/**
 * ProductAdapterRemovable is the adapter class of RecyclerView holding List of Products in RecentlyViewed and WishList
 **/

public class ProductAdapterRemovable extends RecyclerView.Adapter<ProductAdapterRemovable.MyViewHolder> {

    private Context context;
    private TextView emptyText;

    private boolean isRecents;
    private boolean isHorizontal;

    private List<ProductDetails> productList;

    private User_Recents_DB recents_db;
    private User_Favorites_DB favorites_db;


    public ProductAdapterRemovable(Context context, List<ProductDetails> productList, boolean isHorizontal, boolean isRecents, TextView emptyText) {
        this.context = context;
        this.productList = productList;
        this.isHorizontal = isHorizontal;
        this.isRecents = isRecents;
        this.emptyText = emptyText;

        recents_db = new User_Recents_DB();
        favorites_db = new User_Favorites_DB();
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = null;

        if (isHorizontal) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_grid_sm, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_grid_lg, parent, false);
        }

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        // Get the data model based on Position
        final ProductDetails product = productList.get(position);

        holder.product_checked.setVisibility(View.GONE);


        // Set Product Image on ImageView with Glide Library
        Glide.with(context)
                .load(product.getImages().get(0).getSrc())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.cover_loader.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.cover_loader.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.product_cover);


        holder.product_title.setText(product.getName());
    
        // Set WebView for Product's Price
        String styleSheet = "<style> " +
                                "body{margin:0; padding:0; color:#000000; font-size:0.85em;} " +
                                "img{display:inline; height:auto; max-width:100%;}" +
                            "</style>";
    
        holder.product_price_webView.setVerticalScrollBarEnabled(false);
        holder.product_price_webView.setHorizontalScrollBarEnabled(false);
        holder.product_price_webView.setBackgroundColor(Color.TRANSPARENT);
        holder.product_price_webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        holder.product_price_webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        holder.product_price_webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        holder.product_price_webView.loadDataWithBaseURL(null, styleSheet+product.getPriceHtml(), "text/html", "utf-8", null);
    
    
    
        if (Utilities.checkNewProduct(product.getDateCreated())) {
            holder.product_tag_new.setVisibility(View.VISIBLE);
        }
        else {
            holder.product_tag_new.setVisibility(View.GONE);
        }
        
        
        if (product.isOnSale()) {
            holder.product_tag_sale.setVisibility(View.VISIBLE);
        }
        else {
            holder.product_tag_sale.setVisibility(View.GONE);
        }
        
        
        if (product.isFeatured()) {
            holder.product_tag_featured.setVisibility(View.VISIBLE);
        }
        else {
            holder.product_tag_featured.setVisibility(View.GONE);
        }
    
    
        holder.product_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoProductDetails(product);
            }
        });
    
        // Handle the Click event of product_thumbnail ImageView
        holder.product_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoProductDetails(product);
            }
        });
    
    
        // Handle the Click event of product_checked ImageView
        holder.product_checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoProductDetails(product);
            }
        });
    
    
        holder.product_remove_btn.setVisibility(View.VISIBLE);
    
        // Handle Click event of product_remove_btn Button
        holder.product_remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            
                // Check if Product is in User's Recents
                if (isRecents) {
                    // Delete Product from User_Recents_DB Local Database
                    recents_db.deleteRecentItem(product.getId());
                
                } else {
                    // Remove the Product from User's Favorites
                    if (favorites_db.getUserFavorites().contains(product.getId())) {
                        favorites_db.deleteFavoriteItem(product.getId());
                    }
                }
            
                // Remove Product from productList List
                productList.remove(holder.getAdapterPosition());
            
                notifyItemRemoved(holder.getAdapterPosition());
            
                // Update View
                updateView(isRecents);
            }
        });

    }



    //********** Returns the total number of items in the RecyclerView *********//

    @Override
    public int getItemCount() {
        return productList.size();
    }



    //********** Change the Layout View based on the total number of items in the RecyclerView *********//

    public void updateView(boolean isRecentProducts) {


        // Check if Product is in User's Recents
        if (isRecentProducts) {

            // Check if RecyclerView has some Items
            if (getItemCount() != 0) {
                emptyText.setVisibility(View.VISIBLE);
            } else {
                emptyText.setVisibility(View.GONE);
            }

        } else {

            // Check if RecyclerView has some Items
            if (getItemCount() != 0) {
                emptyText.setVisibility(View.GONE);
            } else {
                emptyText.setVisibility(View.VISIBLE);
            }
        }
        
        notifyDataSetChanged();

    }
    
    
    private void gotoProductDetails(ProductDetails product) {
        // Get Product Info
        Bundle itemInfo = new Bundle();
        itemInfo.putParcelable("productDetails", product);
        
        Fragment fragment = new Product_Description();
        
        fragment.setArguments(itemInfo);
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();
        
        
        // Add the Product to User's Recently Viewed Products
        if (!recents_db.getUserRecents().contains(product.getId())) {
            recents_db.insertRecentItem(product.getId());
        }
    }


    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder {
    
        ProgressBar cover_loader;
        Button product_remove_btn;
        WebView product_price_webView;
        ToggleButton product_like_btn;
        ImageView product_cover, product_checked, product_tag_new;
        TextView product_title, product_tag_sale, product_tag_featured;

        public MyViewHolder(final View itemView) {
            super(itemView);

            product_checked = (ImageView) itemView.findViewById(R.id.product_checked);
            cover_loader = (ProgressBar) itemView.findViewById(R.id.product_cover_loader);
    
            cover_loader = (ProgressBar) itemView.findViewById(R.id.product_cover_loader);
            product_checked = (ImageView) itemView.findViewById(R.id.product_checked);
            product_cover = (ImageView) itemView.findViewById(R.id.product_cover);
            product_tag_new = (ImageView) itemView.findViewById(R.id.product_tag_new);
            product_title = (TextView) itemView.findViewById(R.id.product_title);
            product_tag_sale = (TextView) itemView.findViewById(R.id.product_tag_sale);
            product_tag_featured = (TextView) itemView.findViewById(R.id.product_tag_featured);
            product_remove_btn = (Button) itemView.findViewById(R.id.product_card_Btn);
            product_like_btn = (ToggleButton) itemView.findViewById(R.id.product_like_btn);
            product_price_webView = (WebView) itemView.findViewById(R.id.product_price_webView);

            product_like_btn.setVisibility(View.GONE);
            product_remove_btn.setText(context.getString(R.string.removeProduct));
            product_remove_btn.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners_button_red));
        }
    }

}

