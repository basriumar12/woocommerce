package com.vectorcoder.androidwoocommerce.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.R;

import java.util.ArrayList;
import java.util.List;

import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.adapters.ViewPagerCustomAdapter;
import com.vectorcoder.androidwoocommerce.models.category_model.CategoryDetails;


public class Products extends Fragment {
    
    boolean isMenuItem = false;
    boolean isSubFragment = false;
    boolean isSaleApplied = false;
    boolean isFeaturedApplied = false;

    int selectedTabIndex = 0;
    String selectedTabText = "";

    TabLayout product_tabs;
    ViewPager product_viewpager;

    ViewPagerCustomAdapter viewPagerCustomAdapter;

    List<CategoryDetails> allCategoriesList = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get CategoriesList from AppContext
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.products, container, false);

        if (getArguments() != null) {
            if (getArguments().containsKey("isMenuItem")) {
                isMenuItem = getArguments().getBoolean("isMenuItem", false);
            }

            if (getArguments().containsKey("isSubFragment")) {
                isSubFragment = getArguments().getBoolean("isSubFragment", false);
            }
    
            if (getArguments().containsKey("on_sale")) {
                isSaleApplied = getArguments().getBoolean("on_sale", false);
            }
    
            if (getArguments().containsKey("featured")) {
                isFeaturedApplied = getArguments().getBoolean("featured", false);
            }

            if (getArguments().containsKey("CategoryName")) {
                selectedTabText = getArguments().getString("CategoryName", "Category");
            }
        }


        // Toggle Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        if (!isSubFragment) {
            if (isMenuItem) {
                MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            } else {
                MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            }
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.actionShop));
        }


        // Binding Layout Views
        product_tabs = (TabLayout) rootView.findViewById(R.id.product_tabs);
        product_viewpager = (ViewPager) rootView.findViewById(R.id.product_viewpager);


        // Setup ViewPagerAdapter
        setupViewPagerAdapter();


        product_viewpager.setOffscreenPageLimit(0);
        product_viewpager.setAdapter(viewPagerCustomAdapter);

        // Add corresponding ViewPagers to TabLayouts
        product_tabs.setupWithViewPager(product_viewpager);

        
        try {
            product_tabs.getTabAt(selectedTabIndex).select();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        
        return rootView;

    }



    //*********** Setup the ViewPagerAdapter ********//

    private void setupViewPagerAdapter() {

        // Initialize ViewPagerAdapter with ChildFragmentManager for ViewPager
        viewPagerCustomAdapter = new ViewPagerCustomAdapter(getChildFragmentManager());

        // Initialize All_Products Fragment with specified arguments
        Fragment allProducts = new All_Products();
        Bundle bundleInfo = new Bundle();
        bundleInfo.putBoolean("on_sale", isSaleApplied);
        bundleInfo.putBoolean("featured", isFeaturedApplied);
        allProducts.setArguments(bundleInfo);

        // Add the Fragments to the ViewPagerAdapter with TabHeader
        viewPagerCustomAdapter.addFragment(allProducts, getContext().getString(R.string.all));


        for (int i=0;  i < allCategoriesList.size();  i++) {

            // Initialize Category_Products Fragment with specified arguments
            Fragment categoryProducts = new All_Category_Products();
            Bundle categoryInfo = new Bundle();
            categoryInfo.putBoolean("on_sale", isSaleApplied);
            categoryInfo.putBoolean("featured", isFeaturedApplied);
            categoryInfo.putInt("CategoryID", allCategoriesList.get(i).getId());
            categoryProducts.setArguments(categoryInfo);

            // Add the Fragments to the ViewPagerAdapter with TabHeader
            viewPagerCustomAdapter.addFragment(categoryProducts, allCategoriesList.get(i).getName());
    
    
            if (getArguments().containsKey("CategoryName")  && getArguments().containsKey("CategoryID")) {
                if (selectedTabText.equalsIgnoreCase(allCategoriesList.get(i).getName())
                        && getArguments().getInt("CategoryID", 0) == allCategoriesList.get(i).getId()) {
                    selectedTabIndex = i + 1;
                }
            }
        }

    }

}

