package com.vectorcoder.androidwoocommerce.fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.google.gson.Gson;
import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.activities.Login;
import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.adapters.GroupedProductsAdapter;
import com.vectorcoder.androidwoocommerce.adapters.ProductAdapter;
import com.vectorcoder.androidwoocommerce.adapters.ProductAttributesAdapter;
import com.vectorcoder.androidwoocommerce.adapters.ProductReviewsAdapter;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.customs.DialogLoader;
import com.vectorcoder.androidwoocommerce.customs.DividerItemDecoration;
import com.vectorcoder.androidwoocommerce.databases.User_Favorites_DB;
import com.vectorcoder.androidwoocommerce.models.api_response_model.ErrorResponse;
import com.vectorcoder.androidwoocommerce.models.cart_model.CartDetails;
import com.vectorcoder.androidwoocommerce.models.device_model.AppSettingsDetails;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductAttributes;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductDetails;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductImages;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductReviews;
import com.vectorcoder.androidwoocommerce.models.seller_detail_model.SellerDetailModel;
import com.vectorcoder.androidwoocommerce.models.user_model.Nonce;
import com.vectorcoder.androidwoocommerce.models.user_model.UserData;
import com.vectorcoder.androidwoocommerce.network.APIClient;
import com.vectorcoder.androidwoocommerce.utils.Utilities;
import com.vectorcoder.androidwoocommerce.utils.ValidateInputs;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import hyogeun.github.com.colorratingbarlib.ColorRatingBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;


public class Product_Description extends Fragment implements BaseSliderView.OnSliderClickListener {
    
    View rootView;
    SellerDetailModel sellerDetailModel;
    int productID;
    int sellerID;
    int selectedVariationID = 0;
    double productBasePrice = 0;
    double productFinalPrice = 0;
    
    String customerID;
    String[] attributesNames;
    
    Button productCartBtn,store_btn;
    ImageView sliderImageView;
    SliderLayout sliderLayout;
    PagerIndicator pagerIndicator;
    ToggleButton product_like_btn;
    ColorRatingBar product_rating_bar;
    ListView attributes_list_view;
    WebView product_description_webView, product_price_webView;
    ImageButton product_share_btn, product_quantity_plusBtn, product_quantity_minusBtn;
    RecyclerView grouped_products_recycler, product_metadata_recycler, related_products_recycler;
    TextView title, category, product_ratings_count, product_stock, product_total_price, product_tag_new, product_tag_sale, product_tag_featured, product_item_quantity,
            store_name,seller_name;
    LinearLayout product_reviews_ratings, product_attributes, simple_product, grouped_products, product_metadata, related_products, product_description,
    seller_div;
    
    public DialogLoader dialogLoader;
    public ProductDetails productDetails;
    ProductAdapter relatedProductsAdapter;
    ProductAttributesAdapter attributesAdapter;
    GroupedProductsAdapter groupedProductsAdapter;
    
    private User_Favorites_DB favorites_db;
    
    List<Integer> productIDsList;
    List<ProductImages> itemImages;
    List<ProductDetails> relatedProductsList;
    List<ProductDetails> groupedProductsList;
    List<ProductDetails> allProductVariationsList;
    
