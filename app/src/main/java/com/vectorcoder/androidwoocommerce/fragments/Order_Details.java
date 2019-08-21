package com.vectorcoder.androidwoocommerce.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vectorcoder.androidwoocommerce.R;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.util.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.vectorcoder.androidwoocommerce.adapters.CouponsAdapter;
import com.vectorcoder.androidwoocommerce.adapters.OrderedProductsListAdapter;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.customs.DialogLoader;
import com.vectorcoder.androidwoocommerce.models.api_response_model.ErrorResponse;
import com.vectorcoder.androidwoocommerce.models.coupons_model.CouponDetails;
import com.vectorcoder.androidwoocommerce.models.device_model.AppSettingsDetails;
import com.vectorcoder.androidwoocommerce.models.order_model.OrderDetails;
import com.vectorcoder.androidwoocommerce.customs.DividerItemDecoration;
import com.vectorcoder.androidwoocommerce.models.order_model.OrderProducts;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductDetails;
import com.vectorcoder.androidwoocommerce.network.APIClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;


public class Order_Details extends Fragment {

    View rootView;
    
    int orderID;
    OrderDetails orderDetails;

    Button cancel_order_btn;
    CardView buyer_comments_card, seller_comments_card;
    RecyclerView checkout_items_recycler, checkout_coupons_recycler;
    TextView checkout_subtotal, checkout_tax, checkout_shipping, checkout_discount, checkout_total;
    TextView billing_name, billing_street, billing_address, shipping_name, shipping_street, shipping_address;
    TextView order_price, order_products_count, order_status, order_date, shipping_method, payment_method, buyer_comments, seller_comments;

    List<CouponDetails> couponsList;
    List<ProductDetails> productsList;
    List<OrderProducts> orderProductsList;

