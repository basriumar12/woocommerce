package com.vectorcoder.androidwoocommerce.fragments;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.view.LayoutInflater;

import com.google.gson.Gson;
import com.vectorcoder.androidwoocommerce.R;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.ArrayList;

import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.adapters.ShippingMethodsAdapter;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.customs.DialogLoader;
import com.vectorcoder.androidwoocommerce.databases.User_Cart_DB;
import com.vectorcoder.androidwoocommerce.models.api_response_model.ErrorResponse;
import com.vectorcoder.androidwoocommerce.models.cart_model.CartDetails;
import com.vectorcoder.androidwoocommerce.models.order_model.OrderDetails;
import com.vectorcoder.androidwoocommerce.models.order_model.OrderShippingMethod;
import com.vectorcoder.androidwoocommerce.models.shipping_model.ShippingMethods;
import com.vectorcoder.androidwoocommerce.models.shipping_model.ShippingZone;
import com.vectorcoder.androidwoocommerce.models.shipping_model.ShippingZoneLocations;
import com.vectorcoder.androidwoocommerce.models.user_model.UserShipping;
import com.vectorcoder.androidwoocommerce.network.APIClient;
import com.vectorcoder.androidwoocommerce.utils.LocationHelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;


public class Shipping_Methods extends Fragment {
    
    View rootView;
    
    public Button saveShippingMethodBtn;
    TextView empty_record_text;
    RecyclerView shipping_methods_recycler;
    
    List<ShippingMethods> shippingMethodsList;
    ShippingMethodsAdapter shippingMethodsAdapter;
    
    List<ShippingZone> shippingZones;
    List<ShippingZoneLocations> shippingZoneLocations;
    
    UserShipping userShipping;
    User_Cart_DB user_cart_db;
    DialogLoader dialogLoader;
    
    // To keep track of Checked Radio Button
    private RadioButton lastChecked_RB = null;
    
    public ShippingMethods selectedShippingMethod = null;
    
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.shipping_methods, container, false);
        
        // Set the Title of Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.shipping_method));
        
        // Get the Shipping Address from AppContext
        userShipping = ((App) getContext().getApplicationContext()).getOrderDetails().getShipping();
        List<OrderShippingMethod> orderShippingMethods = ((App) getContext().getApplicationContext()).getOrderDetails().getOrderShippingMethods();
        
        if (orderShippingMethods.size() > 0) {
            selectedShippingMethod = new ShippingMethods();
            selectedShippingMethod.setId(orderShippingMethods.get(0).getShippingID());
            selectedShippingMethod.setMethodId(orderShippingMethods.get(0).getMethodId());
            selectedShippingMethod.setMethodTitle(orderShippingMethods.get(0).getMethodTitle());
            selectedShippingMethod.setTitle(orderShippingMethods.get(0).getTotal());
        }
        
        
        // Binding Layout Views
        empty_record_text = (TextView) rootView.findViewById(R.id.empty_record_text);
        saveShippingMethodBtn = (Button) rootView.findViewById(R.id.save_shipping_method_btn);
        shipping_methods_recycler = (RecyclerView) rootView.findViewById(R.id.shipping_methods_recycler);
        
        empty_record_text.setVisibility(View.GONE);
        
        
        if (selectedShippingMethod != null) {
            saveShippingMethodBtn.setEnabled(true);
            saveShippingMethodBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_accent));
        }
        else {
            saveShippingMethodBtn.setEnabled(false);
            saveShippingMethodBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_gray));
        }
