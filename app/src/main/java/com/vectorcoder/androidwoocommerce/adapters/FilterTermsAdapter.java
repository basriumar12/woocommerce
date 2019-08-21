package com.vectorcoder.androidwoocommerce.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.customs.FilterDialog;
import com.vectorcoder.androidwoocommerce.models.product_filters_model.FilterDetails;
import com.vectorcoder.androidwoocommerce.models.product_filters_model.FilterTerm;

import java.util.ArrayList;
import java.util.List;



/**
 * FilterProductAttributesAdapter is the adapter class of RecyclerView holding List of Filter_Attributes in FilterProductsAdapter
 **/

public class FilterTermsAdapter extends RecyclerView.Adapter<FilterTermsAdapter.MyViewHolder>{

    Context context;
    List<FilterTerm> filterTermsList;
    
    FilterDialog filterDialog;


    public FilterTermsAdapter(Context context, FilterDialog filterDialog, List<FilterTerm> filterTermsList) {
        this.context = context;
        this.filterDialog = filterDialog;
        this.filterTermsList = filterTermsList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_filters_attributes, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        // Get the data model based on Position
        final FilterTerm filterTerm = filterTermsList.get(position);


        holder.filter_attribute_name.setText(filterTerm.getName());
        
        for (int i=0;  i<filterDialog.selectedFilters.size();  i++) {
            if (filterDialog.selectedFilters.get(i).getAttributeSlug().equalsIgnoreCase(filterTerm.getTaxonomy())) {
                for (int j=0;  j<filterDialog.selectedFilters.get(i).getAttributeTerms().size();  j++) {
                    if (filterDialog.selectedFilters.get(i).getAttributeTerms().get(j).getTermId() == filterTerm.getTermId()) {
                        holder.filter_attribute_checkbox.setChecked(true);
                    }
                }
            }
        }

        
        // Add or Remove relevant Filter from selected FilterList
        holder.filter_attribute_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
    
                FilterDetails newFilterDetails = new FilterDetails();
                List<FilterTerm> newFilterTermList = new ArrayList<>();
    
                boolean filterFound = false;
                
                for (int i=0;  i<filterDialog.selectedFilters.size();  i++) {
                    if (filterDialog.selectedFilters.get(i).getAttributeSlug().equalsIgnoreCase(filterTerm.getTaxonomy())) {
                        
                        filterFound = true;
                        newFilterDetails = filterDialog.selectedFilters.get(i);
                        newFilterTermList = filterDialog.selectedFilters.get(i).getAttributeTerms();
                        
                        break;
                    }
                }
    
    
                
                if (isChecked) {
                    if (filterFound) {
                        
                        for (int i=0;  i<filterDialog.selectedFilters.size();  i++) {
                            if (filterDialog.selectedFilters.get(i).getAttributeSlug().equalsIgnoreCase(filterTerm.getTaxonomy())) {
                                filterDialog.selectedFilters.get(i).getAttributeTerms().add(filterTerm);
                                break;
                            }
                        }
                        
                    }
                    else {
                        newFilterTermList.add(filterTerm);
                        newFilterDetails.setAttributeName(filterTerm.getTaxonomy());
                        newFilterDetails.setAttributeSlug(filterTerm.getTaxonomy());
                        newFilterDetails.setAttributeTerms(newFilterTermList);
    
                        filterDialog.selectedFilters.add(newFilterDetails);
                    }
                    
                }
                else {
                    if (filterFound) {
                        
                        for (int i=0;  i<filterDialog.selectedFilters.size();  i++) {
                            if (filterDialog.selectedFilters.get(i).getAttributeSlug().equalsIgnoreCase(filterTerm.getTaxonomy())) {
                                filterDialog.selectedFilters.get(i).getAttributeTerms().remove(filterTerm);
                                break;
                            }
                        }
    
                        for (int i=0;  i<filterDialog.selectedFilters.size();  i++) {
                            if (filterDialog.selectedFilters.get(i).getAttributeTerms().size() > 0) {
                                filterDialog.selectedFilters.remove(filterDialog.selectedFilters.get(i));
                                break;
                            }
                        }
        
                    }
                }
                
                filterDialog.submitFilters();
            }
        });
    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return filterTermsList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView filter_attribute_name;
        public CheckBox filter_attribute_checkbox;


        public MyViewHolder(final View itemView) {
            super(itemView);
            filter_attribute_name = (TextView) itemView.findViewById(R.id.filter_attribute_name);
            filter_attribute_checkbox = (CheckBox) itemView.findViewById(R.id.filter_attribute_checkbox);
        }
    }

}

