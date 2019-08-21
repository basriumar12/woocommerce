package com.vectorcoder.androidwoocommerce.adapters;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.databases.User_Recents_DB;
import com.vectorcoder.androidwoocommerce.fragments.Checkout;
import com.vectorcoder.androidwoocommerce.fragments.My_Cart;
import com.vectorcoder.androidwoocommerce.fragments.Product_Description;
import com.vectorcoder.androidwoocommerce.models.cart_model.CartDetails;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductAttributes;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductMetaData;


/**
 * CartItemsAdapter is the adapter class of RecyclerView holding List of Cart Items in My_Cart
 **/

public class CartItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private My_Cart cartFragment;
    private Checkout checkoutFragment;
    private List<CartDetails> cartItemsList;

    private User_Recents_DB recents_db;
    
    private ProductAdditionalValuesAdapter metaDataAdapter;
    private static int LAYOUT_ONE= 0;

    public CartItemsAdapter(Context context, List<CartDetails> cartItemsList, My_Cart cartFragment) {
        this.context = context;
        this.cartItemsList = cartItemsList;
        this.cartFragment = cartFragment;
        recents_db = new User_Recents_DB();
        LAYOUT_ONE = 0;
    }
    
    public CartItemsAdapter(Context context, List<CartDetails> cartItemsList,Checkout checkoutFragment) {
        this.context = context;
        this.cartItemsList = cartItemsList;
        this.checkoutFragment = checkoutFragment;
        recents_db = new User_Recents_DB();
        LAYOUT_ONE = 1;
    }


    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        
        View view =null;
        RecyclerView.ViewHolder viewHolder = null;
    
        if(LAYOUT_ONE==0)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_cart_items, parent, false);
            viewHolder = new MyViewHolder0(view);
        }
        else
        {
            view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_item_checkout, parent, false);
            viewHolder= new MyViewHolder2(view);
        }
    
        return viewHolder;
        
    }
    
    
    //********** Called by RecyclerView to display the Data at the specified Position *********//
    
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holderAll, int position) {
        // Get the data model based on Position
        final CartDetails cartDetails = cartItemsList.get(position);
        
        if(LAYOUT_ONE==0)
        {
    
            final MyViewHolder0 holder = (MyViewHolder0) holderAll;
            // Set Product Image on ImageView with Glide Library
            Glide.with(context).load(cartDetails.getCartProduct().getImage()).into(holder.cart_item_cover);
    
            holder.cart_item_title.setText(cartDetails.getCartProduct().getName());
            holder.cart_item_category.setText(cartDetails.getCartProduct().getCategoryNames());
            holder.cart_item_quantity.setText("" + cartDetails.getCartProduct().getCustomersBasketQuantity());
            holder.cart_item_base_price.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(cartDetails.getCartProduct().getPrice())));
            holder.cart_item_sub_price.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(cartDetails.getCartProduct().getProductsFinalPrice())));
