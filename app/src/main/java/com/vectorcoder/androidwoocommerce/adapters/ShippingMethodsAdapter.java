package com.vectorcoder.androidwoocommerce.adapters;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.vectorcoder.androidwoocommerce.R;

import java.text.DecimalFormat;
import java.util.List;

import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.fragments.Shipping_Methods;
import com.vectorcoder.androidwoocommerce.models.shipping_model.ShippingMethods;


/**
 * ShippingMethodsAdapter is the adapter class of RecyclerView holding List of Shipping Methods in Shipping_Methods
 **/

public class ShippingMethodsAdapter extends RecyclerView.Adapter<ShippingMethodsAdapter.MyViewHolder> {

    Context context;
    private Shipping_Methods shipping_methods_fragment;
    private List<ShippingMethods> shippingMethodsList;

    
    public ShippingMethodsAdapter(Context context, List<ShippingMethods> shippingMethodsList, Shipping_Methods shipping_methods_fragment) {
        this.context = context;
        this.shippingMethodsList = shippingMethodsList;
        this.shipping_methods_fragment = shipping_methods_fragment;
    }
    
    
    
    //********** Called to Inflate a Layout from XML and then return the Holder *********//
    
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shipping_methods, parent, false);
    
        return new MyViewHolder(itemView);
    }
    
    
    
    //********** Called by RecyclerView to display the Data at the specified Position *********//
    
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    
        ShippingMethods shippingMethod = shippingMethodsList.get(position);
        
        holder.shippingMethodName.setText(shippingMethod.getMethodTitle());
        
        if (shippingMethod.getSettings() != null) {
            if (shippingMethod.getSettings().getCost() != null) {
                if (!TextUtils.isEmpty(shippingMethod.getSettings().getCost().getValue())) {
                    shippingMethod.setCost(shippingMethod.getSettings().getCost().getValue());
                }
                else {
                    shippingMethod.setCost("0");
                }
            }
            else if (shippingMethod.getMethodId().equalsIgnoreCase("free_shipping")) {
                if (shippingMethod.getSettings().getMin_amount() != null) {
                    if (!TextUtils.isEmpty(shippingMethod.getSettings().getMin_amount().getValue())) {
                        shippingMethod.setCost("0.00");
                    }
                }
                else {
                    shippingMethod.setCost("0");
                }
            }
            else {
                shippingMethod.setCost("0");
            }
        }
        else {
            shippingMethod.setCost("0");
        }
        
    
        if(shippingMethod.getMethodId().equalsIgnoreCase("free_shipping")){
            holder.shippingMethodCost.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble("0.00")));
        }else {
    
            holder.shippingMethodCost.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(shippingMethod.getCost())));
        }
    
        if (shipping_methods_fragment.getSelectedShippingMethod() != null) {
            if (shippingMethod.getMethodId().equalsIgnoreCase(shipping_methods_fragment.getSelectedShippingMethod().getMethodId())) {
                holder.shippingMethodSelector.setChecked(true);
                shipping_methods_fragment.setLastChecked_RB(holder.shippingMethodSelector);
        
            }
            else {
                holder.shippingMethodSelector.setChecked(false);
            }
        }
    }
    
    
    
    //********** Returns the total number of items in the data set *********//
    
    @Override
    public int getItemCount() {
        return shippingMethodsList.size();
    }
    
    
    
    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/
    
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        LinearLayout shipping_method;
        RadioButton shippingMethodSelector;
        TextView shippingMethodName, shippingMethodCost;
        
        
        public MyViewHolder(final View itemView) {
            super(itemView);
            shipping_method = (LinearLayout) itemView.findViewById(R.id.shipping_method);
            shippingMethodName = (TextView) itemView.findViewById(R.id.shipping_method_name);
            shippingMethodCost = (TextView) itemView.findViewById(R.id.shipping_method_cost);
            shippingMethodSelector = (RadioButton) itemView.findViewById(R.id.shipping_method_selector);
    
            shipping_method.setOnClickListener(this);
        }
        
        
        // Handle Click Listener on ShippingMethod
        @Override
        public void onClick(View view) {
            
            RadioButton currentMethod_RB = (RadioButton) view.findViewById(R.id.shipping_method_selector);
            
            if (shipping_methods_fragment.getLastChecked_RB() != null) {
                shipping_methods_fragment.getLastChecked_RB().setChecked(false);
            }
            
            currentMethod_RB.setChecked(true);
            shipping_methods_fragment.setLastChecked_RB(currentMethod_RB);
            shipping_methods_fragment.selectedShippingMethod = shippingMethodsList.get(getAdapterPosition());
            
            shipping_methods_fragment.saveShippingMethodBtn.setEnabled(true);
            shipping_methods_fragment.saveShippingMethodBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.rounded_corners_button_accent));
        }
        
    }
    
    
}

