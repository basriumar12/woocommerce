package com.vectorcoder.androidwoocommerce.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vectorcoder.androidwoocommerce.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.customs.CustomEditText;
import com.vectorcoder.androidwoocommerce.customs.DialogLoader;
import com.vectorcoder.androidwoocommerce.models.location_model.Countries;
import com.vectorcoder.androidwoocommerce.models.location_model.Zones;
import com.vectorcoder.androidwoocommerce.models.order_model.OrderDetails;
import com.vectorcoder.androidwoocommerce.models.shipping_model.ShippingMethods;
import com.vectorcoder.androidwoocommerce.models.user_model.UpdateUser;
import com.vectorcoder.androidwoocommerce.models.user_model.UserData;
import com.vectorcoder.androidwoocommerce.models.user_model.UserDetails;
import com.vectorcoder.androidwoocommerce.models.user_model.UserShipping;
import com.vectorcoder.androidwoocommerce.network.APIClient;
import com.vectorcoder.androidwoocommerce.utils.LocationHelper;
import com.vectorcoder.androidwoocommerce.utils.ValidateInputs;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;


public class Shipping_Address extends Fragment {
    
    View rootView;
    String customerID,GetArgument;
    
    Zones selectedZone;
    Countries selectedCountry;
    DialogLoader dialogLoader;
    UserShipping userShipping;
    LocationHelper locationHelper;
    
    List<Zones> zoneList;
    List<Countries> countryList;
    
    List<String> zoneNames;
    List<String> countryNames;
    ArrayAdapter<String> zoneAdapter;
    ArrayAdapter<String> countryAdapter;
    