    public List<ProductReviews> productReviews;
    public List<ProductDetails> variationsList;
    public List<ProductAttributes> productAttributesList;
    public LinkedList<ProductAttributes> attributesList;
    public LinkedList<ProductAttributes> selectedAttributesList;
    AppSettingsDetails appSettings;
    
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.product_description, container, false);
    
        setHasOptionsMenu(true);
        
        // Set the Title of Toolbar
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.product_description));
        appSettings = ((App) getContext().getApplicationContext()).getAppSettingsDetails();
        
       
        
        
        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        
        favorites_db = new User_Favorites_DB();
    
        // Binding Layout Views
        productCartBtn = (Button) rootView.findViewById(R.id.product_cart_btn);
        store_btn = (Button)rootView.findViewById(R.id.store_btn);
        title = (TextView) rootView.findViewById(R.id.product_title);
        category = (TextView) rootView.findViewById(R.id.product_category);
        product_stock = (TextView) rootView.findViewById(R.id.product_stock);
        product_total_price = (TextView) rootView.findViewById(R.id.product_total_price);
        product_tag_new = (TextView) rootView.findViewById(R.id.product_tag_new);
        product_tag_sale = (TextView) rootView.findViewById(R.id.product_tag_sale);
        product_tag_featured = (TextView) rootView.findViewById(R.id.product_tag_featured);
        product_item_quantity = (TextView) rootView.findViewById(R.id.product_item_quantity);
        product_ratings_count = (TextView) rootView.findViewById(R.id.product_ratings_count);
        store_name = (TextView)rootView.findViewById(R.id.store_name);
        seller_name = (TextView)rootView.findViewById(R.id.seller_name);
        product_like_btn = (ToggleButton) rootView.findViewById(R.id.product_like_btn);
        product_share_btn = (ImageButton) rootView.findViewById(R.id.product_share_btn);
        product_quantity_plusBtn = (ImageButton) rootView.findViewById(R.id.product_item_quantity_plusBtn);
        product_quantity_minusBtn = (ImageButton) rootView.findViewById(R.id.product_item_quantity_minusBtn);
        related_products = (LinearLayout) rootView.findViewById(R.id.related_products);
        simple_product = (LinearLayout) rootView.findViewById(R.id.simple_product);
        grouped_products = (LinearLayout) rootView.findViewById(R.id.grouped_products);
        product_metadata = (LinearLayout) rootView.findViewById(R.id.product_metadata);
        product_attributes = (LinearLayout) rootView.findViewById(R.id.product_attributes);
        product_description = (LinearLayout) rootView.findViewById(R.id.product_description);
        product_reviews_ratings = (LinearLayout) rootView.findViewById(R.id.product_reviews_ratings);
        seller_div = (LinearLayout) rootView.findViewById(R.id.seller_div);
        product_rating_bar = (ColorRatingBar ) rootView.findViewById(R.id.product_rating_bar);
        sliderLayout = (SliderLayout) rootView.findViewById(R.id.product_cover_slider);
        pagerIndicator = (PagerIndicator) rootView.findViewById(R.id.product_slider_indicator);
        product_price_webView = (WebView) rootView.findViewById(R.id.product_price_webView);
        product_description_webView = (WebView) rootView.findViewById(R.id.product_description_webView);
        attributes_list_view = (ListView) rootView.findViewById(R.id.attributes_list_view);
        related_products_recycler = (RecyclerView) rootView.findViewById(R.id.related_products_recycler);
        grouped_products_recycler = (RecyclerView) rootView.findViewById(R.id.grouped_products_recycler);
        product_metadata_recycler = (RecyclerView) rootView.findViewById(R.id.product_metadata_recycler);
    
        grouped_products_recycler.setNestedScrollingEnabled(false);
        product_metadata_recycler.setNestedScrollingEnabled(false);
        related_products_recycler.setNestedScrollingEnabled(false);
        
        category.setVisibility(View.GONE);
        product_tag_new.setVisibility(View.GONE);
        product_tag_sale.setVisibility(View.GONE);
        product_tag_featured.setVisibility(View.GONE);
        related_products.setVisibility(View.GONE);
        product_metadata.setVisibility(View.GONE);
        grouped_products.setVisibility(View.GONE);
        product_attributes.setVisibility(View.GONE);
        
        // Checking RTL
        boolean isRightToLeft = getContext().getResources().getBoolean(R.bool.is_right_to_left);
        if(isRightToLeft){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            product_stock.setLayoutParams(params);
            Utilities.setMargins(getContext(),product_stock,0,6,0,0);
        }
        
        
        dialogLoader = new DialogLoader(getContext());
    
        productIDsList = new ArrayList<>();
        productReviews = new ArrayList<>();
        relatedProductsList = new ArrayList<>();
        groupedProductsList = new ArrayList<>();
        allProductVariationsList = new ArrayList<>();
        
        groupedProductsAdapter = new GroupedProductsAdapter(getContext(), groupedProductsList, this);
        
        // Get product Info from bundle arguments
        if (getArguments() != null) {
            
            if (getArguments().containsKey("itemID")) {
                productID = getArguments().getInt("itemID");
    
                RequestProductDetail(productID);
                
            }
            else if (getArguments().containsKey("productDetails")) {
                productDetails = getArguments().getParcelable("productDetails");
                // Set Product Details
                setProductDetails(productDetails);
            }
        }
    
    
        // Get if multivender is on or off
    
        if("1".equalsIgnoreCase(appSettings.getDokan_enabled())){
            seller_div.setVisibility(View.VISIBLE);
            sellerID = productDetails.getStore().getId();
            // Request for seller information
            RequestSellerInfo();
        
            store_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get Product Info
                    Bundle bundle = new Bundle();
                    bundle.putInt(ConstantValues.SELLER_ID, productDetails.getStore().getId());
                
                    // Navigate to Product_Description of selected Product
                    Fragment fragment = new SellerDetail();
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();
                
                }
            });
        }
        
        return rootView;
        
    }
    
    // Request seller information
    
    private void RequestSellerInfo(){
        
        dialogLoader.showProgressDialog();
        Call<SellerDetailModel> call = APIClient.getInstance().getSellerInfo(""+sellerID);
        
        call.enqueue(new Callback<SellerDetailModel>() {
            @Override
            public void onResponse(Call<SellerDetailModel> call, Response<SellerDetailModel> response) {
                
                if (response.isSuccessful()) {
                    
                    dialogLoader.hideProgressDialog();
                    sellerDetailModel = response.body();
                    
                    // Setting values to place holders
                    
                    String sellerName = sellerDetailModel.getFirstName()+" "+sellerDetailModel.getLastName();
                    seller_name.setText(sellerName);
                   
                    
                }
                else {
                    dialogLoader.hideProgressDialog();
                    Toast.makeText(getContext(), "Error : "+response.message(), Toast.LENGTH_SHORT).show();
                }
                
            }
            
            @Override
            public void onFailure(Call<SellerDetailModel> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
        
    }
    
    
    //*********** Adds Product's Details to the Views ********//
    
    private void setProductDetails(final ProductDetails product) {
        
        productDetails = product;
    
        getProductReviews(String.valueOf(productDetails.getId()));
        
        
        // Check Product's Type
        if (productDetails.getType().equalsIgnoreCase("external")) {
    
            simple_product.setVisibility(View.GONE);
            grouped_products.setVisibility(View.GONE);
            product_attributes.setVisibility(View.GONE);
            productCartBtn.setText(getString(R.string.view_product));
    
            
            
            // Check if the Product is Out of Stock
            if (!productDetails.isInStock()) {
                
                product_stock.setText(getString(R.string.outOfStock));
                product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentRed));
            }
            else {
                product_stock.setText(getString(R.string.in_stock));
                product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentBlue));
            }
        
        }
        else if (productDetails.getType().equalsIgnoreCase("grouped")) {
            if (productDetails.getGroupedProducts().size() > 0) {
    
                simple_product.setVisibility(View.GONE);
                product_attributes.setVisibility(View.GONE);
                grouped_products.setVisibility(View.VISIBLE);
                
                
                // Set the Adapter and LayoutManager to the RecyclerView
                grouped_products_recycler.setAdapter(groupedProductsAdapter);
                grouped_products_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                grouped_products_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
                groupedProductsAdapter.notifyDataSetChanged();
                
                productIDsList.addAll(productDetails.getGroupedProducts());
                GetLinkedProducts getLinkedProducts = new GetLinkedProducts(getContext(), productIDsList);
                getLinkedProducts.execute();
                
                
                // Check if the Product is Out of Stock
                if (!productDetails.isInStock()) {
                    product_stock.setText(getString(R.string.outOfStock));
                    product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentRed));
                    productCartBtn.setText(getString(R.string.outOfStock));
                    productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_red));
                }
                else {
                    product_stock.setText(getString(R.string.in_stock));
                    product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentBlue));
                    productCartBtn.setText(getString(R.string.addToCart));
                    productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_gray));
                }
                
            }
            
        }
        else if (productDetails.getType().equalsIgnoreCase("variable")) {
            if (productDetails.getVariations().size() > 0) {
    
                simple_product.setVisibility(View.VISIBLE);
                grouped_products.setVisibility(View.GONE);
                product_attributes.setVisibility(View.VISIBLE);
                
                
                productIDsList.add(productDetails.getId());
                GetLinkedProducts getLinkedProducts = new GetLinkedProducts(getContext(), productIDsList);
                getLinkedProducts.execute();
    
    
                productAttributesList = new ArrayList<>();
                productAttributesList = productDetails.getAttributes();
                attributesList = new LinkedList<>();
                selectedAttributesList = new LinkedList<>();
    
                attributesNames = new String[productAttributesList.size()];
                
                for (int i=0;  i<productAttributesList.size();  i++) {
                    ProductAttributes nullAttr = new ProductAttributes();
                    selectedAttributesList.add(i, nullAttr);
                    attributesList.add(i, productAttributesList.get(i));
                    attributesNames[i] = productAttributesList.get(i).getName();
                }
                
    
                // Set the Adapter and LayoutManager to the RecyclerView
                attributesAdapter = new ProductAttributesAdapter(getContext(), productAttributesList);
                attributes_list_view.setAdapter(attributesAdapter);
    
                
                attributes_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        handleAttributeListClick(view, position);
                    }
                });
                
    
                // Check if the Product is Out of Stock
                if (!productDetails.isInStock()) {
                    simple_product.setVisibility(View.GONE);
                    product_stock.setText(getString(R.string.outOfStock));
                    product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentRed));
                    productCartBtn.setText(getString(R.string.outOfStock));
                    productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_red));
                }
                else {
                    simple_product.setVisibility(View.VISIBLE);
                    product_stock.setText(getString(R.string.in_stock));
                    product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentBlue));
                    productCartBtn.setText(getString(R.string.addToCart));
                    productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_accent));
                }
    
                
                if (productDetails.getAttributes().size() > 0) {
                    toggleCartButton(false);
                }
                
                
            }
        
        }
        else if (productDetails.getType().equalsIgnoreCase("variation")) {
            simple_product.setVisibility(View.VISIBLE);
            grouped_products.setVisibility(View.GONE);
            product_attributes.setVisibility(View.GONE);
    
            // Check if the Product is Out of Stock
            if (!productDetails.isInStock()) {
                simple_product.setVisibility(View.GONE);
                product_stock.setText(getString(R.string.outOfStock));
                product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentRed));
                productCartBtn.setText(getString(R.string.outOfStock));
                productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_red));
            }
            else {
                simple_product.setVisibility(View.VISIBLE);
                product_stock.setText(getString(R.string.in_stock));
                product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentBlue));
                productCartBtn.setText(getString(R.string.addToCart));
                productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_accent));
            }
    
        }
        else {
            simple_product.setVisibility(View.VISIBLE);
            grouped_products.setVisibility(View.GONE);
            product_attributes.setVisibility(View.GONE);
    
            // Check if the Product is Out of Stock
            if (!productDetails.isInStock()) {
                simple_product.setVisibility(View.GONE);
                product_stock.setText(getString(R.string.outOfStock));
                product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentRed));
                productCartBtn.setText(getString(R.string.outOfStock));
                productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_red));
            }
            else {
                simple_product.setVisibility(View.VISIBLE);
                product_stock.setText(getString(R.string.in_stock));
                product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentBlue));
                productCartBtn.setText(getString(R.string.addToCart));
                productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_accent));
            }
            
        }
        
        // Get Product Images and Attributes
        itemImages = new ArrayList<>();
        itemImages.addAll(productDetails.getImages());
        productDetails.setImage(itemImages.get(0).getSrc());
        
        // Setup the ImageSlider of Product Images
        ImageSlider(productDetails.getImage(), itemImages);
        
        
        // Set Product's Information
        title.setText(productDetails.getName());
    
    
        String[] categoryIDs = new String[productDetails.getCategories().size()];
        String[] categoryNames = new String[productDetails.getCategories().size()];
        if (productDetails.getCategories().size() > 0) {
        
            for (int i=0;  i<productDetails.getCategories().size();  i++) {
                categoryIDs[i] = String.valueOf(productDetails.getCategories().get(i).getId());
                categoryNames[i] = productDetails.getCategories().get(i).getName();
            }
    
            productDetails.setCategoryIDs(TextUtils.join(",", categoryIDs));
            productDetails.setCategoryNames(TextUtils.join(",", categoryNames));
        }
        else {
            productDetails.setCategoryIDs("");
            productDetails.setCategoryNames("");
        }
    
    
    
        product_ratings_count.setText(String.valueOf(productDetails.getRatingCount()));
    
        if (productDetails.getAverageRating() != null  &&  !TextUtils.isEmpty(productDetails.getAverageRating())) {
            product_rating_bar.setRating(Float.parseFloat(productDetails.getAverageRating()));
        }
        
        
        // Check if the Product is Newly Added with the help of static method of Helper class
        if (Utilities.checkNewProduct(productDetails.getDateCreated())) {
            product_tag_new.setVisibility(View.VISIBLE);
        } else {
            product_tag_new.setVisibility(View.GONE);
        }
    
        // Set Featured Tag
        product_tag_featured.setVisibility(productDetails.isFeatured()? View.VISIBLE : View.GONE);
    
        // Set Discount Tag
        product_tag_sale.setVisibility(productDetails.isOnSale()? View.VISIBLE : View.GONE);
        
    
        if (productDetails.getRelatedIds().size() > 0) {
            related_products.setVisibility(View.VISIBLE);
            
            // Initialize the ProductAdapter and LayoutManager for Related Products RecyclerView
            relatedProductsAdapter = new ProductAdapter(getContext(), relatedProductsList, true);
            related_products_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            related_products_recycler.setAdapter(relatedProductsAdapter);
            
            for (int i=0;  i<productDetails.getRelatedIds().size();  i++) {
                RequestRelatedProducts(productDetails.getRelatedIds().get(i));
            }
        }
    
    
        productDetails.setCustomersBasketQuantity(1);
        
        if (!"".equalsIgnoreCase(productDetails.getPrice()))
            productBasePrice = Double.parseDouble(productDetails.getPrice());
            
        updateProductPrices();
        
        
        String productPrice = productDetails.getPriceHtml();
        String price_styleSheet =   "<style> " +
                                        "body{margin:0; padding:0} " +
                                        "p{color:#757575;} " +
                                        "img{display:inline; height:auto; max-width:100%;}" +
                                    "</style>";
    
        product_price_webView.setVerticalScrollBarEnabled(false);
        product_price_webView.setHorizontalScrollBarEnabled(false);
        product_price_webView.setBackgroundColor(Color.TRANSPARENT);
        product_price_webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        product_price_webView.loadDataWithBaseURL(null, price_styleSheet+productPrice, "text/html", "utf-8", null);
        
        
        
        if (productDetails.getDescription() == null  &&  TextUtils.isEmpty(productDetails.getDescription())) {
            product_description.setVisibility(View.GONE);
        }
        else {
            product_description.setVisibility(View.VISIBLE);
            
            String description = productDetails.getDescription();
            String desc_styleSheet =    "<style> " +
                    "body{margin:0; padding:0} " +
                    "p{color:#757575;} " +
                    "img{display:inline; height:auto; max-width:100%;}" +
                    "</style>";
    
            product_description_webView.setVerticalScrollBarEnabled(false);
            product_description_webView.setHorizontalScrollBarEnabled(false);
            product_description_webView.setBackgroundColor(Color.TRANSPARENT);
            product_description_webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
            product_description_webView.loadDataWithBaseURL(null, desc_styleSheet+description, "text/html", "utf-8", null);
        }
        
    
        // Holds Product Quantity
        final int[] number = {1};
        number[0] = productDetails.getCustomersBasketQuantity();
        product_item_quantity.setText(String.valueOf(productDetails.getCustomersBasketQuantity()));
    
    
        // Decrease Product Quantity
        product_quantity_minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the Quantity is greater than the minimum Quantity
                if (number[0] > 1)
                {
                    // Decrease Quantity by 1
                    number[0] = number[0] - 1;
                    
                    product_item_quantity.setText(""+ number[0]);
                    productDetails.setCustomersBasketQuantity(number[0]);
                    
                    updateProductPrices();
                }
            }
        });
    
    
        // Increase Product Quantity
        product_quantity_plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the Quantity is less than the maximum or stock Quantity
                if (productDetails.getStockQuantity() == null ||  number[0] < Long.parseLong(productDetails.getStockQuantity())) {
                    // Increase Quantity by 1
                    number[0] = number[0] + 1;
    
                    product_item_quantity.setText(""+ number[0]);
                    productDetails.setCustomersBasketQuantity(number[0]);
                    
                    updateProductPrices();
                }
            }
        });
        
        
        // Handle Click event of product_share_btn Button
        product_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Share Product with the help of static method of Helper class
                Utilities.shareProduct
                        (
                                getContext(),
                                productDetails.getName(),
                                sliderImageView,
                                productDetails.getPermalink()
                        );
            }
        });
        
        
        if (favorites_db.getUserFavorites().contains(productDetails.getId())) {
            productDetails.setIsLiked("1");
            product_like_btn.setChecked(true);
        }
        else {
            productDetails.setIsLiked("0");
            product_like_btn.setChecked(false);
        }
        
        
        // Handle Click event of product_like_btn Button
        product_like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                // Check if the User has Checked the Like Button
                if(product_like_btn.isChecked()) {
                    productDetails.setIsLiked("1");
                    product_like_btn.setChecked(true);
                    
                    // Add the Product to User's Favorites
                    if (!favorites_db.getUserFavorites().contains(productDetails.getId())) {
                        favorites_db.insertFavoriteItem(productDetails.getId());
                    }
    
                    Snackbar.make(view, getContext().getString(R.string.added_to_favourites), Snackbar.LENGTH_SHORT).show();
                
                } else {
                    productDetails.setIsLiked("0");
                    product_like_btn.setChecked(false);
                    
                    // Remove the Product from User's Favorites
                    if (favorites_db.getUserFavorites().contains(productDetails.getId())) {
                        favorites_db.deleteFavoriteItem(productDetails.getId());
                    }
    
                    Snackbar.make(view, getContext().getString(R.string.removed_from_favourites), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    
    
        // Handle Click event of product_reviews_ratings Button
        product_reviews_ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingsAndReviewsOfProduct();
            }
        });
        
        
        // Handle Click event of productCartBtn Button
        productCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if (productDetails.getType().equalsIgnoreCase("external")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(productDetails.getPermalink())));
                    
                }
                else if (productDetails.getType().equalsIgnoreCase("grouped")) {
                    
                    int cartSizeBefore = My_Cart.getCartSize();
                    
                    for (int i=0;  i<groupedProductsList.size();  i++) {
                        if (groupedProductsList.get(i).isInStock()  && groupedProductsList.get(i).getCustomersBasketQuantity() > 0) {
                            
                            CartDetails cartDetails = new CartDetails();
                            selectedAttributesList = new LinkedList<>();
    
                            // Set Product's Quantity and selected Attributes Info
                            groupedProductsList.get(i).setAttributesPrice(String.valueOf(0));
    
                            cartDetails.setCartProduct(groupedProductsList.get(i));
//                            cartDetails.setCustomersBasketProductAttributes(selectedAttributesList);
    
    
                            // Add the Product to User's Cart with the help of static method of My_Cart class
                            My_Cart.AddCartItem
                                    (
                                            cartDetails
                                    );
                        }
                    }
    
                    int cartSizeAfter = My_Cart.getCartSize();
                    
                    if (cartSizeAfter > cartSizeBefore) {
                        // Recreate the OptionsMenu
                        ((MainActivity) getContext()).invalidateOptionsMenu();
                        Snackbar.make(view, getContext().getString(R.string.item_added_to_cart), Snackbar.LENGTH_SHORT).show();
                    }
                    
                }
                else if (productDetails.getType().equalsIgnoreCase("simple")) {
                    if (productDetails.isInStock()) {
        
                        updateProductPrices();
        
                        CartDetails cartDetails = new CartDetails();
                        selectedAttributesList = new LinkedList<>();
                        
                        cartDetails.setCartProduct(productDetails);
//                        cartDetails.setCustomersBasketProductAttributes(selectedAttributesList);
        
        
                        // Add the Product to User's Cart with the help of static method of My_Cart class
                        My_Cart.AddCartItem
                                (
                                        cartDetails
                                );
        
        
                        // Recreate the OptionsMenu
                        getActivity().invalidateOptionsMenu();
        
                        Snackbar.make(view, getContext().getString(R.string.item_added_to_cart), Snackbar.LENGTH_SHORT).show();
                    }
    
                }
                else if (productDetails.getType().equalsIgnoreCase("variable")) {
                    if (productDetails.getAttributes().size() > 0  && allProductVariationsList.size() > 0  &&  selectedVariationID != 0) {
                        updateProductPrices();
    
                        CartDetails cartDetails = new CartDetails();
//                        selectedAttributesList = new LinkedList<>();
                        
                        int variationID = selectedVariationID;
                        cartDetails.setCartProduct(productDetails);
                        String str = title.getText().toString();
                        cartDetails.getCartProduct().setName(title.getText().toString());
                        cartDetails.getCartProduct().setAttributes(selectedAttributesList);
                        cartDetails.getCartProduct().setSelectedVariationID(selectedVariationID);
                        
                        
//                        cartDetails.setCustomersBasketProductAttributes(selectedAttributesList);
    
    
                        // Add the Product to User's Cart with the help of static method of My_Cart class
                        My_Cart.AddCartItem
                                (
                                        cartDetails
                                );
    
    
                        // Recreate the OptionsMenu
                        ((MainActivity) getContext()).invalidateOptionsMenu();
    
                        Snackbar.make(view, getContext().getString(R.string.item_added_to_cart), Snackbar.LENGTH_SHORT).show();
                        
                    }
                    else {
                        updateProductPrices();
    
                        CartDetails cartDetails = new CartDetails();
                        selectedAttributesList = new LinkedList<>();
    
                        cartDetails.setCartProduct(productDetails);
                        cartDetails.getCartProduct().setAttributes(selectedAttributesList);
    
    
                        // Add the Product to User's Cart with the help of static method of My_Cart class
                        My_Cart.AddCartItem
                                (
                                        cartDetails
                                );
    
    
                        // Recreate the OptionsMenu
                        ((MainActivity) getContext()).invalidateOptionsMenu();
    
                        Snackbar.make(view, getContext().getString(R.string.item_added_to_cart), Snackbar.LENGTH_SHORT).show();
                        
                    }
                }
                else {
                    updateProductPrices();
    
                    CartDetails cartDetails = new CartDetails();
                    selectedAttributesList = new LinkedList<>();
    
                    cartDetails.setCartProduct(productDetails);
//                        cartDetails.setCustomersBasketProductAttributes(selectedAttributesList);
    
    
                    // Add the Product to User's Cart with the help of static method of My_Cart class
                    My_Cart.AddCartItem
                            (
                                    cartDetails
                            );
    
    
                    // Recreate the OptionsMenu
                    ((MainActivity) getContext()).invalidateOptionsMenu();
    
                    Snackbar.make(view, getContext().getString(R.string.item_added_to_cart), Snackbar.LENGTH_SHORT).show();
                }
                
            }
        });
        
    }
    
    
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
    
    
    
    //*********** Update Product's final Price based on selected Attributes ********//
    
    public void toggleCartButton(boolean isEnabled) {
    
        productCartBtn.setEnabled(isEnabled);
        
        if (isEnabled) {
            productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_accent));
        }
        else {
            productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_gray));
        }
        
    }
    
    
    
    //*********** Update Product's final Price based on selected Attributes ********//
    
    public void updateProductPrices() {
        
        productFinalPrice = productBasePrice * productDetails.getCustomersBasketQuantity();
    
        // Set Final Price and Quantity
        productDetails.setPrice(String.valueOf(productBasePrice));
        productDetails.setTotalPrice(String.valueOf(productFinalPrice));
        productDetails.setProductsFinalPrice(String.valueOf(productFinalPrice));
        
        product_total_price.setText(ConstantValues.CURRENCY_SYMBOL + new DecimalFormat("#0.00").format(productFinalPrice));
        
    }
    
    
    
    
    //*********** Setup the ImageSlider with the given List of Product Images ********//
    
    public void handleAttributeListClick(final View view, final int position) {
        
        final TextView attribute_value = (TextView) view.findViewById(R.id.attribute_value);
        
        final ProductAttributes productsAttribute = attributesList.get(position);
        
        final String selectedAttributeName = productsAttribute.getName();
        
        
        List<String> attributeValues = new ArrayList<>();
        attributeValues.add(getContext().getString(R.string.choose_option));
        attributeValues.addAll(productsAttribute.getOptions());
        
        
        final ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        optionsAdapter.clear();
        optionsAdapter.addAll(attributeValues);
        
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_list, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        
        Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
        TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
        ListView dialog_list = (ListView) dialogView.findViewById(R.id.dialog_list);
        
        dialog_title.setText(selectedAttributeName);
        dialog_list.setAdapter(optionsAdapter);
        
        
        final AlertDialog alertDialog = dialog.create();
        
        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        
        alertDialog.show();
        
        
        
        dialog_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> dialogParent, View v, int pos, long id) {
                
                String selectedItem = optionsAdapter.getItem(pos);
                attribute_value.setText(selectedItem);
                
                
                attributesList = new LinkedList<>();
                attributesList.clear();
                for (int i=0;  i<productAttributesList.size();  i++) {
                    attributesList.add(i, productAttributesList.get(i));
                }
                
                
                if (selectedItem.equalsIgnoreCase(getContext().getString(R.string.choose_option))) {
                    removeVariation();
                    ProductAttributes nullAttr = new ProductAttributes();
                    selectedAttributesList.remove(position);
                    selectedAttributesList.add(position, nullAttr);
                }
                else {
                    productsAttribute.setOption(selectedItem);
                    selectedAttributesList.remove(position);
                    selectedAttributesList.add(position, productsAttribute);
                }
                
                
                int selected_attr_count = 0;
                boolean any_attr_selected = false;
                boolean all_attr_are_selected = true;
                
                for (int i=0;  i<selectedAttributesList.size();  i++) {
                    if (selectedAttributesList.get(i).getName() == null || TextUtils.isEmpty(selectedAttributesList.get(i).getName()))
                        all_attr_are_selected = false;
                    
                    if (selectedAttributesList.get(i).getName() != null && !TextUtils.isEmpty(selectedAttributesList.get(i).getName())) {
                        selected_attr_count += 1;
                        any_attr_selected = true;
                    }
                }
                
                
                if (selectedItem.equalsIgnoreCase(getContext().getString(R.string.choose_option))) {
                    
                    if (!any_attr_selected) {
                        for (int i=0;  i<productAttributesList.size();  i++) {
                            ProductAttributes nullAttr = new ProductAttributes();
                            attributesList.remove(i);
                            attributesList.add(i, productAttributesList.get(i));
                            selectedAttributesList.remove(i);
                            selectedAttributesList.add(i, nullAttr);
                        }
                        
                        attributesAdapter.notifyDataSetChanged();
                        
                    }
                    else {
                        // Match and Set Variation based on selected
                        variationsList = new ArrayList<>();
                        variationsList.clear();
                        variationsList.addAll(allProductVariationsList);
                        
                        for (int x=0;  x<selectedAttributesList.size();  x++) {
                            if (selectedAttributesList.get(x).getName() != null && !TextUtils.isEmpty(selectedAttributesList.get(x).getName())) {
                                for (int i=0;  i<allProductVariationsList.size();  i++) {
                                    for (int j=0;  j<allProductVariationsList.get(i).getAttributes().size();  j++) {
                                        if (selectedAttributesList.get(x).getName().equalsIgnoreCase(allProductVariationsList.get(i).getAttributes().get(j).getName())
                                                && !selectedAttributesList.get(x).getOption().equalsIgnoreCase(allProductVariationsList.get(i).getAttributes().get(j).getOption()))
                                        {
                                            for (int z=0;  z<variationsList.size();  z++) {
                                                if (allProductVariationsList.get(i).getId() == variationsList.get(z).getId()) {
                                                    variationsList.remove(z);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        
                        // Filter Variations based on Attributes
                        List<ProductDetails> v_all_attr = new ArrayList<>();
                        List<ProductDetails> v_no_attr = new ArrayList<>();
                        List<ProductDetails> v_some_attr = new ArrayList<>();
                        
                        
                        for (int x=0;  x<variationsList.size();  x++) {
                            if (variationsList.get(x).getAttributes().size() == 0) {
                                v_no_attr.add(variationsList.get(x));
                            }
                            else if (variationsList.get(x).getAttributes().size() == productAttributesList.size()) {
                                v_all_attr.add(variationsList.get(x));
                            }
                            else {
                                v_some_attr.add(variationsList.get(x));
                            }
                        }
                        
                        
                        
                        if (v_no_attr.size() == 0) {
                            for (int i=0;  i<attributesNames.length;  i++) {
                                
                                ProductAttributes single_selected_attr = null;
                                
                                if (selected_attr_count == 1) {
                                    for (int x=0;  x<selectedAttributesList.size();  x++) {
                                        if (selectedAttributesList.get(x).getName() != null && !TextUtils.isEmpty(selectedAttributesList.get(x).getName())) {
                                            single_selected_attr = selectedAttributesList.get(x);
                                        }
                                    }
                                }
                                
                                
                                if (single_selected_attr == null  ||  !attributesNames[i].equalsIgnoreCase(single_selected_attr.getName())) {
                                    
                                    boolean contains_all_options = false;
                                    
                                    for (int x=0;  x<variationsList.size();  x++) {
                                        if (variationsList.get(x).getAttributes().size() == 0) {
                                            contains_all_options = true;
                                        }
                                        else {
                                            boolean attr_found = false;
                                            
                                            for (int y=0;  y<variationsList.get(x).getAttributes().size();  y++) {
                                                if (attributesNames[i].equalsIgnoreCase(variationsList.get(x).getAttributes().get(y).getName())) {
                                                    attr_found = true;
                                                }
                                            }
                                            
                                            if (!attr_found){
                                                contains_all_options = true;
                                            }
                                        }
                                    }
                                    
                                    
                                    // Limit options
                                    if (!contains_all_options) {
                                        ProductAttributes limitedOptionsAttr = new ProductAttributes();
                                        List<String> attrOptions = new ArrayList<>();
                                        
                                        for (int x=0;  x<variationsList.size();  x++) {
                                            for (int y=0;  y<variationsList.get(x).getAttributes().size();  y++) {
                                                if (attributesNames[i].equalsIgnoreCase(variationsList.get(x).getAttributes().get(y).getName())) {
                                                    if (!attrOptions.contains(variationsList.get(x).getAttributes().get(y).getOption())) {
                                                        attrOptions.add(variationsList.get(x).getAttributes().get(y).getOption());
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        
                                        limitedOptionsAttr.setOptions(attrOptions);
                                        
                                        for (int x=0;  x<attributesList.size();  x++) {
                                            if (attributesList.get(x).getName().equalsIgnoreCase(attributesNames[i])) {
                                                limitedOptionsAttr.setName(attributesList.get(x).getName());
                                                attributesList.remove(x);
                                                attributesList.add(x, limitedOptionsAttr);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                }
                else {
                    if (!all_attr_are_selected) {
                        // Match and Limit Attributes based on selected
                        variationsList = new ArrayList<>();
                        variationsList.clear();
                        variationsList.addAll(allProductVariationsList);
                        
                        for (int x=0;  x<selectedAttributesList.size();  x++) {
                            if (selectedAttributesList.get(x).getName() != null && !TextUtils.isEmpty(selectedAttributesList.get(x).getName())) {
                                for (int i=0;  i<allProductVariationsList.size();  i++) {
                                    for (int j=0;  j<allProductVariationsList.get(i).getAttributes().size();  j++) {
                                        if (selectedAttributesList.get(x).getName().equalsIgnoreCase(allProductVariationsList.get(i).getAttributes().get(j).getName())
                                                && !selectedAttributesList.get(x).getOption().equalsIgnoreCase(allProductVariationsList.get(i).getAttributes().get(j).getOption()))
                                        {
                                            for (int z=0;  z<variationsList.size();  z++) {
                                                if (allProductVariationsList.get(i).getId() == variationsList.get(z).getId()) {
                                                    variationsList.remove(z);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        
                        // Filter Variations based on Attributes
                        List<ProductDetails> v_all_attr = new ArrayList<>();
                        List<ProductDetails> v_no_attr = new ArrayList<>();
                        List<ProductDetails> v_some_attr = new ArrayList<>();
                        
                        
                        for (int x=0;  x<variationsList.size();  x++) {
                            if (variationsList.get(x).getAttributes().size() == 0) {
                                v_no_attr.add(variationsList.get(x));
                            }
                            else if (variationsList.get(x).getAttributes().size() == productAttributesList.size()) {
                                v_all_attr.add(variationsList.get(x));
                            }
                            else {
                                v_some_attr.add(variationsList.get(x));
                            }
                        }
                        
                        if (v_no_attr.size() == 0) {
                            for (int i=0;  i<attributesNames.length;  i++) {
                                
                                ProductAttributes single_selected_attr = null;
                                
                                if (selected_attr_count == 1) {
                                    for (int x=0;  x<selectedAttributesList.size();  x++) {
                                        if (selectedAttributesList.get(x).getName() != null && !TextUtils.isEmpty(selectedAttributesList.get(x).getName())) {
                                            single_selected_attr = selectedAttributesList.get(x);
                                        }
                                    }
                                }
                                
                                
                                if (single_selected_attr == null  ||  !attributesNames[i].equalsIgnoreCase(single_selected_attr.getName())) {
                                    
                                    boolean contains_all_options = false;
                                    
                                    for (int x=0;  x<variationsList.size();  x++) {
                                        if (variationsList.get(x).getAttributes().size() == 0) {
                                            contains_all_options = true;
                                        }
                                        else {
                                            boolean attr_found = false;
                                            
                                            for (int y=0;  y<variationsList.get(x).getAttributes().size();  y++) {
                                                if (attributesNames[i].equalsIgnoreCase(variationsList.get(x).getAttributes().get(y).getName())) {
                                                    attr_found = true;
                                                }
                                            }
                                            
                                            if (!attr_found){
                                                contains_all_options = true;
                                            }
                                        }
                                    }
                                    
                                    
                                    // Limit options
                                    if (!contains_all_options) {
                                        ProductAttributes limitedOptionsAttr = new ProductAttributes();
                                        List<String> attrOptions = new ArrayList<>();
                                        
                                        for (int x=0;  x<variationsList.size();  x++) {
                                            for (int y=0;  y<variationsList.get(x).getAttributes().size();  y++) {
                                                if (attributesNames[i].equalsIgnoreCase(variationsList.get(x).getAttributes().get(y).getName())) {
                                                    if (!attrOptions.contains(variationsList.get(x).getAttributes().get(y).getOption())) {
                                                        attrOptions.add(variationsList.get(x).getAttributes().get(y).getOption());
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        
                                        limitedOptionsAttr.setOptions(attrOptions);
                                        
                                        for (int x=0;  x<attributesList.size();  x++) {
                                            if (attributesList.get(x).getName().equalsIgnoreCase(attributesNames[i])) {
                                                limitedOptionsAttr.setName(attributesList.get(x).getName());
                                                attributesList.remove(x);
                                                attributesList.add(x, limitedOptionsAttr);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                    }
                    else {
                        // Match and Set Variation based on selected
                        variationsList = new ArrayList<>();
                        variationsList.clear();
                        variationsList.addAll(allProductVariationsList);
                        
                        for (int x=0;  x<selectedAttributesList.size();  x++) {
                            if (selectedAttributesList.get(x).getName() != null && !TextUtils.isEmpty(selectedAttributesList.get(x).getName())) {
                                for (int i=0;  i<allProductVariationsList.size();  i++) {
                                    for (int j=0;  j<allProductVariationsList.get(i).getAttributes().size();  j++) {
                                        if (selectedAttributesList.get(x).getName().equalsIgnoreCase(allProductVariationsList.get(i).getAttributes().get(j).getName())
                                                && !selectedAttributesList.get(x).getOption().equalsIgnoreCase(allProductVariationsList.get(i).getAttributes().get(j).getOption()))
                                        {
                                            for (int z=0;  z<variationsList.size();  z++) {
                                                if (allProductVariationsList.get(i).getId() == variationsList.get(z).getId()) {
                                                    variationsList.remove(z);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        
                        // Filter Variations based on Attributes
                        List<ProductDetails> v_all_attr = new ArrayList<>();
                        List<ProductDetails> v_no_attr = new ArrayList<>();
                        List<ProductDetails> v_some_attr = new ArrayList<>();
                        
                        
                        for (int x=0;  x<variationsList.size();  x++) {
                            if (variationsList.get(x).getAttributes().size() == 0) {
                                v_no_attr.add(variationsList.get(x));
                            }
                            else if (variationsList.get(x).getAttributes().size() == productAttributesList.size()) {
                                v_all_attr.add(variationsList.get(x));
                            }
                            else {
                                v_some_attr.add(variationsList.get(x));
                            }
                        }
                        
                        
                        // Limit all options based on selected
                        if (v_no_attr.size() == 0) {
                            for (int i=0;  i<attributesNames.length;  i++) {
                                
                                ProductAttributes single_selected_attr = null;
                                
                                if (selected_attr_count == 1) {
                                    for (int x=0;  x<selectedAttributesList.size();  x++) {
                                        if (selectedAttributesList.get(x).getName() != null && !TextUtils.isEmpty(selectedAttributesList.get(x).getName())) {
                                            single_selected_attr = selectedAttributesList.get(x);
                                        }
                                    }
                                }
                                
                                
                                if (single_selected_attr == null  ||  !attributesNames[i].equalsIgnoreCase(single_selected_attr.getName())) {
                                    
                                    boolean contains_all_options = false;
                                    
                                    for (int x=0;  x<variationsList.size();  x++) {
                                        if (variationsList.get(x).getAttributes().size() == 0) {
                                            contains_all_options = true;
                                        }
                                        else {
                                            boolean attr_found = false;
                                            
                                            for (int y=0;  y<variationsList.get(x).getAttributes().size();  y++) {
                                                if (attributesNames[i].equalsIgnoreCase(variationsList.get(x).getAttributes().get(y).getName())) {
                                                    attr_found = true;
                                                }
                                            }
                                            
                                            if (!attr_found){
                                                contains_all_options = true;
                                            }
                                        }
                                    }
                                    
                                    
                                    // Limit options
                                    if (!contains_all_options) {
                                        ProductAttributes limitedOptionsAttr = new ProductAttributes();
                                        List<String> attrOptions = new ArrayList<>();
                                        
                                        for (int x=0;  x<variationsList.size();  x++) {
                                            for (int y=0;  y<variationsList.get(x).getAttributes().size();  y++) {
                                                if (attributesNames[i].equalsIgnoreCase(variationsList.get(x).getAttributes().get(y).getName())) {
                                                    if (!attrOptions.contains(variationsList.get(x).getAttributes().get(y).getOption())) {
                                                        attrOptions.add(variationsList.get(x).getAttributes().get(y).getOption());
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        
                                        limitedOptionsAttr.setOptions(attrOptions);
                                        
                                        for (int x=0;  x<attributesList.size();  x++) {
                                            if (attributesList.get(x).getName().equalsIgnoreCase(attributesNames[i])) {
                                                limitedOptionsAttr.setName(attributesList.get(x).getName());
                                                attributesList.remove(x);
                                                attributesList.add(x, limitedOptionsAttr);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        
                        // Match and Set variation
                        boolean variationMatched = false;
                        
                        for (int i=0;  i<productDetails.getVariations().size();  i++) {
                            for (int j=0;  j<variationsList.size();  j++) {
                                if (variationsList.get(j).getId() == productDetails.getVariations().get(i)) {
                                    variationMatched = true;
                                    RequestVariationDetail(variationsList.get(j).getId());
                                    break;
                                }
                            }
                            
                            if (variationMatched)
                                break;
                        }
                        
                        
                        // Variation not found
                        if (!variationMatched)
                            Toast.makeText(App.getContext(), "Variation not found!", Toast.LENGTH_SHORT).show();
                        
                    }
                }
                
                alertDialog.dismiss();
                
            }
        });
        
    }
    
    
    //*********** Setup the ImageSlider with the given List of Product Images ********//
    
    public void setVariation(ProductDetails variationProduct)  {
        
        selectedVariationID = variationProduct.getId();
        
        Log.i("variation", "selectedVariationID = "+selectedVariationID);
        
        Toast.makeText(getContext(), "variation="+selectedVariationID, Toast.LENGTH_SHORT).show();
        
        List<ProductImages> imagesList = new ArrayList<>();
        imagesList.addAll(variationProduct.getImages());
        
        // Get Product Images and Attributes
        itemImages = new ArrayList<>();
        itemImages.addAll(imagesList);
        productDetails.setImage(itemImages.get(0).getSrc());
        
        
        // Setup the ImageSlider of Product Images
        ImageSlider(productDetails.getImage(), itemImages);
        
        
        // Set Product's Information
        if (!TextUtils.isEmpty(variationProduct.getName()))
            title.setText(variationProduct.getName());
        
        productDetails.setCustomersBasketQuantity(1);
        product_item_quantity.setText(String.valueOf(productDetails.getCustomersBasketQuantity()));
        
        
        if (!TextUtils.isEmpty(variationProduct.getPrice()))
            productBasePrice = Double.parseDouble(variationProduct.getPrice());
        
        
        if (!TextUtils.isEmpty(variationProduct.getPriceHtml())) {
            String productPrice = variationProduct.getPriceHtml();
            String price_styleSheet =   "<style> " +
                    "body{margin:0; padding:0} " +
                    "p{color:#757575;} " +
                    "img{display:inline; height:auto; max-width:100%;}" +
                    "</style>";
            
            product_price_webView.setVerticalScrollBarEnabled(false);
            product_price_webView.setHorizontalScrollBarEnabled(false);
            product_price_webView.setBackgroundColor(Color.TRANSPARENT);
            product_price_webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
            product_price_webView.loadDataWithBaseURL(null, price_styleSheet+productPrice, "text/html", "utf-8", null);
        }
        
        
        // Check if the Product is Out of Stock
        if (!variationProduct.isInStock()) {
            simple_product.setVisibility(View.GONE);
            product_stock.setText(getString(R.string.outOfStock));
            product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentRed));
            productCartBtn.setText(getString(R.string.outOfStock));
            productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_red));
        }
        else {
            simple_product.setVisibility(View.VISIBLE);
            product_stock.setText(getString(R.string.in_stock));
            product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentBlue));
            productCartBtn.setText(getString(R.string.addToCart));
            productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_accent));
        }
        
        
        toggleCartButton(true);
        
        
        updateProductPrices();
        
    }
    
    
    
    //*********** Setup the ImageSlider with the given List of Product Images ********//
    
    public void removeVariation() {
        selectedVariationID = 0;
    
        // Get Product Images and Attributes
        itemImages = new ArrayList<>();
        itemImages.addAll(productDetails.getImages());
        productDetails.setImage(productDetails.getImages().get(0).getSrc());
    
        // Setup the ImageSlider of Product Images
        ImageSlider(productDetails.getImage(), itemImages);
    
    
        // Set Product's Information
        title.setText(productDetails.getName());
        productDetails.setCustomersBasketQuantity(1);
        product_item_quantity.setText(String.valueOf(productDetails.getCustomersBasketQuantity()));
    
    
        if (!"".equalsIgnoreCase(productDetails.getPrice()))
            productBasePrice = Double.parseDouble(productDetails.getPrice());
        
        
        String productPrice = productDetails.getPriceHtml();
        String price_styleSheet =   "<style> " +
                "body{margin:0; padding:0} " +
                "p{color:#757575;} " +
                "img{display:inline; height:auto; max-width:100%;}" +
                "</style>";
    
        product_price_webView.setVerticalScrollBarEnabled(false);
        product_price_webView.setHorizontalScrollBarEnabled(false);
        product_price_webView.setBackgroundColor(Color.TRANSPARENT);
        product_price_webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        product_price_webView.loadDataWithBaseURL(null, price_styleSheet+productPrice, "text/html", "utf-8", null);
    
    
        // Check if the Product is Out of Stock
        if (!productDetails.isInStock()) {
            simple_product.setVisibility(View.GONE);
            product_stock.setText(getString(R.string.outOfStock));
            product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentRed));
            productCartBtn.setText(getString(R.string.outOfStock));
            productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_red));
        }
        else {
            simple_product.setVisibility(View.VISIBLE);
            product_stock.setText(getString(R.string.in_stock));
            product_stock.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccentBlue));
            productCartBtn.setText(getString(R.string.addToCart));
            productCartBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_accent));
        }
    
    
        toggleCartButton(false);
    
        updateProductPrices();
        
    }
    
    
    
    //*********** Setup the ImageSlider with the given List of Product Images ********//
    
    public void showRatingsAndReviewsOfProduct() {
        
        int rating_1_count = 0;
        int rating_2_count = 0;
        int rating_3_count = 0;
        int rating_4_count = 0;
        int rating_5_count = 0;
        
        final Dialog reviews_ratings_dialog = new Dialog(getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        reviews_ratings_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reviews_ratings_dialog.setCancelable(true);
        reviews_ratings_dialog.setContentView(R.layout.dialog_product_rating_reviews);
        reviews_ratings_dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    
        // Bind Dialog Views
        TextView average_rating = (TextView) reviews_ratings_dialog.findViewById(R.id.average_rating);
        TextView total_rating_count = (TextView) reviews_ratings_dialog.findViewById(R.id.total_rating_count);
        ProgressBar rating_progress_5 = (ProgressBar) reviews_ratings_dialog.findViewById(R.id.rating_progress_5);
        ProgressBar rating_progress_4 = (ProgressBar) reviews_ratings_dialog.findViewById(R.id.rating_progress_4);
        ProgressBar rating_progress_3 = (ProgressBar) reviews_ratings_dialog.findViewById(R.id.rating_progress_3);
        ProgressBar rating_progress_2 = (ProgressBar) reviews_ratings_dialog.findViewById(R.id.rating_progress_2);
        ProgressBar rating_progress_1 = (ProgressBar) reviews_ratings_dialog.findViewById(R.id.rating_progress_1);
        Button rate_product_button = (Button) reviews_ratings_dialog.findViewById(R.id.rate_product);
        ImageButton dialog_back_button = (ImageButton) reviews_ratings_dialog.findViewById(R.id.dialog_button);
        RecyclerView reviews_list_recycler = (RecyclerView) reviews_ratings_dialog.findViewById(R.id.reviews_list_recycler);
    
        reviews_list_recycler.setNestedScrollingEnabled(false);
        ViewCompat.setNestedScrollingEnabled(reviews_list_recycler, false);
    
        average_rating.setText(productDetails.getAverageRating());
        total_rating_count.setText(String.valueOf(productDetails.getRatingCount()));
    
        rate_product_button.setVisibility(productDetails.isReviewsAllowed()? View.VISIBLE : View.GONE);
    
        rating_progress_1.setMax(productDetails.getRatingCount());
        rating_progress_2.setMax(productDetails.getRatingCount());
        rating_progress_3.setMax(productDetails.getRatingCount());
        rating_progress_4.setMax(productDetails.getRatingCount());
        rating_progress_5.setMax(productDetails.getRatingCount());
        
        for (int i=0;  i<productReviews.size();  i++) {
            if (productReviews.get(i).getRating() == 1)
                rating_1_count += 1;
            else if (productReviews.get(i).getRating() == 2)
                rating_2_count += 1;
            else if (productReviews.get(i).getRating() == 3)
                rating_3_count += 1;
            else if (productReviews.get(i).getRating() == 4)
                rating_4_count += 1;
            else if (productReviews.get(i).getRating() == 5)
                rating_5_count += 1;
        }
        
        rating_progress_1.setProgress(rating_1_count);
        rating_progress_2.setProgress(rating_2_count);
        rating_progress_3.setProgress(rating_3_count);
        rating_progress_4.setProgress(rating_4_count);
        rating_progress_5.setProgress(rating_5_count);
        
        // Initialize the ReviewsAdapter for RecyclerView
        ProductReviewsAdapter reviewsAdapter = new ProductReviewsAdapter(getContext(), productReviews);
    
        // Set the Adapter and LayoutManager to the RecyclerView
        reviews_list_recycler.setAdapter(reviewsAdapter);
        reviews_list_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        reviews_list_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    
        reviewsAdapter.notifyDataSetChanged();
        
    
        rate_product_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConstantValues.IS_USER_LOGGED_IN) {
                    showRateProductDialog();
                }
                else {
                    getContext().startActivity(new Intent(getContext(), Login.class));
                    ((MainActivity) getContext()).finish();
                    ((MainActivity) getContext()).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                }
            }
        });
    
    
        dialog_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviews_ratings_dialog.dismiss();
            }
        });
    
    
        reviews_ratings_dialog.show();
    }
    
    
    
    //*********** Setup the ImageSlider with the given List of Product Images ********//
    
    public void showRateProductDialog() {
        
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_rate_product, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
    
        final ColorRatingBar dialog_rating_bar = dialogView.findViewById(R.id.dialog_rating_bar);
        final EditText dialog_author_name = dialogView.findViewById(R.id.dialog_author_name);
        final EditText dialog_author_email = dialogView.findViewById(R.id.dialog_author_email);
        final EditText dialog_author_message = dialogView.findViewById(R.id.dialog_author_message);
        final Button dialog_button = dialogView.findViewById(R.id.dialog_button);
    
        final AlertDialog rateProductDialog = dialog.create();
    
        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateInputs.isValidName(dialog_author_name.getText().toString())) {
                    if (ValidateInputs.isValidEmail(dialog_author_email.getText().toString())) {
                        if (!"".equalsIgnoreCase(dialog_author_message.getText().toString())) {
    
                            rateProductDialog.dismiss();
    
                            getNonceForProductRating
                                    (
                                            String.valueOf(productDetails.getId()),
                                            String.valueOf(dialog_rating_bar.getRating()),
                                            dialog_author_name.getText().toString().trim(),
                                            dialog_author_email.getText().toString().trim(),
                                            dialog_author_message.getText().toString().trim()
                                    );
                
                        } else {
                            dialog_author_message.setError(getContext().getString(R.string.enter_message));
                        }
                    } else {
                        dialog_author_email.setError(getContext().getString(R.string.invalid_email));
                    }
                } else {
                    dialog_author_name.setError(getContext().getString(R.string.enter_name));
                }
            }
        });
    
        rateProductDialog.show();
    }
    
    
    
    //*********** Setup the ImageSlider with the given List of Product Images ********//
    
    private void ImageSlider(String itemThumbnail, List<ProductImages> itemImages) {
    
        sliderLayout.removeAllSliders();
        
        // Initialize new HashMap<ImageName, ImagePath>
        final LinkedHashMap<String, String> slider_covers = new LinkedHashMap<>();
        // Initialize new Array for Image's URL
        final String[] images = new String[itemImages.size()];
        
        
        if (itemImages.size() > 0) {
            for (int i=0;  i< itemImages.size();  i++) {
                // Get Image's URL at given Position from itemImages List
                images[i] = itemImages.get(i).getSrc();
            }
        }
        
        
        // Put Image's Name and URL to the HashMap slider_covers
        if (images.length == 0) {
            if ("".equalsIgnoreCase(itemThumbnail)) {
                slider_covers.put("a", ""+R.drawable.placeholder);
            }
            else {
                slider_covers.put("a", itemThumbnail);
            }
        }
        else {
            for (int i=0;  i<images.length;  i++) {
                slider_covers.put("b"+i, images[i]);
            }
        }
        
        
        
        for(String name : slider_covers.keySet()) {
            
            // Initialize DefaultSliderView
            DefaultSliderView defaultSliderView = new DefaultSliderView(getContext()) {
                @Override
                public View getView() {
                    View v = LayoutInflater.from(getContext()).inflate(com.daimajia.slider.library.R.layout.render_type_default,null);
                    
                    // Get daimajia_slider_image ImageView of DefaultSliderView
                    sliderImageView = (ImageView)v.findViewById(com.daimajia.slider.library.R.id.daimajia_slider_image);
                    
                    // Set ScaleType of ImageView
                    sliderImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    bindEventAndShow(v, sliderImageView);
                    
                    return v;
                }
            };
            
            // Set Attributes(Name, Placeholder, Image, Type etc) to DefaultSliderView
            defaultSliderView
                    .description(name)
                    .empty(R.drawable.placeholder)
                    .image(slider_covers.get(name))
                    .setScaleType(DefaultSliderView.ScaleType.CenterInside)
                    .setOnSliderClickListener(this);
            
            // Add DefaultSliderView to the SliderLayout
            sliderLayout.addSlider(defaultSliderView);
        }
        
        // Set PresetTransformer type of the SliderLayout
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        
        
        // Check if the size of Images in the Slider is less than 2
        if (slider_covers.size() < 2) {
            // Disable PagerTransformer
            sliderLayout.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });
            
            // Hide Slider PagerIndicator
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
            
        }
        else {
            // Set custom PagerIndicator to the SliderLayout
            sliderLayout.setCustomIndicator(pagerIndicator);
            // Make PagerIndicator Visible
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        }
    }
    
    
    
    //*********** Handle the Click Listener on BannerImages of Slider ********//
    
    @Override
    public void onSliderClick(BaseSliderView slider) {
        
        int position = sliderLayout.getCurrentPosition();
        String img_url = itemImages.get(position).getSrc();
    
        final Dialog dialog = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_image_view);
    
        ImageView dialog_image = dialog.findViewById(R.id.dialog_image);
        ImageButton dialog_cancel = dialog.findViewById(R.id.dialog_cancel);
        
        Glide.with(getContext())
                .load(img_url)
                .error(R.drawable.placeholder)
                .into(dialog_image);
        
        dialog_image.setOnTouchListener(new ImageMatrixTouchHandler(dialog.getContext()));
    
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    
        dialog.show();
    }
    
    
    
    //*********** Request Product Details from the Server based on productID ********//
    
    public void RequestProductDetail(final int productID) {
    
        dialogLoader.showProgressDialog();
        
        Call<ProductDetails> call = APIClient.getInstance()
                .getSingleProduct
                        (
                                String.valueOf(productID)
                                
                        );
    
        call.enqueue(new Callback<ProductDetails>() {
            @Override
            public void onResponse(Call<ProductDetails> call, retrofit2.Response<ProductDetails> response) {
    
                dialogLoader.hideProgressDialog();
                
                if (response.isSuccessful()) {
                    
                    productDetails = response.body();
    
                    setProductDetails(productDetails);
                
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
            public void onFailure(Call<ProductDetails> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getActivity().getApplicationContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
    }
    
    
    
    //*********** Request Product Details from the Server based on productID ********//
    
    public void RequestGroupedProduct(final int productID) {
        
        Call<ProductDetails> call = APIClient.getInstance()
                .getSingleProduct
                        (
                                String.valueOf(productID)
                        );
        
        try {
            
            Response<ProductDetails> response = call.execute();
            
            if (response.isSuccessful()) {
                
                String str = new Gson().toJson(response.body());
                groupedProductsList.add(response.body());
                
            }
            else {
                Converter<ResponseBody, ErrorResponse> converter = APIClient.retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                ErrorResponse error;
                try {
                    error = converter.convert(response.errorBody());
                } catch (IOException e) {
                    error = new ErrorResponse();
                }
                
                Toast.makeText(getContext(), "Error Fetching Product : "+error, Toast.LENGTH_LONG).show();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Exception : "+e, Toast.LENGTH_LONG).show();
        }
        
    }
    
    
    
    //*********** Request Product Details from the Server based on productID ********//
    
    public void RequestProductVariations(final int productID) {
        
        Call<List<ProductDetails>> call = APIClient.getInstance()
                .getVariations
                        (
                                productID
                        );
        
        try {
            
            Response<List<ProductDetails>> response = call.execute();
            
            if (response.isSuccessful()) {
                
                allProductVariationsList.addAll(response.body());
                
            }
            else {
                Converter<ResponseBody, ErrorResponse> converter = APIClient.retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);
                ErrorResponse error;
                try {
                    error = converter.convert(response.errorBody());
                } catch (IOException e) {
                    error = new ErrorResponse();
                }

                Toast.makeText(getContext(), "Error Fetching Product : "+error, Toast.LENGTH_LONG).show();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
           Toast.makeText(getContext(), "Exception : "+e, Toast.LENGTH_LONG).show();
        }
        
    }
    
    
    
    //*********** Request Product Details from the Server based on productID ********//
    
    public void RequestVariationDetail(final int variationID) {
        
        dialogLoader.showProgressDialog();
        
        Call<ProductDetails> call = APIClient.getInstance()
                .getSingleProduct
                        (
                                String.valueOf(variationID)
                                
                        );
        
        call.enqueue(new Callback<ProductDetails>() {
            @Override
            public void onResponse(Call<ProductDetails> call, retrofit2.Response<ProductDetails> response) {
                
                dialogLoader.hideProgressDialog();
                
                if (response.isSuccessful()) {
                    
                    //String response1 = String.valueOf(response.body());
                    
                    ProductDetails variationDetails = response.body();
                    
                    setVariation(variationDetails);
                    
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
            public void onFailure(Call<ProductDetails> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getActivity().getApplicationContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
    }
    
    
    
    //*********** Proceed User Registration Request ********//
    
    private void getProductReviews(final String productID) {
        
        dialogLoader.showProgressDialog();
        
        Call<List<ProductReviews>> call = APIClient.getInstance()
                .getProductReviews
                        (
                                productID,
                                ConstantValues.LANGUAGE_CODE
                        );
        
        call.enqueue(new Callback<List<ProductReviews>>() {
            @Override
            public void onResponse(Call<List<ProductReviews>> call, retrofit2.Response<List<ProductReviews>> response) {
                
                dialogLoader.hideProgressDialog();
                
                // Check if the Response is successful
                if (response.isSuccessful()) {
                    
                    productReviews = new ArrayList<>();
                    
                    if (response.body() !=  null)
                        productReviews.addAll(response.body());
                
                }
            }
            
            @Override
            public void onFailure(Call<List<ProductReviews>> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    
    //*********** Proceed User Registration Request ********//
    
    private void getNonceForProductRating(final String productID, final String rate_star, final String a_name, final String a_email, final String a_message) {
        
        dialogLoader.showProgressDialog();
        
        Map<String, String> params = new LinkedHashMap<>();
        params.put("controller", "AndroidAppSettings");
        params.put("method", "android_create_product_review");
        
        
        Call<Nonce> call = APIClient.getInstance()
                .getNonce
                        (
                                params
                        );
        
        call.enqueue(new Callback<Nonce>() {
            @Override
            public void onResponse(Call<Nonce> call, retrofit2.Response<Nonce> response) {
                
                // Check if the Response is successful
                if (response.isSuccessful()) {
                    
                    String nonce = "";
                    
                    if (response.body().getNonce() != null)
                        nonce = response.body().getNonce();
                    
                    if (!TextUtils.isEmpty(nonce)) {
                        
                        CreateProductReview
                                (
                                        nonce,
                                        productID,
                                        rate_star,
                                        a_name,
                                        a_email,
                                        a_message
                                );
                        
                    }
                    else {
                        dialogLoader.hideProgressDialog();
                        Toast.makeText(App.getContext(), "Nonce is Empty", Toast.LENGTH_SHORT).show();
                    }
                    
                }
                else {
                    dialogLoader.hideProgressDialog();
                    Toast.makeText(App.getContext(), "Nonce is Empty", Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<Nonce> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    
    //*********** Request Product Details from the Server based on productID ********//
    
    public void CreateProductReview(String nonce, String productID, String rate_star, String a_name, String a_email, String a_message) {
        
        Call<UserData> call = APIClient.getInstance()
                .addProductReview
                        (
                                "cool",
                                nonce,
                                productID,
                                rate_star,
                                a_name,
                                a_email,
                                a_message
                        );
        
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, retrofit2.Response<UserData> response) {
                
                dialogLoader.hideProgressDialog();
                
                if (response.isSuccessful()) {
                    // Show the Response Message
                    
                    if ("ok".equalsIgnoreCase(response.body().getStatus())) {
                        if (response.body().getMessage() != null)
                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (response.body().getError() != null)
                            Toast.makeText(getContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<UserData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
    }
    
    
    
    //*********** Request the Product's Details from the Server based on Product's ID ********//
    
    public void RequestRelatedProducts(int products_id) {
        
        Call<ProductDetails> call = APIClient.getInstance()
                .getSingleProduct
                        (
                                String.valueOf(products_id)
                               
                        );
        
        call.enqueue(new Callback<ProductDetails>() {
            @Override
            public void onResponse(Call<ProductDetails> call, retrofit2.Response<ProductDetails> response) {
                
                if (response.isSuccessful()) {
                    
                    if (response.body().getStatus() == null  ||  response.body().getStatus().equalsIgnoreCase("publish"))
                        relatedProductsList.add(response.body());
                    
                    relatedProductsAdapter.notifyDataSetChanged();
    
                    if (relatedProductsAdapter.getItemCount() < 1)
                        related_products.setVisibility(View.GONE);
                    
                }
            }
            
            @Override
            public void onFailure(Call<ProductDetails> call, Throwable t) {}
        });
    }
    
    
    
    /*********** LoadMoreTask Used to Load more Products from the Server in the Background Thread using AsyncTask ********/
    
    private class GetLinkedProducts extends AsyncTask<String, Void, String> {
        
        List<Integer> productIDList;
        
        
        private GetLinkedProducts(Context context, List<Integer> productIDList) {
            this.productIDList = productIDList;
        }
        
        
        //*********** Runs on the UI thread before #doInBackground() ********//
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoader.showProgressDialog();
        }
        
        
        //*********** Performs some Processes on Background Thread and Returns a specified Result  ********//
        
        @Override
        protected String doInBackground(String... params) {
    
            if ("grouped".equalsIgnoreCase(productDetails.getType())) {
                for (int i=0;  i<productIDList.size();  i++) {
                    RequestGroupedProduct(productIDList.get(i));
                }
            }
            else if ("variable".equalsIgnoreCase(productDetails.getType())) {
                RequestProductVariations(productIDList.get(0));
            }
            
            
            return "All Done!";
        }
        
        
        //*********** Runs on the UI thread after #doInBackground() ********//
        
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
    
            if ("grouped".equalsIgnoreCase(productDetails.getType())) {
                for (int i=0;  i<groupedProductsList.size();  i++) {
                    groupedProductsList.get(i).setCustomersBasketQuantity(0);
                }
                groupedProductsAdapter.notifyDataSetChanged();
            }
            else if ("variable".equalsIgnoreCase(productDetails.getType())) {
                attributesAdapter.notifyDataSetChanged();
            }
    
            dialogLoader.hideProgressDialog();
        }
    }
    
}

