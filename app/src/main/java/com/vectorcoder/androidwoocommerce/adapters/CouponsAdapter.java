package com.vectorcoder.androidwoocommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.vectorcoder.androidwoocommerce.R;

import java.text.DecimalFormat;
import java.util.List;

import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.fragments.My_Cart;
import com.vectorcoder.androidwoocommerce.models.coupons_model.CouponDetails;


/**
 * CouponsAdapter is the adapter class of RecyclerView holding List of Coupons in Checkout and Order_Details
 **/

public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.MyViewHolder> {

    Context context;
    Boolean isRemovable;
    
    My_Cart my_cart;
    List<CouponDetails> couponsList;


    public CouponsAdapter(Context context, List<CouponDetails> couponsList, Boolean isRemovable, My_Cart my_cart) {
        this.context = context;
        this.my_cart = my_cart;
        this.isRemovable = isRemovable;
        this.couponsList = couponsList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_coupons, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final CouponDetails coupon = couponsList.get(position);

        holder.coupon_code.setText(coupon.getCode());
        holder.coupon_discount.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(coupon.getDiscount())));
        
        if (coupon.getDiscountType() != null) {
            holder.coupon_type_layout.setVisibility(View.VISIBLE);
            holder.coupon_type.setText(coupon.getDiscountType());
        }
        else {
            holder.coupon_type_layout.setVisibility(View.GONE);
        }
    
    
        if (coupon.getAmount() != null) {
            holder.coupon_amount_layout.setVisibility(View.VISIBLE);
    
            if (coupon.getDiscountType().equalsIgnoreCase("fixed_cart") || coupon.getDiscountType().equalsIgnoreCase("fixed_product")) {
                holder.coupon_amount.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(coupon.getAmount())));
            }
            else if (coupon.getDiscountType().equalsIgnoreCase("percent") || coupon.getDiscountType().equalsIgnoreCase("percent_product")) {
                holder.coupon_amount.setText(coupon.getAmount() + "%");
            }
            
        }
        else {
            holder.coupon_amount_layout.setVisibility(View.GONE);
        }
        
        

        if (isRemovable) {
            holder.coupon_delete.setVisibility(View.VISIBLE);

            holder.coupon_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                    my_cart.removeCoupon(coupon);
                }
            });

        }
        else {
            holder.coupon_delete.setVisibility(View.GONE);
        }

    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return couponsList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageButton coupon_delete;
        private TextView coupon_code, coupon_type, coupon_amount, coupon_discount;
        private RelativeLayout coupon_code_layout, coupon_type_layout, coupon_amount_layout, coupon_discount_layout;


        public MyViewHolder(final View itemView) {
            super(itemView);

            coupon_code = (TextView) itemView.findViewById(R.id.coupon_code);
            coupon_type = (TextView) itemView.findViewById(R.id.coupon_type);
            coupon_amount = (TextView) itemView.findViewById(R.id.coupon_amount);
            coupon_discount = (TextView) itemView.findViewById(R.id.coupon_discount);
            coupon_delete = (ImageButton) itemView.findViewById(R.id.coupon_delete);
            coupon_code_layout = (RelativeLayout) itemView.findViewById(R.id.coupon_code_layout);
            coupon_type_layout = (RelativeLayout) itemView.findViewById(R.id.coupon_type_layout);
            coupon_amount_layout = (RelativeLayout) itemView.findViewById(R.id.coupon_amount_layout);
            coupon_discount_layout = (RelativeLayout) itemView.findViewById(R.id.coupon_discount_layout);

        }
    }
}