    Button proceed_checkout_btn;
    LinearLayout default_shipping_layout;
    CustomEditText input_firstname, input_lastname, input_address_1, input_address_2, input_company;
    CustomEditText input_country, input_zone, input_city, input_postcode, input_email, input_contact;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.address, container, false);
        
        // Set the Title of Toolbar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.shipping_address));
        
        // Get the customersID and defaultAddressID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        
        
        // Binding Layout Views
        input_firstname = (CustomEditText) rootView.findViewById(R.id.firstname);
        //input_firstname.setSelection(input_firstname.getText().length());
        input_lastname = (CustomEditText) rootView.findViewById(R.id.lastname);
        input_address_1 = (CustomEditText) rootView.findViewById(R.id.address_1);
        input_address_2 = (CustomEditText) rootView.findViewById(R.id.address_2);
        input_company = (CustomEditText) rootView.findViewById(R.id.company);
        input_country = (CustomEditText) rootView.findViewById(R.id.country);
        input_zone = (CustomEditText) rootView.findViewById(R.id.zone);
        input_city = (CustomEditText) rootView.findViewById(R.id.city);
        input_postcode = (CustomEditText) rootView.findViewById(R.id.postcode);
        input_email = (CustomEditText) rootView.findViewById(R.id.email);
        input_contact = (CustomEditText) rootView.findViewById(R.id.contact);
        proceed_checkout_btn = (Button) rootView.findViewById(R.id.save_address_btn);
        default_shipping_layout = (LinearLayout) rootView.findViewById(R.id.default_shipping_layout);
        
        
        // Set KeyListener of some View to null
        input_country.setKeyListener(null);
        input_zone.setKeyListener(null);
        input_zone.setFocusableInTouchMode(false);
        
        // Hide the Default Checkbox Layout
        input_email.setVisibility(View.GONE);
        input_contact.setVisibility(View.GONE);
        default_shipping_layout.setVisibility(View.GONE);
        
        // Set the text of Button
        
        if(getArguments()!=null){
            GetArgument = getArguments().getString("shipping");
            if("1".equalsIgnoreCase(GetArgument)){
                // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
                MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                proceed_checkout_btn.setText(getContext().getString(R.string.update));
            }
            else {
                proceed_checkout_btn.setText(getContext().getString(R.string.next));
            }
        }
        
        
        
        dialogLoader = new DialogLoader(getContext());
        locationHelper = new LocationHelper(getContext());
        
        userShipping = ((App) getContext().getApplicationContext()).getOrderDetails().getShipping();
        
        
        zoneList = new ArrayList<>();
        zoneNames = new ArrayList<>();
        countryNames = new ArrayList<>();
        
        selectedZone = null;
        selectedCountry = null;
        
        
        countryList = locationHelper.getCountries();
        
        for (int i=0;  i<countryList.size();  i++) {
            countryNames.add(countryList.get(i).getName());
        }
        countryNames.add("Other");
        
        
        
        if (userShipping == null) {
            // Request User Info
            if (!ConstantValues.IS_GUEST_LOGGED_IN)
                RequestUserDetails();
        }
        else {
            // Set the Address details
            input_firstname.setText(userShipping.getFirstName());
            input_lastname.setText(userShipping.getLastName());
            input_address_1.setText(userShipping.getAddress1());
            input_address_2.setText(userShipping.getAddress2());
            input_company.setText(userShipping.getCompany());
            input_city.setText(userShipping.getCity());
            input_postcode.setText(userShipping.getPostcode());
            
            String zone_code = "";
            String country_code = "";
            
            if (!TextUtils.isEmpty(userShipping.getCountry())) {
                country_code = userShipping.getCountry();
                selectedCountry = locationHelper.getCountryFromCode(country_code);
                input_country.setText(selectedCountry.getName());
                
                // Get Zones of the selected Country
                zoneList.clear();
                zoneList = new ArrayList<>();
                zoneList = locationHelper.getStates(selectedCountry.getValue());
                
                zoneNames.clear();
                
                // Add the Zone Names to the zoneNames List
                for (int x=0;  x<zoneList.size();  x++){
                    zoneNames.add(zoneList.get(x).getName());
                }
                zoneNames.add("Other");
                
                input_zone.setFocusableInTouchMode(true);
            }
            
            
            if (!TextUtils.isEmpty(userShipping.getState())) {
                zone_code = userShipping.getState();
                selectedZone = locationHelper.getStateFromCode(country_code, zone_code);
                input_zone.setText(selectedZone.getName());
            }
        }
        
        
        // Handle Touch event of input_country EditText
        input_country.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    
                    countryAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
                    countryAdapter.addAll(countryNames);
                    
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_list_search, null);
                    dialog.setView(dialogView);
                    dialog.setCancelable(false);
                    
                    Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
                    EditText dialog_input = (EditText) dialogView.findViewById(R.id.dialog_input);
                    TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                    ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);
                    
                    dialog_title.setText(getString(R.string.country));
                    dialog_list.setVerticalScrollBarEnabled(true);
                    dialog_list.setAdapter(countryAdapter);
                    
                    dialog_input.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                            countryAdapter.getFilter().filter(charSequence);
                        }
                        @Override
                        public void afterTextChanged(Editable s) {}
                    });
                    
                    
                    final AlertDialog alertDialog = dialog.create();
                    
                    dialog_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    
                    alertDialog.show();
                    
                    dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            
                            final String selectedItem = countryAdapter.getItem(position);
                            input_country.setText(selectedItem);
                            
                            if (!selectedItem.equalsIgnoreCase("Other")) {
                                
                                for (int i=0;  i<countryList.size();  i++) {
                                    if (countryList.get(i).getName().equalsIgnoreCase(selectedItem)) {
                                        
                                        selectedCountry = countryList.get(i);
                                        
                                        // Get Zones of the selected Country
                                        zoneList.clear();
                                        zoneList = new ArrayList<>();
                                        zoneList = locationHelper.getStates(selectedCountry.getValue());
                                        
                                        zoneNames.clear();
                                        
                                        // Add the Zone Names to the zoneNames List
                                        for (int x=0;  x<zoneList.size();  x++){
                                            zoneNames.add(zoneList.get(x).getName());
                                        }
                                        zoneNames.add("Other");
                                    }
                                }
                                
                            }
                            else {
                                selectedCountry = null;
                                
                                // Get Zones of the selected Country
                                zoneList.clear();
                                zoneList = new ArrayList<>();
                                
                                zoneNames.clear();
                                zoneNames.add("Other");
                            }
                            
                            input_zone.setText("");
                            input_zone.setFocusableInTouchMode(true);
                            
                            alertDialog.dismiss();
                        }
                    });
                    
                }
                
                return false;
            }
        });
        
        
        // Handle Touch event of input_zone EditText
        input_zone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    
                    zoneAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
                    zoneAdapter.addAll(zoneNames);
                    
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_list_search, null);
                    dialog.setView(dialogView);
                    dialog.setCancelable(false);
                    
                    Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
                    EditText dialog_input = (EditText) dialogView.findViewById(R.id.dialog_input);
                    TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                    ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);
                    
                    dialog_title.setText(getString(R.string.zone));
                    dialog_list.setVerticalScrollBarEnabled(true);
                    dialog_list.setAdapter(zoneAdapter);
                    
                    dialog_input.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                        @Override
                        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                            zoneAdapter.getFilter().filter(charSequence);
                        }
                        @Override
                        public void afterTextChanged(Editable s) {}
                    });
                    
                    
                    final AlertDialog alertDialog = dialog.create();
                    
                    dialog_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    
                    alertDialog.show();
                    
                    
                    
                    dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            
                            final String selectedItem = zoneAdapter.getItem(position);
                            input_zone.setText(selectedItem);
                            
                            if (!zoneAdapter.getItem(position).equalsIgnoreCase("Other")) {
                                
                                for (int i=0;  i<zoneList.size();  i++) {
                                    if (zoneList.get(i).getName().equalsIgnoreCase(selectedItem)) {
                                        
                                        selectedZone = zoneList.get(i);
                                    }
                                }
                            }
                            else {
                                selectedZone = null;
                            }
                            
                            alertDialog.dismiss();
                        }
                    });
                    
                }
                
                return false;
            }
        });
        
        
        // Handle the Click event of Proceed Order Button
        proceed_checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if ("1".equalsIgnoreCase(GetArgument)) {
                    processUpdateUser();
                    
                } else {
                    
                    // Validate Address Form Inputs
                    boolean isValidData = validateAddressForm();
                    
                    if (isValidData) {
                        // New Instance of AddressDetails
                        userShipping = new UserShipping();
                        
                        userShipping.setFirstName(input_firstname.getText().toString().trim());
                        userShipping.setLastName(input_lastname.getText().toString().trim());
                        userShipping.setAddress1(input_address_1.getText().toString().trim());
                        userShipping.setAddress2(input_address_2.getText().toString().trim());
                        userShipping.setCompany(input_company.getText().toString().trim());
                        userShipping.setCity(input_city.getText().toString().trim());
                        userShipping.setPostcode(input_postcode.getText().toString().trim());
                        
                        if (selectedCountry != null) {
                            userShipping.setCountry(selectedCountry.getValue());
                        } else {
                            userShipping.setCountry("");
                        }
                        
                        if (selectedZone != null) {
                            userShipping.setState(selectedZone.getValue());
                        } else {
                            userShipping.setState("");
                        }
                        
                        
                        OrderDetails orderDetails = ((App) getContext().getApplicationContext()).getOrderDetails();
                        orderDetails.setShipping(userShipping);
                        
                        ((App) getContext().getApplicationContext()).setOrderDetails(orderDetails);
                        ((App) getContext().getApplicationContext()).getUserDetails().setShipping(userShipping);
                        Bundle bundle = new Bundle();
                        bundle.putString("shipping", "0");
                        
                        // Navigate to Billing_Address Fragment
                        Fragment fragment = new Billing_Address();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment)
                                .addToBackStack(null).commit();
                        
                    }
                }
            }
        });
        
        
        return rootView;
    }