    DialogLoader dialogLoader;
    CouponsAdapter couponsAdapter;
    OrderedProductsListAdapter orderedProductsAdapter;
    AppSettingsDetails appSettings;
    SimpleDateFormat simpleDateFormat;
    int cancel_order_hours;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.order_details, container, false);
        
        // Set the Title of Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.order_details));
    
        appSettings = ((App) getContext().getApplicationContext()).getAppSettingsDetails();
        
        cancel_order_hours = Integer.parseInt(appSettings.getCancel_order_hours());

        // Binding Layout Views
        order_price = (TextView) rootView.findViewById(R.id.order_price);
        order_products_count = (TextView) rootView.findViewById(R.id.order_products_count);
        shipping_method = (TextView) rootView.findViewById(R.id.shipping_method);
        payment_method = (TextView) rootView.findViewById(R.id.payment_method);
        order_status = (TextView) rootView.findViewById(R.id.order_status);
        order_date = (TextView) rootView.findViewById(R.id.order_date);
        checkout_subtotal = (TextView) rootView.findViewById(R.id.checkout_subtotal);
        checkout_tax = (TextView) rootView.findViewById(R.id.checkout_tax);
        checkout_shipping = (TextView) rootView.findViewById(R.id.checkout_shipping);
        checkout_discount = (TextView) rootView.findViewById(R.id.checkout_discount);
        checkout_total = (TextView) rootView.findViewById(R.id.checkout_total);
        billing_name = (TextView) rootView.findViewById(R.id.billing_name);
        billing_address = (TextView) rootView.findViewById(R.id.billing_address);
        billing_street = (TextView) rootView.findViewById(R.id.billing_street);
        shipping_name = (TextView) rootView.findViewById(R.id.shipping_name);
        shipping_address = (TextView) rootView.findViewById(R.id.shipping_address);
        shipping_street = (TextView) rootView.findViewById(R.id.shipping_street);
        buyer_comments = (TextView) rootView.findViewById(R.id.buyer_comments);
        seller_comments = (TextView) rootView.findViewById(R.id.seller_comments);
        cancel_order_btn = (Button) rootView.findViewById(R.id.cancel_order_btn);
        
        
        
        
        buyer_comments_card = (CardView) rootView.findViewById(R.id.buyer_comments_card);
        seller_comments_card = (CardView) rootView.findViewById(R.id.seller_comments_card);
        checkout_items_recycler = (RecyclerView) rootView.findViewById(R.id.checkout_items_recycler);
        checkout_coupons_recycler = (RecyclerView) rootView.findViewById(R.id.checkout_coupons_recycler);
    
        cancel_order_btn.setVisibility(View.GONE);
        checkout_items_recycler.setNestedScrollingEnabled(false);
        checkout_coupons_recycler.setNestedScrollingEnabled(false);
    
    
        dialogLoader = new DialogLoader(getContext());
    
        // Get product Info from bundle arguments
        if (getArguments() != null) {
            if (getArguments().containsKey("orderDetails")) {
                
                orderDetails = getArguments().getParcelable("orderDetails");
                // Set Product Details
                setOrderDetails(orderDetails);
                
            }
            else if (getArguments().containsKey("orderID")) {
                
                orderID = getArguments().getInt("orderID");
                // Get Product Details
                RequestOrderDetail(orderID);
    
            }
        }
        

        return rootView;

    }
    
    
    
    //*********** Adds Product's Details to the Views ********//
    
    private void setOrderDetails(final OrderDetails orderDetails) {
    
        productsList = new ArrayList<>();
        
        couponsList = orderDetails.getOrderCoupons();
        orderProductsList = orderDetails.getOrderProducts();
        if(appSettings!=null) {
            if ("1".equalsIgnoreCase(appSettings.getCancel_order_button())) {
                cancel_order_btn.setVisibility(View.VISIBLE);
                if (getcancelOrdeHour(orderDetails) < cancel_order_hours) {
                    if (orderDetails.getStatus().equalsIgnoreCase("pending")
                            || orderDetails.getStatus().equalsIgnoreCase("on-hold")) {
    
                        cancel_order_btn.setVisibility(View.VISIBLE);
                    }
                    else {
    
                        cancel_order_btn.setVisibility(View.VISIBLE);
                    }
                }
                
                else {
                    cancel_order_btn.setVisibility(View.GONE);
                }
            }
        }
        
    
        double subTotal = 0;
        int noOfProducts = 0;
        for (int i=0;  i<orderProductsList.size();  i++) {
            subTotal += Double.parseDouble(orderProductsList.get(i).getTotal());
            noOfProducts += orderProductsList.get(i).getQuantity();
        }
    
    
        order_products_count.setText(String.valueOf(noOfProducts));
        payment_method.setText(orderDetails.getPaymentMethodTitle());
        order_status.setText(orderDetails.getStatus());
        order_date.setText(orderDetails.getDateCreated().replaceAll("[a-zA-Z]", " "));
        
        if (orderDetails.getOrderShippingMethods().size() > 0)
            shipping_method.setText(orderDetails.getOrderShippingMethods().get(0).getMethodTitle());
    
        checkout_subtotal.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(subTotal));
        checkout_tax.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getTotalTax())));
        checkout_shipping.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getShippingTotal())));
        checkout_discount.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getDiscountTotal())));
        checkout_total.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getTotal())));
        order_price.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(Double.parseDouble(orderDetails.getTotal())));
    
        billing_name.setText(orderDetails.getBilling().getFirstName());
        billing_address.setText(orderDetails.getBilling().getCity());
        billing_street.setText(orderDetails.getBilling().getAddress1());
        shipping_name.setText(orderDetails.getShipping().getFirstName());
        shipping_address.setText(orderDetails.getShipping().getCity());
        shipping_street.setText(orderDetails.getShipping().getAddress1());
    
    
        if (orderDetails.getCustomerNote() != null  &&  !TextUtils.isEmpty(orderDetails.getCustomerNote())) {
            buyer_comments_card.setVisibility(View.VISIBLE);
            buyer_comments.setText(orderDetails.getCustomerNote());
        } else {
            buyer_comments_card.setVisibility(View.GONE);
        }
        
        seller_comments_card.setVisibility(View.GONE);
    
        
    
        couponsAdapter = new CouponsAdapter(getContext(), couponsList, false, null);
    
        checkout_coupons_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        checkout_coupons_recycler.setAdapter(couponsAdapter);
    
        
        GetOrderedProducts getOrderedProducts = new GetOrderedProducts(getContext(), orderProductsList);
        getOrderedProducts.execute();
        
    
        orderedProductsAdapter = new OrderedProductsListAdapter(getContext(), productsList);
    
        checkout_items_recycler.setAdapter(orderedProductsAdapter);
        checkout_items_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        checkout_items_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    
    
    
        cancel_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestCancelOrder(orderDetails.getId());
            }
        });
        
    }
    
    
    //*********** Request Product Details from the Server based on productID ********//
    
    public void addOrderProduct(ProductDetails productDetails) {
        
        for (int i=0;  i<orderProductsList.size();  i++) {
            if (orderProductsList.get(i).getProductId() == productDetails.getId()) {
                productDetails.setPrice(orderProductsList.get(i).getPrice());
                productDetails.setProductsFinalPrice(orderProductsList.get(i).getSubtotal());
                productDetails.setTotalPrice(orderProductsList.get(i).getTotal());
                productDetails.setCustomersBasketQuantity(orderProductsList.get(i).getQuantity());
    
                if (productDetails.getImages() != null  &&  !TextUtils.isEmpty(productDetails.getImages().get(0).getSrc())) {
                    productDetails.setImage(productDetails.getImages().get(0).getSrc());
                }
                else {
                    productDetails.setImage("");
                }
    
                productsList.add(productDetails);
            }
        }
    }
    
    
    
    //*********** Request Order Details from the Server based on orderID ********//
    
    public void RequestOrderDetail(final int orderID) {
        
        dialogLoader.showProgressDialog();
        
        Call<OrderDetails> call = APIClient.getInstance()
                .getSingleOrder
                        (
                                String.valueOf(orderID)
                        );
        
        call.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, retrofit2.Response<OrderDetails> response) {
                
                dialogLoader.hideProgressDialog();
                
                
                if (response.isSuccessful()) {
                    
                    setOrderDetails(response.body());
                    
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
            public void onFailure(Call<OrderDetails> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
    }
    
    
    //*********** Request to cancel the Order from the Server based on orderID ********//
    
    public void RequestCancelOrder(final int orderID) {
        
        dialogLoader.showProgressDialog();
        
        Call<OrderDetails> call = APIClient.getInstance()
                .updateOrder
                        (
                                String.valueOf(orderID),
                                "cancelled"
                        );
        
        call.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, retrofit2.Response<OrderDetails> response) {
                
                dialogLoader.hideProgressDialog();
                
                
                if (response.isSuccessful()) {
    
                    if (response.body().getStatus() != null) {
                        order_status.setText(response.body().getStatus());
                        cancel_order_btn.setVisibility(View.GONE);
    
                        Snackbar.make(rootView, getString(R.string.order_cancelled), Snackbar.LENGTH_SHORT).show();
                    }
                    
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
            public void onFailure(Call<OrderDetails> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
    }
    
    
    
    /*********** LoadMoreTask Used to Load more Products from the Server in the Background Thread using AsyncTask ********/
    
    private class GetOrderedProducts extends AsyncTask<String, Void, String> {
        
        List<OrderProducts> orderProducts;
        
        
        private GetOrderedProducts(Context context, List<OrderProducts> orderProducts) {
            this.orderProducts = orderProducts;
        }
        
        
        //*********** Runs on the UI thread before #doInBackground() ********//
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        
        
        //*********** Performs some Processes on Background Thread and Returns a specified Result  ********//
        
        @Override
        protected String doInBackground(String... params) {
            
            for (int i=0;  i<orderProducts.size();  i++) {
                
                Call<ProductDetails> call = APIClient.getInstance()
                    .getSingleProduct
                        (
                            String.valueOf(orderProducts.get(i).getProductId())
                            
                        );
    
                try {
        
                    Response<ProductDetails> response = call.execute();
        
                    if (response.isSuccessful()) {
    
                        addOrderProduct(response.body());
            
                    }
        
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                
            }
            
            return "All Done!";
        }
        
        
        //*********** Runs on the UI thread after #doInBackground() ********//
        
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            orderedProductsAdapter.notifyDataSetChanged();
        }
    }
    
    
    public int getcancelOrdeHour(OrderDetails orderDetails){
        
        int cancelHours=0;
       
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    
        String currentDateTime = simpleDateFormat.format(new Date());
        String orderDateTime = orderDetails.getDateCreated().replaceAll("[a-zA-Z]", " ");
        String replace = orderDateTime.replaceAll("-","/");
        Date initDate = null;
        String orderDateTimeFinal = null;
        try {
            initDate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse(replace);
        
            orderDateTimeFinal = simpleDateFormat.format(initDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    
    
        try {
            Date date1 = simpleDateFormat.parse(orderDateTimeFinal);
            Date date2 = simpleDateFormat.parse(currentDateTime);
    
            cancelHours = printDifference(date1, date2);
        
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return cancelHours;
    
        /// End Function
    }
    
    //1 minute = 60 seconds
//1 hour = 60 x 60 = 3600
//1 day = 3600 x 24 = 86400
    public int printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();
        
        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);
        
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        
        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;
        
        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        
        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
        
        long elapsedSeconds = different / secondsInMilli;
        
        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        
        return (int) elapsedHours;
        
    }
 

}