package com.vectorcoder.androidwoocommerce.fragments;


import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.R;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.vectorcoder.androidwoocommerce.activities.Login;
import com.vectorcoder.androidwoocommerce.adapters.CartItemsAdapter;
import com.vectorcoder.androidwoocommerce.adapters.CouponsAdapter;
import com.vectorcoder.androidwoocommerce.adapters.DemoCouponsListAdapter;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.customs.DialogLoader;
import com.vectorcoder.androidwoocommerce.databases.User_Cart_DB;
import com.vectorcoder.androidwoocommerce.models.api_response_model.ErrorResponse;
import com.vectorcoder.androidwoocommerce.models.cart_model.CartDetails;
import com.vectorcoder.androidwoocommerce.models.coupons_model.CouponDetails;
import com.vectorcoder.androidwoocommerce.models.order_model.OrderDetails;
import com.vectorcoder.androidwoocommerce.models.order_model.OrderProducts;
import com.vectorcoder.androidwoocommerce.models.order_model.OrderShippingMethod;
import com.vectorcoder.androidwoocommerce.models.user_model.UserBilling;
import com.vectorcoder.androidwoocommerce.models.user_model.UserShipping;
import com.vectorcoder.androidwoocommerce.network.APIClient;
import com.vectorcoder.androidwoocommerce.utils.Utilities;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;


public class My_Cart extends Fragment {
    
    View rootView;
    double cartSubTotal = 0;
    public static double cartDiscount;
    double cartTotalPrice = 0;
    boolean disableOtherCoupons = false;
    String customerID, customerToken, customerEmailAddress;

    EditText cart_coupon_code;
    LinearLayout cart_view, cart_view_empty, cart_prices;
    RecyclerView cart_items_recycler, cart_coupons_recycler;
    Button cart_checkout_btn, apply_coupon_btn, continue_shopping_btn;
    TextView cart_subtotal, cart_discount, cart_total_price, demo_coupons_text;
    
    DialogLoader dialogLoader;
    AlertDialog demoCouponsDialog;
    User_Cart_DB user_cart_db = new User_Cart_DB();
    
    CouponsAdapter couponsAdapter;
    CartItemsAdapter cartItemsAdapter;
    