//*********** Proceed Update of User's Details ********//
    
    private void processUpdateUser() {
        UserDetails user = new UserDetails();
        UserShipping shippingMethodsApi = new UserShipping();
        
        // Validate Address Form Inputs
        boolean isValidData = validateAddressForm();
        
        if (isValidData) {
            // New Instance of AddressDetails
            
            shippingMethodsApi.setFirstName(input_firstname.getText().toString().trim());
            shippingMethodsApi.setLastName(input_lastname.getText().toString().trim());
            shippingMethodsApi.setAddress1(input_address_1.getText().toString().trim());
            shippingMethodsApi.setAddress2(input_address_2.getText().toString().trim());
            shippingMethodsApi.setCompany(input_company.getText().toString().trim());
            shippingMethodsApi.setCity(input_city.getText().toString().trim());
            shippingMethodsApi.setPostcode(input_postcode.getText().toString().trim());
            
            if (selectedCountry != null) {
                shippingMethodsApi.setCountry(selectedCountry.getValue());
            } else {
                shippingMethodsApi.setCountry("");
            }
            
            if (selectedZone != null) {
                shippingMethodsApi.setState(selectedZone.getValue());
            } else {
                shippingMethodsApi.setState("");
            }
        }
        
        user.setShipping(shippingMethodsApi);
        updateCustomerInfo(user);
    }
    
    
    //*********** Updates User's Personal Information ********//
    
    private void updateCustomerInfo(UserDetails userDetails) {
        
        dialogLoader.showProgressDialog();
    
        String json = new Gson().toJson(userDetails);
        Call<UserDetails> call = APIClient.getInstance()
                .updateCustomerAddress
                        (       customerID,
                                userDetails
                        );
        
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, retrofit2.Response<UserDetails> response) {
                
                dialogLoader.hideProgressDialog();
                String url = response.raw().request().url().toString();
                // Check if the Response is successful
                if (response.isSuccessful()) {
                    String url2 = response.raw().request().url().toString();
                    String str = new Gson().toJson(response);
                    Log.d("Response",str);
                    
                    // New Instance of AddressDetails
                    userShipping = new UserShipping();
                    
                    userShipping.setFirstName(input_firstname.getText().toString().trim());
                    userShipping.setLastName(input_lastname.getText().toString().trim());
                    userShipping.setAddress1(input_address_1.getText().toString().trim());
                    userShipping.setAddress2(input_address_2.getText().toString().trim());
                    userShipping.setCompany(input_company.getText().toString().trim());
                    userShipping.setCity(input_city.getText().toString().trim());
                    userShipping.setPostcode(input_postcode.getText().toString().trim());
                    
                    if (selectedCountry != null) {
                        userShipping.setCountry(selectedCountry.getValue());
                    } else {
                        userShipping.setCountry("");
                    }
                    
                    if (selectedZone != null) {
                        userShipping.setState(selectedZone.getValue());
                    } else {
                        userShipping.setState("");
                     }
                    
                    
                    OrderDetails orderDetails = ((App) getContext().getApplicationContext()).getOrderDetails();
                    orderDetails.setShipping(userShipping);
                    
                    ((App) getContext().getApplicationContext()).setOrderDetails(orderDetails);
                    ((App) getContext().getApplicationContext()).getUserDetails().setShipping(userShipping);
                    
                    
                    Snackbar.make(rootView, getString(R.string.account_updated), Snackbar.LENGTH_SHORT).show();
                    
                    Bundle bundle = new Bundle();
                    bundle.putString("shipping", "1");
                    // Navigate to Billing_Address Fragment
                    Fragment fragment = new Billing_Address();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment)
                            .addToBackStack(null).commit();
                    
                }
                
                else {
                    
                    Converter<ResponseBody, UserData> converter = APIClient.retrofit.responseBodyConverter(UserData.class, new java.lang.annotation.Annotation[0]);
                    UserData userData;
                    try {
                        userData = converter.convert(response.errorBody());
                    } catch (IOException e) {
                        userData = new UserData();
                    }
                    
                    Toast.makeText(App.getContext(), "Error : "+userData.getError(), Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    //*********** Request User's Info form Server ********//
    
    public void RequestUserDetails() {
        
        dialogLoader.showProgressDialog();
        
        Call<UserDetails> call = APIClient.getInstance()
                .getUserInfo
                        (
                                customerID
                        );
        
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, retrofit2.Response<UserDetails> response) {
                
                dialogLoader.hideProgressDialog();
                
                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getShipping() != null) {
                        
                        userShipping = response.body().getShipping();
                        
                        // Set the Address details
                        input_firstname.setText(userShipping.getFirstName());
                        input_lastname.setText(userShipping.getLastName());
                        input_address_1.setText(userShipping.getAddress1());
                        input_address_2.setText(userShipping.getAddress2());
                        input_company.setText(userShipping.getCompany());
                        input_city.setText(userShipping.getCity());
                        input_postcode.setText(userShipping.getPostcode());
                        
                        String zone_code = "";
                        String country_code = "";
                        
                        if (!TextUtils.isEmpty(userShipping.getCountry())) {
                            country_code = userShipping.getCountry();
                            selectedCountry = locationHelper.getCountryFromCode(country_code);
                            input_country.setText(selectedCountry.getName());
                            
                            // Get Zones of the selected Country
                            zoneList.clear();
                            zoneList = new ArrayList<>();
                            zoneList = locationHelper.getStates(selectedCountry.getValue());
                            
                            zoneNames.clear();
                            
                            // Add the Zone Names to the zoneNames List
                            for (int x=0;  x<zoneList.size();  x++){
                                zoneNames.add(zoneList.get(x).getName());
                            }
                            zoneNames.add("Other");
                            
                            input_zone.setFocusableInTouchMode(true);
                        }
                        
                        
                        if (!TextUtils.isEmpty(userShipping.getState())) {
                            zone_code = userShipping.getState();
                            selectedZone = locationHelper.getStateFromCode(country_code, zone_code);
                            input_zone.setText(selectedZone.getName());
                        }
                    }
                    else {
                        // Set the Address details
                        input_firstname.setText("");
                        input_lastname.setText("");
                        input_address_1.setText("");
                        input_address_2.setText("");
                        input_company.setText("");
                        input_country.setText("");
                        input_zone.setText("");
                        input_city.setText("");
                        input_postcode.setText("");
                        
                        String zone_code = "";
                        String country_code = "";
                        
                        selectedCountry = null;
                        selectedZone = null;
                    }
                }
                else {
                    // Set the Address details
                    input_firstname.setText("");
                    input_lastname.setText("");
                    input_address_1.setText("");
                    input_address_2.setText("");
                    input_company.setText("");
                    input_country.setText("");
                    input_zone.setText("");
                    input_city.setText("");
                    input_postcode.setText("");
                    
                    String zone_code = "";
                    String country_code = "";
                    
                    selectedCountry = null;
                    selectedZone = null;
                }
            }
            
            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    
    //*********** Validate Address Form Inputs ********//
    
    private boolean validateAddressForm() {
        if (!ValidateInputs.isValidName(input_firstname.getText().toString().trim())) {
            input_firstname.setError(getString(R.string.invalid_first_name));
            return false;
        } else if (!ValidateInputs.isValidName(input_lastname.getText().toString().trim())) {
            input_lastname.setError(getString(R.string.invalid_last_name));
            return false;
        } else if (!ValidateInputs.isValidInput(input_address_1.getText().toString().trim())) {
            input_address_1.setError(getString(R.string.invalid_address));
            return false;
        } else if (!ValidateInputs.isIfValidInput(input_address_2.getText().toString().trim())) {
            input_address_2.setError(getString(R.string.invalid_address));
            return false;
        } else if (!ValidateInputs.isIfValidInput(input_company.getText().toString().trim())) {
            input_company.setError(getString(R.string.invalid_company));
            return false;
        } else if (!ValidateInputs.isValidInput(input_country.getText().toString().trim())) {
            input_country.setError(getString(R.string.select_country));
            return false;
        } else if (!ValidateInputs.isValidInput(input_zone.getText().toString().trim())) {
            input_zone.setError(getString(R.string.select_zone));
            return false;
        } else if (!ValidateInputs.isValidInput(input_city.getText().toString().trim())) {
            input_city.setError(getString(R.string.enter_city));
            return false;
        } else if (!ValidateInputs.isValidNumber(input_postcode.getText().toString().trim())) {
            input_postcode.setError(getString(R.string.invalid_post_code));
            return false;
        } else {
            return true;
        }
    }
    
}

