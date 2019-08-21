package com.vectorcoder.androidwoocommerce.customs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import com.vectorcoder.androidwoocommerce.R;

import com.vectorcoder.androidwoocommerce.adapters.FiltersAdapter;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.models.product_filters_model.FilterDetails;
import com.vectorcoder.androidwoocommerce.models.product_filters_model.PostFilters;
import com.vectorcoder.androidwoocommerce.models.product_filters_model.ProductFilters;

import java.util.ArrayList;
import java.util.List;


/**
 * FilterDialog will be used to implement Price and Attribute Filters on Products in different categories
 **/

public abstract class FilterDialog extends Dialog {
    
    private PostFilters filters;
    private ProductFilters productFilters;
    
    public FiltersAdapter filtersAdapter;
    public List<FilterDetails> filterDetailsList;
    public List<FilterDetails> selectedFilters = new ArrayList<>();
    
    private RecyclerView filters_recycler;
    private LinearLayout filter_dialog_attributes;
    private CrystalRangeSeekbar filter_price_slider;
    private CheckBox sale_checkbox, featured_checkbox;
    private TextView filter_min_price, filter_max_price;
    private RelativeLayout sale_checkbox_layout, featured_checkbox_layout;
    

    
    public FilterDialog(Context context, List<FilterDetails> filterDetailsList, ProductFilters productFilters, PostFilters filters) {
        super(context);
        this.filters = filters;
        this.productFilters = productFilters;
        this.filterDetailsList = filterDetailsList;
        filtersAdapter = new FiltersAdapter(getContext(), FilterDialog.this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make the Window Full Screen
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setContentView(R.layout.filter_dialog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    
        sale_checkbox = (CheckBox) findViewById(R.id.sale_checkbox);
        featured_checkbox = (CheckBox) findViewById(R.id.featured_checkbox);
        filter_min_price = (TextView) findViewById(R.id.filter_min_price);
        filter_max_price = (TextView) findViewById(R.id.filter_max_price);
        filters_recycler = (RecyclerView) findViewById(R.id.filters_recycler);
        filter_dialog_attributes = (LinearLayout) findViewById(R.id.filter_dialog_attributes);
        sale_checkbox_layout = (RelativeLayout) findViewById(R.id.sale_checkbox_layout);
        featured_checkbox_layout = (RelativeLayout) findViewById(R.id.featured_checkbox_layout);
        filter_price_slider = (CrystalRangeSeekbar) findViewById(R.id.filter_price_slider);
        
        
        filters_recycler.setNestedScrollingEnabled(false);
        
    
        if (filterDetailsList.size() > 0) {
            filter_dialog_attributes.setVisibility(View.VISIBLE);
        } else {
            filter_dialog_attributes.setVisibility(View.GONE);
        }
        
    
        // Initialize the FilterProductsAdapter for RecyclerView
        filters_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        filters_recycler.setAdapter(filtersAdapter);
    
        filtersAdapter.notifyDataSetChanged();
        
        
        
        if (productFilters != null) {
            sale_checkbox_layout.setVisibility((productFilters.getOnSale() == null  ||  productFilters.getOnSale().equalsIgnoreCase("true")) ? View.VISIBLE : View.GONE);
            featured_checkbox_layout.setVisibility((productFilters.getFeatured() == null  ||  productFilters.getFeatured().equalsIgnoreCase("true")) ? View.VISIBLE : View.GONE);
            
            filter_min_price.setText(productFilters.getMinPrice() == null ? "0" : productFilters.getMinPrice());
            filter_max_price.setText(productFilters.getMaxPrice() == null ? ConstantValues.FILTER_MAX_PRICE : productFilters.getMaxPrice());
            filter_price_slider.setMinValue(productFilters.getMinPrice() == null ? Float.parseFloat("0") : Float.parseFloat(productFilters.getMinPrice()));
            filter_price_slider.setMaxValue(productFilters.getMaxPrice() == null ? Float.parseFloat(ConstantValues.FILTER_MAX_PRICE) : Float.parseFloat(productFilters.getMaxPrice()));
            
        }
        else {
            sale_checkbox.setChecked(false);
            featured_checkbox.setChecked(false);
            filter_min_price.setText("0");
            filter_max_price.setText(ConstantValues.FILTER_MAX_PRICE);
            filter_price_slider.setMinValue(Float.parseFloat("0"));
            filter_price_slider.setMaxValue(Float.parseFloat(ConstantValues.FILTER_MAX_PRICE));
        }
    
    
        if (filters != null) {
            if (filters.getSelectedAttributes() != null  &&  filters.getSelectedAttributes().size() > 0)
                selectedFilters = filters.getSelectedAttributes();
    
            
            sale_checkbox.setChecked((filters.getOnSale() == null ||  filters.getOnSale().equalsIgnoreCase("false")) ? false : true);
            featured_checkbox.setChecked((filters.getFeatured() == null || filters.getFeatured().equalsIgnoreCase("false")) ? false : true);
        }
    
    
        // Get the Price RangeBar Values
        filter_price_slider.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                // Set the Minimum and Maximum Price Values
                filter_min_price.setText(String.valueOf(minValue));
                filter_max_price.setText(String.valueOf(maxValue));
            }
        });
        
    
        // Handle the Range change listener of filter_price_slider RangeSeekBar
        filter_price_slider.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                // Set the Minimum and Maximum Price Values
                filter_min_price.setText(String.valueOf(minValue));
                filter_max_price.setText(String.valueOf(maxValue));
    
                submitFilters();
            }
        });
        
    
        // Handle the Check change listener of sale_checkbox Checkbox
        sale_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                submitFilters();
            }
        });
    
        // Handle the Check change listener of featured_checkbox Checkbox
        featured_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                submitFilters();
            }
        });
        
    }
    
    
    
    //*********** Apply Selected Filters on the Products of a Category ********//
    
    public void submitFilters() {
        
        if (selectedFilters.size() < 1
                        &&  !sale_checkbox.isChecked()
                        &&  !featured_checkbox.isChecked()
                        &&  filter_min_price.getText().toString().equalsIgnoreCase(productFilters.getMinPrice())
                        &&  filter_max_price.getText().toString().equalsIgnoreCase(productFilters.getMaxPrice()))
        {
            clearFilters();
            dismiss();
            
        }
        else {
            PostFilters appliedFilters = new PostFilters();
    
            appliedFilters.setOnSale(String.valueOf(sale_checkbox.isChecked()));
            appliedFilters.setFeatured(String.valueOf(featured_checkbox.isChecked()));
            appliedFilters.setMinPrice(filter_min_price.getText().toString());
            appliedFilters.setMaxPrice(filter_max_price.getText().toString());
    
            appliedFilters.setSelectedAttributes(selectedFilters);
    
            // Apply Filters
            applyFilters(appliedFilters);
    
            dismiss();
        }
        
    }
    


    //*********** Apply Selected Filters on the Products of a Category ********//

    public abstract void applyFilters(PostFilters filters);



    //*********** Clear All Filters on the Products of a Category ********//

    public abstract void clearFilters();
    
}