    List<CartDetails> cartItemsList = new ArrayList<>();
    List<CouponDetails> couponsList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.my_cart, container, false);
    
        // This boolean Define this call comming from which fragmnet
        Checkout.CHECKOUT_CALL = false;
        
        setHasOptionsMenu(true);

        
        // cart discount to zero
        cartDiscount = 0.0;
        
        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.actionCart));
    
    
        // Get the customerEmailAddress and defaultAddressID from SharedPreferences
        customerEmailAddress = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userEmail", "");
    
        
        // Get the List of Cart Items from the Local Databases User_Cart_DB
        cartItemsList = new ArrayList<>();
        cartItemsList = user_cart_db.getCartItems();
        
    
        // Binding Layout Views
        cart_prices = (LinearLayout) rootView.findViewById(R.id.cart_prices);
        cart_view = (LinearLayout) rootView.findViewById(R.id.cart_view);
        cart_view_empty = (LinearLayout) rootView.findViewById(R.id.cart_view_empty);
        cart_subtotal = (TextView) rootView.findViewById(R.id.cart_subtotal);
        cart_discount = (TextView) rootView.findViewById(R.id.cart_discount);
        cart_total_price = (TextView) rootView.findViewById(R.id.cart_total_price);
        demo_coupons_text = (TextView) rootView.findViewById(R.id.demo_coupons_text);
        cart_coupon_code = (EditText) rootView.findViewById(R.id.cart_coupon_code);
        apply_coupon_btn = (Button) rootView.findViewById(R.id.cart_coupon_btn);
        cart_checkout_btn = (Button) rootView.findViewById(R.id.cart_checkout_btn);
        continue_shopping_btn = (Button) rootView.findViewById(R.id.continue_shopping_btn);
        cart_items_recycler = (RecyclerView) rootView.findViewById(R.id.cart_items_recycler);
        cart_coupons_recycler = (RecyclerView) rootView.findViewById(R.id.cart_coupons_recycler);
    
        
        cart_items_recycler.setNestedScrollingEnabled(false);
        cart_coupons_recycler.setNestedScrollingEnabled(false);


        // Change the Visibility of cart_view and cart_view_empty LinearLayout based on CartItemsList's Size
        if (cartItemsList.size() != 0) {
            cart_view.setVisibility(View.VISIBLE);
            cart_view_empty.setVisibility(View.GONE);
        } else {
            cart_view.setVisibility(View.GONE);
            cart_view_empty.setVisibility(View.VISIBLE);
        }
    
    
        dialogLoader = new DialogLoader(getContext());
        
        couponsList = new ArrayList<>();
        
        
        // Initialize the AddressListAdapter for RecyclerView
        cartItemsAdapter = new CartItemsAdapter(getContext(), cartItemsList, My_Cart.this);

        // Set the Adapter and LayoutManager to the RecyclerView
        cart_items_recycler.setAdapter(cartItemsAdapter);
        cart_items_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cartItemsAdapter.notifyDataSetChanged();
    
        
        // Initialize the CouponsAdapter for RecyclerView
        couponsAdapter = new CouponsAdapter(getContext(), couponsList, true, My_Cart.this);
    
        // Set the Adapter, LayoutManager and ItemDecoration to the RecyclerView
        cart_coupons_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cart_coupons_recycler.setAdapter(couponsAdapter);
        couponsAdapter.notifyDataSetChanged();
    
        
    
        // Update Cart's Total
        updateCart();
        
    
    
        if (!ConstantValues.IS_CLIENT_ACTIVE) {
            setupDemoCoupons();
        }
        else {
            demo_coupons_text.setVisibility(View.GONE);
        }
    
    
        // Handle the Click event of apply_coupon_btn Button
        apply_coupon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cart_coupon_code.getText().toString().isEmpty()) {
                    GetCouponInfo(cart_coupon_code.getText().toString());
                    dialogLoader.showProgressDialog();
                }
            }
        });
        

        // Handle Click event of continue_shopping_btn Button
        continue_shopping_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Bundle bundle = new Bundle();
                bundle.putBoolean("isSubFragment", false);
    
                // Navigate to Products Fragment
                Fragment fragment = new Products();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionCart)).commit();

            }
        });


        // Handle Click event of cart_checkout_btn Button
        cart_checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check if cartItemsList isn't empty
                if (cartItemsList.size() != 0) {

                    // Check if User is Logged-In
                    if (ConstantValues.IS_USER_LOGGED_IN  ||  ConstantValues.IS_GUEST_LOGGED_IN) {
    
                        proceedCheckout();
                        
                    }
                    else {
                        // Navigate to Login Activity
                        Intent i = new Intent(getContext(), Login.class);
                        i.putExtra("cart_navigation", true);
                        
                        if (ConstantValues.IS_GUEST_CHECKOUT_ENABLED) {
                            i.putExtra("show_guest", true);
                        }
                        
                        getContext().startActivity(i);
                        ((MainActivity) getContext()).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                    }
                }
            }
        });


        return rootView;
    }
    
    
    @Override
    public void onPause() {
        super.onPause();
    }
    
    
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getContext()).setupExpandableDrawerList();
        ((MainActivity) getContext()).setupExpandableDrawerHeader();
    }
    
    
    
    //*********** Change the Layout View of My_Cart Fragment based on Cart Items ********//

    public void updateCartView(int cartListSize) {

        // Check if Cart has some Items
        if (cartListSize != 0) {
            cart_view.setVisibility(View.VISIBLE);
            cart_view_empty.setVisibility(View.GONE);
        } else {
            cart_view.setVisibility(View.GONE);
            cart_view_empty.setVisibility(View.VISIBLE);
        }
    }
    
    
    //*********** Update Cart Products and Cart Coupons ********//
    
    public void updateCart() {
    
        double total = 0;
        double subtotal = 0;
        double totalDiscount = 0;
    
        // Calculate Cart's total Price
        for (int i=0;  i<cartItemsList.size();  i++) {
            subtotal += Double.parseDouble(cartItemsList.get(i).getCartProduct().getProductsFinalPrice());
        }
        
    
        for (int x=0;  x<cartItemsList.size();  x++) {
            cartItemsList.get(x).getCartProduct().setTotalPrice(cartItemsList.get(x).getCartProduct().getProductsFinalPrice());
        }
        
    
        if (couponsList.size() > 0) {
            for (int i=0;  i<couponsList.size();  i++) {
    
                int validItemsCount = 0;
                boolean cartHasValidItemsForCoupon = false;
                for (int x=0;  x<cartItemsList.size();  x++) {
                    if (couponsList.get(i).getValid_items().contains(cartItemsList.get(x).getCartID())) {
                        cartHasValidItemsForCoupon = true;
                        validItemsCount += cartItemsList.get(x).getCartProduct().getCustomersBasketQuantity();
                    }
    
                    couponsList.get(i).setValid_items_count(validItemsCount);
                }
                
                
                // Check if Coupon is Valid
                if (!validateCoupon(couponsList.get(i)) || !cartHasValidItemsForCoupon) {
    
                    for (int x=0;  x<couponsList.size();  x++) {
                        if (couponsList.get(i).getCode().equalsIgnoreCase(couponsList.get(x).getCode())) {
                            couponsList.remove(x);
                        }
                    }
                    couponsAdapter.notifyDataSetChanged();
                    
                }
                else {
                    double couponDiscount = 0.0;
                    
                    if (couponsList.get(i).getDiscountType().equalsIgnoreCase("fixed_product")) {
                        for (int x=0;  x<cartItemsList.size();  x++) {
                            if (couponsList.get(i).getValid_items().contains(cartItemsList.get(x).getCartID())) {
                                couponDiscount += (Double.parseDouble(couponsList.get(i).getAmount()) * cartItemsList.get(x).getCartProduct().getCustomersBasketQuantity());
                            }
                        }
                    }
                    else if (couponsList.get(i).getDiscountType().equalsIgnoreCase("fixed_cart")) {
                        couponDiscount = Double.parseDouble(couponsList.get(i).getAmount());
                    }
                    else if (couponsList.get(i).getDiscountType().equalsIgnoreCase("percent")) {
                        couponDiscount = (subtotal * Double.parseDouble(couponsList.get(i).getAmount())) / 100;
                    }
                    
    
                    double productDiscount = couponDiscount / couponsList.get(i).getValid_items_count();
    
                    for (int x=0;  x<cartItemsList.size();  x++) {
                        if (couponsList.get(i).getValid_items().contains(cartItemsList.get(x).getCartID())) {
                            double totalPrice = Double.parseDouble(cartItemsList.get(x).getCartProduct().getTotalPrice()) - (productDiscount * cartItemsList.get(x).getCartProduct().getCustomersBasketQuantity());
                            cartItemsList.get(x).getCartProduct().setTotalPrice(String.valueOf(totalPrice));
                        }
                    }
    
    
                    couponsList.get(i).setDiscount(String.valueOf(couponDiscount));
                }
            }
        }
    
    
        couponsAdapter.notifyDataSetChanged();
        cartItemsAdapter.notifyDataSetChanged();
        
    
        for (int i=0;  i<couponsList.size();  i++) {
            // Calculate total Discount
            totalDiscount += Double.parseDouble(couponsList.get(i).getDiscount());
        }
        
        
        total = subtotal - totalDiscount;
    
        cartTotalPrice  = total;
        cartSubTotal  = subtotal;
        cartDiscount  = totalDiscount;
        
        setCartTotal();
    
    }
    
    //*********** Calculate and Set the Cart's Total Price ********//
    
    public void setCartTotal() {
    
        cart_discount.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(cartDiscount));
        cart_subtotal.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(cartSubTotal));
        cart_total_price.setText(getString(R.string.total) +" : "+ ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(cartTotalPrice));
        
        
        if (couponsList.size() > 0) {
            cart_prices.setVisibility(View.VISIBLE);
        }
        else {
            cart_prices.setVisibility(View.GONE);
        }
        
    }
    
    
    
    //*********** Set Order Details and Proceed to Checkout ********//
    
    private void proceedCheckout() {
    
        // Get the customerID and customerToken and defaultAddressID from SharedPreferences
        customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        customerToken = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userCookie", "");
    
        
        OrderDetails orderDetails = new OrderDetails();
        List<OrderProducts> orderProductsList = new ArrayList<>();
        
        for (int i=0;  i<cartItemsList.size();  i++) {
            OrderProducts orderProduct = new OrderProducts();
    
            Log.i("variationID", "selectedVariationID = "+cartItemsList.get(i).getCartProduct().getSelectedVariationID());
            orderProduct.setId(cartItemsList.get(i).getCartProduct().getId());
            orderProduct.setProductId(cartItemsList.get(i).getCartProduct().getId());
            orderProduct.setVariationId(cartItemsList.get(i).getCartProduct().getSelectedVariationID());
            orderProduct.setQuantity(cartItemsList.get(i).getCartProduct().getCustomersBasketQuantity());
            orderProduct.setName(cartItemsList.get(i).getCartProduct().getName());
            orderProduct.setPrice(cartItemsList.get(i).getCartProduct().getPrice());
            orderProduct.setSubtotal(cartItemsList.get(i).getCartProduct().getProductsFinalPrice());
            orderProduct.setTotal(cartItemsList.get(i).getCartProduct().getTotalPrice());
            orderProduct.setTaxClass(cartItemsList.get(i).getCartProduct().getTaxClass());
            
            orderProductsList.add(orderProduct);
        }
    
    
        orderDetails.setSetPaid(false);
        orderDetails.setDiscountTotal(String.valueOf(cartDiscount));
        orderDetails.setTotal(String.valueOf(cartTotalPrice));
        orderDetails.setDateCreated(Utilities.getDateTime());
    
        orderDetails.setOrderCoupons(couponsList);
        orderDetails.setOrderProducts(orderProductsList);
        
        
        if (!ConstantValues.IS_GUEST_LOGGED_IN) {
            orderDetails.setToken(customerToken);
            orderDetails.setCustomerId(customerID);
        }
        else {
            orderDetails.setToken("");
            orderDetails.setCustomerId("");
        }
        
    
        if (ConstantValues.IS_ONE_PAGE_CHECKOUT_ENABLED) {
            
            UserBilling userBilling = new UserBilling();
            userBilling.setFirstName("");
            userBilling.setLastName("");
            userBilling.setAddress1("");
            userBilling.setAddress2("");
            userBilling.setCompany("");
            userBilling.setCity("");
            userBilling.setState("");
            userBilling.setCountry("");
            userBilling.setPostcode("");
            userBilling.setEmail("");
            userBilling.setPhone("");
    
            UserShipping userShipping = new UserShipping();
            userShipping.setFirstName("");
            userShipping.setLastName("");
            userShipping.setAddress1("");
            userShipping.setAddress2("");
            userShipping.setCompany("");
            userShipping.setCity("");
            userShipping.setState("");
            userShipping.setCountry("");
            userShipping.setPostcode("");
    
            List<OrderShippingMethod> shippingMethodsList = new ArrayList<>();
            OrderShippingMethod shippingMethods = new OrderShippingMethod();
            shippingMethods.setMethodId("");
            shippingMethods.setMethodTitle("");
            shippingMethods.setTotal("");
            shippingMethodsList.add(shippingMethods);
            
    
            orderDetails.setSameAddress(false);
            orderDetails.setBilling(userBilling);
            orderDetails.setShipping(userShipping);
            orderDetails.setOrderShippingMethods(shippingMethodsList);
            
        }
    
        
        // Save the OrderDetails
        ((App) getContext().getApplicationContext()).setOrderDetails(orderDetails);
        
        
        if (ConstantValues.IS_ONE_PAGE_CHECKOUT_ENABLED) {
            // Navigate to Checkout Fragment
            Fragment fragment = new Checkout();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment)
                    .addToBackStack(null).commit();
            
        }
        else {
            // Navigate to Shipping_Address Fragment
            Fragment fragment = new Shipping_Address();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment)
                    .addToBackStack(getString(R.string.actionCart)).commit();
        }
        
    }



    //*********** Static method to Add the given Item to User's Cart ********//

    public static void AddCartItem(CartDetails cartDetails) {
        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.addCartItem
                (
                        cartDetails
                );
    }


    //*********** Static method to Update the given Item in User's Cart ********//

    public static void UpdateCartItem(CartDetails cartDetails) {
        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.updateCartItem
                (
                        cartDetails
                );
    }


    //*********** Static method to Delete the given Item from User's Cart ********//

    public static void DeleteCartItem(int cart_item_id) {
        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.deleteCartItem
                (
                        cart_item_id
                );
    }
    
    
    //*********** Static method to Clear User's Cart ********//
    
    public static void ClearCart() {
        User_Cart_DB user_cart_db = new User_Cart_DB();
        user_cart_db.clearCart();
    }


    //*********** Static method to get total number of Items in User's Cart ********//

    public static int getCartSize() {
        int cartSize = 0;
        
        User_Cart_DB user_cart_db = new User_Cart_DB();
        List<CartDetails> cartItems = user_cart_db.getCartItems();
        
        for (int i=0;  i<cartItems.size();  i++) {
            cartSize += cartItems.get(i).getCartProduct().getCustomersBasketQuantity();
        }
        
        return cartSize;
    }


    //*********** Static method to check if the given Product is already in User's Cart ********//

    public static boolean checkCartHasProduct(int cart_item_id) {
        User_Cart_DB user_cart_db = new User_Cart_DB();
//        return user_cart_db.getCartItemsIDs().contains(cart_item_id);
        return false;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Hide Cart Icon in the Toolbar
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        cartItem.setVisible(false);
        searchItem.setVisible(true);
    }
    
    
    
    //*********** Request the Server to Get Coupon Details ********//
    
    private void GetCouponInfo(String coupon_code) {
    
        Map<String, String> params = new LinkedHashMap<>();
        params.put("code", coupon_code);
       
        
        Call<List<CouponDetails>> call = APIClient.getInstance()
                .getCouponInfo
                        (
                                params
                        );
        
        
        call.enqueue(new Callback<List<CouponDetails>>() {
            @Override
            public void onResponse(Call<List<CouponDetails>> call, retrofit2.Response<List<CouponDetails>> response) {
                
                dialogLoader.hideProgressDialog();
                
                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().size() > 0) {
                        
                        final CouponDetails couponDetails = response.body().get(0);
    
                        if (couponsList.size() != 0 && couponDetails.isIndividualUse()) {
        
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        
                            dialog.setTitle(getString(R.string.add_coupon));
                            dialog.setMessage(getString(R.string.coupon_removes_other_coupons));
        
                            dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                
                                    if (validateCoupon(couponDetails))
                                        applyCoupon(couponDetails);
                                }
                            });
        
                            dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
        
                        }
                        else {
                            if (validateCoupon(couponDetails))
                                applyCoupon(couponDetails);
                        }
                    }
                    else {
                        showSnackBarForCoupon(getString(R.string.invalid_coupon));
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
            public void onFailure(Call<List<CouponDetails>> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    
    //*********** Apply given Coupon to checkout ********//
    
    public void applyCoupon(CouponDetails coupon) {
    
        int validItemsCount = 0;
        double couponDiscount = 0.0;
        
        
        if (coupon.getDiscountType().equalsIgnoreCase("fixed_product")) {
            
            for (int i=0;  i<cartItemsList.size();  i++) {
                if (cartItemsList.get(i).isProductValidForCoupon()) {
                    validItemsCount += cartItemsList.get(i).getCartProduct().getCustomersBasketQuantity();
                    couponDiscount += (Double.parseDouble(coupon.getAmount()) * cartItemsList.get(i).getCartProduct().getCustomersBasketQuantity());
                }
            }
            
        }
        else if (coupon.getDiscountType().equalsIgnoreCase("fixed_cart")) {
    
            couponDiscount = Double.parseDouble(coupon.getAmount());
    
            for (int i=0;  i<cartItemsList.size();  i++) {
                if (cartItemsList.get(i).isProductValidForCoupon()) {
                    validItemsCount += cartItemsList.get(i).getCartProduct().getCustomersBasketQuantity();
                }
            }
            
        }
        else if (coupon.getDiscountType().equalsIgnoreCase("percent")) {
    
            couponDiscount = (cartSubTotal * Double.parseDouble(coupon.getAmount())) / 100;
    
            for (int i=0;  i<cartItemsList.size();  i++) {
                if (cartItemsList.get(i).isProductValidForCoupon()) {
                    validItemsCount += cartItemsList.get(i).getCartProduct().getCustomersBasketQuantity();
                }
            }
    
        }
    
    
        if ((couponDiscount + cartDiscount) >= cartSubTotal) {
            showSnackBarForCoupon(getString(R.string.coupon_cannot_be_applied));
            
        }
        else {
            
            double productDiscount = couponDiscount / validItemsCount;
    
            for (int i=0;  i<cartItemsList.size();  i++) {
                if (cartItemsList.get(i).isProductValidForCoupon()) {
                    if (0 > Double.parseDouble(cartItemsList.get(i).getCartProduct().getTotalPrice()) - productDiscount) {
                        cartItemsList.get(i).setProductValidForCoupon(false);
                        validItemsCount -= cartItemsList.get(i).getCartProduct().getCustomersBasketQuantity();
                    }
                }
            }
    
            
            for (int i=0;  i<cartItemsList.size();  i++) {
                if (cartItemsList.get(i).isProductValidForCoupon()) {
                    coupon.getValid_items().add(cartItemsList.get(i).getCartID());
                }
            }
    
    
            
            boolean coupon_applied_already = false;
    
            if (couponsList.size() != 0) {
                for (int i=0;  i<couponsList.size();  i++) {
                    if (coupon.getCode().equalsIgnoreCase(couponsList.get(i).getCode()))
                        coupon_applied_already = true;
                }
            }
            
    
            if (!disableOtherCoupons) {
                if (!coupon_applied_already) {
                    if (validItemsCount > 0) {
        
                        if (coupon.isIndividualUse()) {
                            couponsList.clear();
                            disableOtherCoupons = true;
                        }
    
    
                        coupon.setValid_items_count(validItemsCount);
                        coupon.setDiscount(String.valueOf(couponDiscount));
                        
        
        
                        couponsList.add(coupon);
                        cart_coupon_code.setText("");
                        couponsAdapter.notifyDataSetChanged();
        
        
                        updateCart();
        
                    }
                    else {
                        showSnackBarForCoupon(getString(R.string.coupon_cannot_be_applied));
                    }
                }
                else {
                    showSnackBarForCoupon(getString(R.string.coupon_applied));
                }
            }
            else {
                showSnackBarForCoupon(getString(R.string.coupon_cannot_used_with_existing));
            }
            
        }

    }
    
    
    
    //********** Remove given Coupon from checkout *******//
    
    public void removeCoupon(CouponDetails coupon) {
    
        for (int i=0;  i<couponsList.size();  i++) {
            if (coupon.getCode().equalsIgnoreCase(couponsList.get(i).getCode())) {
                couponsList.remove(i);
            }
        }
        
        updateCart();

    }
    
    
    
    //*********** Validate given Coupon ********//
    
    private boolean validateCoupon(CouponDetails coupon) {
    
        int user_used_this_coupon_counter = 0;
        
        boolean user_usage_limit_exceeds = false;               // false
        boolean coupon_usage_limit_exceeds = false;             // false
        boolean items_limit_exceeds_to_usage = false;           // false
    
        boolean user_email_valid_for_coupon = false;            // true
        
        boolean any_valid_item_in_cart = false;                 // true
        boolean any_valid_category_item_in_cart = false;        // true
        
        boolean all_sale_items_in_cart = true;                  // false
        boolean all_excluded_items_in_cart = true;              // false
        boolean all_excluded_category_items_in_cart = true;     // false
        
        
        for (int i=0;  i<coupon.getUsedBy().size();  i++) {
            if (!"".equalsIgnoreCase(customerID))
                if (coupon.getUsedBy().contains(customerID))
                    user_used_this_coupon_counter += 1;
        }
        
        
        if (coupon.getUsageLimitPerUser() != 0  &&  user_used_this_coupon_counter >= coupon.getUsageLimitPerUser()) {
            user_usage_limit_exceeds = true;
        }
        
        
        if (coupon.getUsageLimit() != 0  &&  coupon.getUsageCount() >= coupon.getUsageLimit()) {
            coupon_usage_limit_exceeds = true;
        }
    
    
        if (coupon.getLimitUsageToXItems() != 0  &&  cartItemsList.size() >= coupon.getLimitUsageToXItems()) {
            items_limit_exceeds_to_usage = true;
        }
        
        
        
        if (coupon.getEmailRestrictions().size() > 0  &&  !"".equalsIgnoreCase(customerEmailAddress)) {
            if (isStringExistsInList(customerEmailAddress, coupon.getEmailRestrictions())) {
                user_email_valid_for_coupon = true;
            }
        }
        else {
            user_email_valid_for_coupon = true;
        }
        
    
    
        for (int i=0;  i<cartItemsList.size();  i++) {
    
            boolean isValidProduct = true;
            boolean isExcludedOnSale = false;
            boolean isExcludedProduct = true;
            boolean anyValidCategory = false;
            boolean anyExcludedCategory = true;
            
    
            int productID = cartItemsList.get(i).getCartProduct().getId();
            List<String> categoryIDs = new ArrayList<>();
            if (!"".equalsIgnoreCase(cartItemsList.get(i).getCartProduct().getCategoryIDs())  &&  cartItemsList.get(i).getCartProduct().getCategoryIDs() != null)
                categoryIDs = Arrays.asList(cartItemsList.get(i).getCartProduct().getCategoryIDs().replaceAll("\\s", "").split(","));
            
            List<Integer> categoryIDsList = new ArrayList<>();
            if (categoryIDs.size() > 0) {
                for (int j=0;  j<categoryIDs.size();  j++) {
                    categoryIDsList.add(Integer.parseInt(categoryIDs.get(j)));
                }
            }
            
    
            if (coupon.isExcludeSaleItems()) {
                if (!cartItemsList.get(i).getCartProduct().isOnSale()) {
                    all_sale_items_in_cart = false;
                }
                else {
                    isExcludedOnSale = true;
                }
            }
            else {
                all_sale_items_in_cart = false;
            }
    
            
            if (coupon.getProductIds().size() > 0) {
                if (coupon.getProductIds().contains(productID)) {
                    any_valid_item_in_cart = true;
                }
                else {
                    isValidProduct = false;
                }
            }
            else {
                any_valid_item_in_cart = true;
            }
    
    
            if (coupon.getProductCategories().size() > 0  &&  categoryIDs.size() > 0) {
                for (int y=0;  y<categoryIDs.size();  y++) {
                    if (coupon.getProductCategories().contains(categoryIDsList.get(y))) {
                        anyValidCategory = true;
                        any_valid_category_item_in_cart = true;
                    }
                }
            }
            else {
                anyValidCategory = true;
                any_valid_category_item_in_cart = true;
            }
    
    
            if (coupon.getExcludedProductIds().size() > 0) {
                if (!coupon.getExcludedProductIds().contains(productID)) {
                    isExcludedProduct = false;
                    all_excluded_items_in_cart = false;
                }
            }
            else {
                isExcludedProduct = false;
                all_excluded_items_in_cart = false;
            }
    
    
            if (coupon.getExcludedProductCategories().size() > 0  &&  categoryIDs.size() > 0) {
                for (int y=0;  y<categoryIDs.size();  y++) {
                    if (!coupon.getExcludedProductCategories().contains(categoryIDsList.get(y))) {
                        anyExcludedCategory = false;
                        all_excluded_category_items_in_cart = false;
                    }
                }
            }
            else {
                anyExcludedCategory = false;
                all_excluded_category_items_in_cart = false;
            }
            
            
            
            if (!isExcludedOnSale && !isExcludedProduct && !anyExcludedCategory && isValidProduct && anyValidCategory) {
                cartItemsList.get(i).setProductValidForCoupon(true);
            }
            else {
                cartItemsList.get(i).setProductValidForCoupon(false);
            }
            
        }
        
    
        if (coupon.getExpiryDate() == null  ||  !Utilities.checkIsDatePassed(coupon.getExpiryDate())) {
            if (!coupon_usage_limit_exceeds) {
                if (!user_usage_limit_exceeds) {
                    if (user_email_valid_for_coupon) {
                        if (Double.parseDouble(coupon.getMinimumAmount()) <= cartSubTotal) {
                            if (Double.parseDouble(coupon.getMaximumAmount()) == 0.0  ||  cartSubTotal <= Double.parseDouble(coupon.getMaximumAmount())) {
                                if (!items_limit_exceeds_to_usage) {
                                    if (!all_sale_items_in_cart) {
                                        if (!all_excluded_category_items_in_cart) {
                                            if (!all_excluded_items_in_cart) {
                                                if (any_valid_category_item_in_cart) {
                                                    if (any_valid_item_in_cart) {
                                                    
                                                        return true;
                                                    
                                                    } else {
                                                        showSnackBarForCoupon(getString(R.string.coupon_is_not_for_these_products));
                                                        return false;
                                                    }
                                                } else {
                                                    showSnackBarForCoupon(getString(R.string.coupon_is_not_for_these_categories));
                                                    return false;
                                                }
                                            } else {
                                                showSnackBarForCoupon(getString(R.string.coupon_is_not_for_excluded_products));
                                                return false;
                                            }
                                        } else {
                                            showSnackBarForCoupon(getString(R.string.coupon_is_not_for_excluded_categories));
                                            return false;
                                        }
                                    } else {
                                        showSnackBarForCoupon(getString(R.string.coupon_is_not_for_sale_items));
                                        return false;
                                    }
                                } else {
                                    showSnackBarForCoupon(getString(R.string.coupon_is_not_for_too_many_products));
                                    return false;
                                }
                            } else {
                                showSnackBarForCoupon(getString(R.string.coupon_max_amount_is_less_than_order_total));
                                return false;
                            }
                        } else {
                            showSnackBarForCoupon(getString(R.string.coupon_min_amount_is_greater_than_order_total));
                            return false;
                        }
                    } else {
                        showSnackBarForCoupon(getString(R.string.coupon_is_not_for_you));
                        return false;
                    }
                } else {
                    showSnackBarForCoupon(getString(R.string.coupon_used_by_you));
                    return false;
                }
            } else {
                showSnackBarForCoupon(getString(R.string.coupon_used_by_all));
                return false;
            }
        } else {
            cart_coupon_code.setError(getString(R.string.coupon_expired));
            return false;
        }

    }
    
    
    
    //*********** Show SnackBar with given Message  ********//
    
    private void showSnackBarForCoupon(String msg) {
        final Snackbar snackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    
    
    
    //*********** Check if the given String exists in the given List ********//
    
    private boolean isStringExistsInList(String str, List<String> stringList) {
        boolean isExists = false;
        
        for (int i=0;  i<stringList.size();  i++) {
            if (stringList.get(i).equalsIgnoreCase(str)) {
                isExists = true;
            }
        }
        
        return isExists;
    }
    
    
    
    //*********** Setup Demo Coupons Dialog ********//
    
    private void setupDemoCoupons() {
        
        demo_coupons_text.setVisibility(View.VISIBLE);
        demo_coupons_text.setPaintFlags(demo_coupons_text.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        
        demo_coupons_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                final List<CouponDetails> couponsList = demoCouponsList();
                DemoCouponsListAdapter couponsListAdapter = new DemoCouponsListAdapter(getContext(), couponsList, My_Cart.this);
                
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_list, null);
                dialog.setView(dialogView);
                dialog.setCancelable(false);
                
                Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
                TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
                ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);
                
                dialog_title.setText(getString(R.string.search) +" "+ getString(R.string.coupon));
                dialog_list.setVerticalScrollBarEnabled(true);
                dialog_list.setAdapter(couponsListAdapter);
                
                demoCouponsDialog = dialog.create();
                
                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        demoCouponsDialog.dismiss();
                    }
                });
                
                demoCouponsDialog.show();
            }
        });
    }
    
    
    
    //*********** Sets selected Coupon code from the Dialog ********//
    
    public void setCouponCode(String code) {
        cart_coupon_code.setText(code);
        demoCouponsDialog.dismiss();
    }
    
    
    
    //*********** Demo Coupons List ********//
    
    private List<CouponDetails> demoCouponsList() {
        List<CouponDetails> couponsList = new ArrayList<>();
        
        CouponDetails coupon2 = new CouponDetails();
        coupon2.setCode("fixed_product_10");
        coupon2.setAmount("10");
        coupon2.setDiscountType("fixed_product");
        coupon2.setDescription("For All Products");
        
        CouponDetails coupon3 = new CouponDetails();
        coupon3.setCode("percent_cart_10");
        coupon3.setAmount("10");
        coupon3.setDiscountType("percent");
        coupon3.setDescription("For All Products");
        
        CouponDetails coupon4 = new CouponDetails();
        coupon4.setCode("fixed_cart_10");
        coupon4.setAmount("10");
        coupon4.setDiscountType("fixed_cart");
        coupon4.setDescription("For All Products");
        
        CouponDetails coupon5 = new CouponDetails();
        coupon5.setCode("single_coupon_50");
        coupon5.setAmount("50");
        coupon5.setDiscountType("fixed_cart");
        coupon5.setDescription("Individual Use");
        
        CouponDetails coupon6 = new CouponDetails();
        coupon6.setCode("free_shipping_20");
        coupon6.setAmount("20");
        coupon6.setDiscountType("fixed_cart");
        coupon6.setDescription("Free Shipping");
        
        CouponDetails coupon7 = new CouponDetails();
        coupon7.setCode("exclude_sale_15");
        coupon7.setAmount("15");
        coupon7.setDiscountType("fixed_cart");
        coupon7.setDescription("Not for Sale Items");
        
        
        couponsList.add(coupon2);
        couponsList.add(coupon3);
        couponsList.add(coupon4);
        couponsList.add(coupon5);
        couponsList.add(coupon6);
        couponsList.add(coupon7);
        
        return couponsList;
    }
    
}