//
//
//        if (ConstantValues.IS_ADMOBE_ENABLED) {
//            // Initialize InterstitialAd
//            final InterstitialAd mInterstitialAd = new InterstitialAd(getActivity());
//            mInterstitialAd.setAdUnitId(ConstantValues.AD_UNIT_ID_INTERSTITIAL);
//            mInterstitialAd.loadAd(new AdRequest.Builder().build());
//            mInterstitialAd.setAdListener(new AdListener(){
//                @Override
//                public void onAdLoaded() {
//                    mInterstitialAd.show();
//                }
//            });
//        }
        
        
        user_cart_db = new User_Cart_DB();
        dialogLoader = new DialogLoader(getContext());
        
        
        shippingZones = new ArrayList<>();
        shippingZoneLocations = new ArrayList<>();
        
        shippingMethodsList = new ArrayList<>();
        
        // Initialize the ShippingMethodsAdapter for RecyclerView
        shippingMethodsAdapter = new ShippingMethodsAdapter(getContext(), shippingMethodsList, Shipping_Methods.this);
        
        // Set the Adapter and LayoutManager to the RecyclerView
        shipping_methods_recycler.setAdapter(shippingMethodsAdapter);
        shipping_methods_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        
        
        
        // Request Shipping Rates
        GetShippingZonesAndLocations shippingZonesAndLocations = new GetShippingZonesAndLocations();
        shippingZonesAndLocations.execute();
        
        
        saveShippingMethodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (selectedShippingMethod != null) {
                    
                    OrderDetails orderDetails = ((App) getContext().getApplicationContext()).getOrderDetails();
                    
                    
                    List<OrderShippingMethod> orderShippingMethodList = new ArrayList<>();
                    OrderShippingMethod orderShippingMethod = new OrderShippingMethod();
                    
                    String str = selectedShippingMethod.getId();
                    String str2 = selectedShippingMethod.getMethodId();
                    
                    orderShippingMethod.setShippingID(selectedShippingMethod.getId());
                    orderShippingMethod.setMethodId(selectedShippingMethod.getMethodId());
                    orderShippingMethod.setMethodTitle(selectedShippingMethod.getMethodTitle());
                    orderShippingMethod.setTotal(selectedShippingMethod.getCost());
                    
                    orderShippingMethodList.add(orderShippingMethod);
                    
                    orderDetails.setOrderShippingMethods(orderShippingMethodList);
                    
                    ((App) getContext().getApplicationContext()).setOrderDetails(orderDetails);
    
                    ((App) getContext().getApplicationContext()).setShippingService(orderShippingMethod);
                    
                    // Navigate to Checkout Fragment
                    Fragment fragment = new Checkout();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment)
                            .addToBackStack(null).commit();
                    
                }
            }
        });
        
        
        return rootView;
    }
    
    //*********** Handle ShippingMethods returned from the Server ********//
    
    private void matchShippingZone() {
        int zone_ID = 0;
        boolean isMatched = false;
        
        for (int i=0;  i<shippingZoneLocations.size();  i++) {
            ShippingZoneLocations shippingZoneLocation = shippingZoneLocations.get(i);
            if ("postcode".equalsIgnoreCase(shippingZoneLocation.getType())) {
                if (matchPostCode(shippingZoneLocation)) {
                    zone_ID = shippingZoneLocation.getZoneID();
                    isMatched = true;
                    break;
                }
            }
        }
        
        if (!isMatched) {
            for (int i=0;  i<shippingZoneLocations.size();  i++) {
                ShippingZoneLocations shippingZoneLocation = shippingZoneLocations.get(i);
                if ("state".equalsIgnoreCase(shippingZoneLocation.getType())) {
                    if ((userShipping.getCountry() +":"+ userShipping.getState()).equalsIgnoreCase(shippingZoneLocation.getCode())) {
                        zone_ID = shippingZoneLocation.getZoneID();
                        isMatched = true;
                        break;
                    }
                }
            }
        }
        
        if (!isMatched) {
            for (int i=0;  i<shippingZoneLocations.size();  i++) {
                ShippingZoneLocations shippingZoneLocation = shippingZoneLocations.get(i);
                if ("country".equalsIgnoreCase(shippingZoneLocation.getType())) {
                    if (userShipping.getCountry().equalsIgnoreCase(shippingZoneLocation.getCode())) {
                        zone_ID = shippingZoneLocation.getZoneID();
                        isMatched = true;
                        break;
                    }
                }
            }
        }
        
        if (!isMatched) {
            for (int i=0;  i<shippingZoneLocations.size();  i++) {
                ShippingZoneLocations shippingZoneLocation = shippingZoneLocations.get(i);
                if ("continent".equalsIgnoreCase(shippingZoneLocation.getType())) {
                    LocationHelper locationHelper = new LocationHelper(getContext());
                    String selectedContinent = locationHelper.getContinentCode(userShipping.getCountry());
                    
                    if (selectedContinent.equalsIgnoreCase(shippingZoneLocation.getCode())) {
                        zone_ID = shippingZoneLocation.getZoneID();
                        isMatched = true;
                        break;
                    }
                }
            }
        }
        
        
        if (isMatched) {
            RequestShippingMethods(zone_ID);
        }
        else {
            empty_record_text.setVisibility(View.VISIBLE);
//            RequestDefaultShippingMethods();
        }
        
    }
    
    //*********** Match PostCode of the ShippingMethods returned from the Server ********//
    
    private boolean matchPostCode(ShippingZoneLocations shippingZoneLocation) {
        boolean isMatched = false;
        
        String selectedPostCode = userShipping.getPostcode();
        
        if (selectedPostCode.equalsIgnoreCase(shippingZoneLocation.getCode()))
            return true;
        
        if (shippingZoneLocation.getCode().contains("*")) {
            String code = shippingZoneLocation.getCode().replaceAll("\\*", "");
            if (selectedPostCode.startsWith(code))
                return true;
        }
        
        if (shippingZoneLocation.getCode().contains(".")) {
            String code = shippingZoneLocation.getCode();
            String[] separated = code.split("\\.");
            String min = separated[0];
            String max = separated[3].trim();
            
            if (Long.parseLong(selectedPostCode) >= Long.parseLong(min)  &&  Long.parseLong(selectedPostCode) <= Long.parseLong(max))
                return true;
        }
        
        return isMatched;
    }
    
    
    
    public RadioButton getLastChecked_RB() {
        return lastChecked_RB;
    }
    
    public void setLastChecked_RB(RadioButton lastChecked_RB) {
        this.lastChecked_RB = lastChecked_RB;
    }
    
    public ShippingMethods getSelectedShippingMethod() {
        return selectedShippingMethod;
    }
    
    
    //*********** Adds the returned shipping methods to the list sd ********//
    
    public void addShippingMethodsToList(List<ShippingMethods> sMethodsList) {
        
        List<CartDetails> cartItems = user_cart_db.getCartItems();
        double total_amount = getProductsSubTotal(cartItems);
        
        
        for (int i = 0;  i<sMethodsList.size();  i++) {
            if (sMethodsList.get(i).isEnabled())
                
                if(sMethodsList.get(i).getMethodId().equalsIgnoreCase("free_shipping") ) {
                    double check_min_amount = Double.parseDouble(sMethodsList.get(i).getSettings().getMin_amount().getValue());
                    if(total_amount>check_min_amount){
                        
                        shippingMethodsList.add(sMethodsList.get(i));
                    }
                }
                else {
                    shippingMethodsList.add(sMethodsList.get(i));
                }
        }
        
        shippingMethodsAdapter.notifyDataSetChanged();
        
        
        if (shippingMethodsAdapter.getItemCount() < 0) {
            empty_record_text.setVisibility(View.VISIBLE);
        }
        else {
            empty_record_text.setVisibility(View.GONE);
        }
        
    }
    
    
    //*********** Returns Final Price of User's Cart ********//
    
    private double getProductsSubTotal(List<CartDetails> checkoutItemsList) {
        
        double finalPrice = 0;
        
        for (int i=0;  i<checkoutItemsList.size();  i++) {
            // Add the Price of each Cart Product to finalPrice
            finalPrice += Double.parseDouble(checkoutItemsList.get(i).getCartProduct().getTotalPrice());
        }
        
        return finalPrice;
    }
    
    
    //*********** Request the Server to get Default Shipping Methods ********//
    /*
    public void RequestDefaultShippingMethods() {
        
        dialogLoader.showProgressDialog();
        
        Call<List<ShippingMethods>> call = APIClient.getInstance()
                .getDefaultShippingMethods();
         
        call.enqueue(new Callback<List<ShippingMethods>>() {
            @Override
            public void onResponse(Call<List<ShippingMethods>> call, retrofit2.Response<List<ShippingMethods>> response) {
                
                dialogLoader.hideProgressDialog();
                
                if (response.isSuccessful()) {
     
                    addShippingMethodsToList(response.body());
                    
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
            public void onFailure(Call<List<ShippingMethods>> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    */
    
    
    //*********** Request the Server to get Shipping Methods based on zone_id ********//
    
    public void RequestShippingMethods(int zone_id) {
        
        dialogLoader.showProgressDialog();
        empty_record_text.setVisibility(View.GONE);
        
        int id = zone_id;
        
        Call<List<ShippingMethods>> call = APIClient.getInstance()
                .getShippingMethods
                        (
                                String.valueOf(zone_id)
                        );
        
        call.enqueue(new Callback<List<ShippingMethods>>() {
            @Override
            public void onResponse(Call<List<ShippingMethods>> call, retrofit2.Response<List<ShippingMethods>> response) {
                
                String str = new Gson().toJson(response);
                
                dialogLoader.hideProgressDialog();
                
                if (response.isSuccessful()) {
                    
                    if (response.body().size() > 0) {
                        
                        addShippingMethodsToList(response.body());
                        
                    }
                    else {
                        empty_record_text.setVisibility(View.VISIBLE);
                    }
                    
                }
                else {
                    empty_record_text.setVisibility(View.VISIBLE);
                }
            }
            
            @Override
            public void onFailure(Call<List<ShippingMethods>> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                empty_record_text.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
    }
    
    
    
    //*********** Request the Server to get Shipping Zones ********//
    
    public void RequestShippingZones() {
        
        Call<List<ShippingZone>> call = APIClient.getInstance()
                .getShippingZones();
        
        try {
            Response<List<ShippingZone>> response = call.execute();
            
            if (response.isSuccessful()) {
                
                shippingZones.addAll(response.body());
                
                for (int i=0;  i<shippingZones.size();  i++) {
                    RequestShippingZoneLocations(shippingZones.get(i).getId());
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
//                Toast.makeText(getContext(), "Error Fetching Product : "+error, Toast.LENGTH_LONG).show();
                empty_record_text.setVisibility(View.VISIBLE);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            empty_record_text.setVisibility(View.VISIBLE);
//            Toast.makeText(getContext(), "Exception : "+e, Toast.LENGTH_LONG).show();
        }
    }
    
    
    
    //*********** Request the Server to get Zone Locations based on zone_id ********//
    
    public void RequestShippingZoneLocations(int zone_id) {
        
        Call<List<ShippingZoneLocations>> call = APIClient.getInstance()
                .getShippingZoneLocations
                        (
                                String.valueOf(zone_id)
                        );
        
        try {
            Response<List<ShippingZoneLocations>> response = call.execute();
            
            if (response.isSuccessful()) {
                
                List<ShippingZoneLocations> shippingZoneLocationsList = response.body();
                
                for (int i=0;  i<shippingZoneLocationsList.size();  i++) {
                    
                    shippingZoneLocationsList.get(i).setZoneID(zone_id);
                    shippingZoneLocations.add(shippingZoneLocationsList.get(i));
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
//                Toast.makeText(getContext(), "Error Fetching Product : "+error, Toast.LENGTH_LONG).show();
                empty_record_text.setVisibility(View.VISIBLE);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            empty_record_text.setVisibility(View.VISIBLE);
//            Toast.makeText(getContext(), "Exception : "+e, Toast.LENGTH_LONG).show();
        }
    }
    
    /*********** LoadMoreTask Used to Load Shipping Zones and Locations from the Server in the Background Thread using AsyncTask ********/
    
    private class GetShippingZonesAndLocations extends AsyncTask<String, Void, String> {
        
        //*********** Runs on the UI thread before #doInBackground() ********//
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoader.showProgressDialog();
        }
        
        
        //*********** Performs some Processes on Background Thread and Returns a specified Result  ********//
        
        @Override
        protected String doInBackground(String... params) {
            
            RequestShippingZones();
            
            return "All Done!";
        }
        
        
        //*********** Runs on the UI thread after #doInBackground() ********//
        
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialogLoader.hideProgressDialog();
            matchShippingZone();
        }
    }
    
}