//        holder.cart_item_total_price.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(cartDetails.getCartProduct().getTotalPrice())));
    
    
            List<ProductMetaData> productMetaDataList = new ArrayList<>();
            List<ProductAttributes> productAttributesList = new ArrayList<>();
    
            productAttributesList = cartDetails.getCartProduct().getAttributes();
    
            for (int i = 0; i < productAttributesList.size(); i++) {
                ProductMetaData metaData = new ProductMetaData();
                metaData.setId(productAttributesList.get(i).getId());
                metaData.setKey(productAttributesList.get(i).getName());
                metaData.setValue(productAttributesList.get(i).getOption());
        
                productMetaDataList.add(metaData);
            }
    
            // Initialize the ProductAdditionalValuesAdapter for RecyclerView
            metaDataAdapter = new ProductAdditionalValuesAdapter(context, productMetaDataList);
    
            holder.attributes_recycler.setAdapter(metaDataAdapter);
            holder.attributes_recycler.setLayoutManager(new LinearLayoutManager(context));
    
            metaDataAdapter.notifyDataSetChanged();
    
    
            // Holds Product Quantity
            final int[] number = {1};
            number[0] = cartDetails.getCartProduct().getCustomersBasketQuantity();
    
    
            // Decrease Product Quantity
            holder.cart_item_quantity_minusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Check if the Quantity is greater than the minimum Quantity
                    if (number[0] > 1) {
                        // Decrease Quantity by 1
                        number[0] = number[0] - 1;
                        holder.cart_item_quantity.setText("" + number[0]);
                
                        // Calculate Product Price with selected Quantity
                        double price = Double.parseDouble(cartDetails.getCartProduct().getPrice()) * number[0];
                
                        // Set Final Price and Quantity
                        cartDetails.getCartProduct().setTotalPrice("" + price);
                        cartDetails.getCartProduct().setProductsFinalPrice("" + price);
                        cartDetails.getCartProduct().setCustomersBasketQuantity(number[0]);
                
                
                        // Update CartItem in Local Database using static method of My_Cart
                        My_Cart.UpdateCartItem
                                (
                                        cartDetails
                                );
                
                
                        notifyItemChanged(holder.getAdapterPosition());
                
                
                        // Calculate Cart's Total Price Again
                        cartFragment.updateCart();
                    }
                }
            });
    
    
            // Increase Product Quantity
            holder.cart_item_quantity_plusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Check if the Quantity is less than the maximum or stock Quantity
                    if (cartDetails.getCartProduct().getStockQuantity() == null || number[0] < Long.parseLong(cartDetails.getCartProduct().getStockQuantity())) {
                        // Increase Quantity by 1
                        number[0] = number[0] + 1;
                        holder.cart_item_quantity.setText("" + number[0]);
                
                        // Calculate Product Price with selected Quantity
                        double price = Double.parseDouble(cartDetails.getCartProduct().getPrice()) * number[0];
                
                        // Set Final Price and Quantity
                        cartDetails.getCartProduct().setTotalPrice("" + price);
                        cartDetails.getCartProduct().setProductsFinalPrice("" + price);
                        cartDetails.getCartProduct().setCustomersBasketQuantity(number[0]);
                
                
                        // Update CartItem in Local Database using static method of My_Cart
                        My_Cart.UpdateCartItem
                                (
                                        cartDetails
                                );
                
                
                        notifyItemChanged(holder.getAdapterPosition());
                
                
                        // Calculate Cart's Total Price Again
                        cartFragment.updateCart();
                    }
                }
            });
    
    
            // View Product Details
            holder.cart_item_viewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
            
                    int productID = cartDetails.getCartProduct().getId();
            
                    // Get Product Info
                    Bundle itemInfo = new Bundle();
                    itemInfo.putInt("itemID", productID);
            
                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new Product_Description();
                    fragment.setArguments(itemInfo);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
            
            
                    // Add the Product to User's Recently Viewed Products
                    if (!recents_db.getUserRecents().contains(cartDetails.getCartProduct().getId())) {
                        recents_db.insertRecentItem(cartDetails.getCartProduct().getId());
                    }
                }
            });
    
    
            // Delete relevant Product from Cart
            holder.cart_item_removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
            
                    holder.cart_item_removeBtn.setClickable(false);
            
                    // Delete CartItem from Local Database using static method of My_Cart
                    My_Cart.DeleteCartItem
                            (
                                    cartDetails.getCartID()
                            );
            
            
                    // Remove CartItem from Cart List
                    cartItemsList.remove(holder.getAdapterPosition());
            
                    // Notify that item at position has been removed
                    notifyItemRemoved(holder.getAdapterPosition());
            
            
                    // Calculate Cart's Total Price Again
                    cartFragment.updateCart();
            
            
                    // Update Cart View from Local Database using static method of My_Cart
                    cartFragment.updateCartView(getItemCount());
            
                }
            });
            
        }
        else {
    
            final MyViewHolder2 holder2 = (MyViewHolder2) holderAll;
            // Typecast Viewholder
            // Set Viewholder properties
            // Add any click listener if any
    
            // Set Product Image on ImageView with Glide Library
            Glide.with(context).load(cartDetails.getCartProduct().getImage()).into(holder2.checkout_item_cover);
    
            holder2.checkout_item_title.setText(cartDetails.getCartProduct().getName());
            holder2.checkout_item_category.setText(cartDetails.getCartProduct().getCategoryNames());
            holder2.checkout_item_quantity.setText("" + cartDetails.getCartProduct().getCustomersBasketQuantity());
            holder2.checkout_item_price.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(cartDetails.getCartProduct().getPrice())));
            holder2.checkout_item_price_final.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(cartDetails.getCartProduct().getProductsFinalPrice())));
    
    
            List<ProductMetaData> productMetaDataList = new ArrayList<>();
            List<ProductAttributes> productAttributesList = new ArrayList<>();
    
            productAttributesList = cartDetails.getCartProduct().getAttributes();
    
            for (int i = 0; i < productAttributesList.size(); i++) {
                ProductMetaData metaData = new ProductMetaData();
                metaData.setId(productAttributesList.get(i).getId());
                metaData.setKey(productAttributesList.get(i).getName());
                metaData.setValue(productAttributesList.get(i).getOption());
        
                productMetaDataList.add(metaData);
            }
    
            // Initialize the ProductAdditionalValuesAdapter for RecyclerView
            metaDataAdapter = new ProductAdditionalValuesAdapter(context, productMetaDataList);
    
            holder2.attributes_recycler.setAdapter(metaDataAdapter);
            holder2.attributes_recycler.setLayoutManager(new LinearLayoutManager(context));
    
            metaDataAdapter.notifyDataSetChanged();
        }
    }
    
    

    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return cartItemsList.size();
    }
    



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder0 extends RecyclerView.ViewHolder {
    
        private Button cart_item_viewBtn;
        private ImageView cart_item_cover;
        private RecyclerView attributes_recycler;
        private ImageButton cart_item_quantity_minusBtn, cart_item_quantity_plusBtn, cart_item_removeBtn;
        private TextView cart_item_title, cart_item_category, cart_item_base_price, cart_item_sub_price, cart_item_total_price, cart_item_quantity;


        public MyViewHolder0(final View itemView) {
            super(itemView);
            
            cart_item_title = (TextView) itemView.findViewById(R.id.cart_item_title);
            cart_item_base_price = (TextView) itemView.findViewById(R.id.cart_item_base_price);
            cart_item_sub_price = (TextView) itemView.findViewById(R.id.cart_item_sub_price);
            cart_item_total_price = (TextView) itemView.findViewById(R.id.cart_item_total_price);
            cart_item_quantity = (TextView) itemView.findViewById(R.id.cart_item_quantity);
            cart_item_category = (TextView) itemView.findViewById(R.id.cart_item_category);
            cart_item_cover = (ImageView) itemView.findViewById(R.id.cart_item_cover);
            cart_item_viewBtn = (Button) itemView.findViewById(R.id.cart_item_viewBtn);
            cart_item_removeBtn = (ImageButton) itemView.findViewById(R.id.cart_item_removeBtn);
            cart_item_quantity_plusBtn = (ImageButton) itemView.findViewById(R.id.cart_item_quantity_plusBtn);
            cart_item_quantity_minusBtn = (ImageButton) itemView.findViewById(R.id.cart_item_quantity_minusBtn);
            
            attributes_recycler = (RecyclerView) itemView.findViewById(R.id.cart_item_attributes_recycler);
    
    
            cart_item_total_price.setVisibility(View.GONE);
        }
        
    }
    
    
    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/
    
    public class MyViewHolder2 extends RecyclerView.ViewHolder {
    
        private ImageView checkout_item_cover;
        private RecyclerView attributes_recycler;
        private TextView checkout_item_title, checkout_item_quantity, checkout_item_price, checkout_item_price_final, checkout_item_category;
    
    
        public MyViewHolder2(final View itemView) {
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

