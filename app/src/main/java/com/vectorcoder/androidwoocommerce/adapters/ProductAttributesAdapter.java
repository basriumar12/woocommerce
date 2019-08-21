package com.vectorcoder.androidwoocommerce.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductAttributes;

import java.util.ArrayList;
import java.util.List;


/**
 * ProductAttributesAdapter is the adapter class of the List holding Attributes in Product_Description
 **/

public class ProductAttributesAdapter extends BaseAdapter {

    private Context context;
    private List<ProductAttributes> productAttributes;
    
    private LayoutInflater layoutInflater;


    public ProductAttributesAdapter(Context context, List<ProductAttributes> productAttributes) {
        this.context = context;
        this.productAttributes = productAttributes;
    
        layoutInflater = LayoutInflater.from(context);
    }
    
    
    //********** Returns the total number of items in the data set represented by this Adapter *********//
    
    @Override
    public int getCount() {
        return productAttributes.size();
    }
    
    
    //********** Returns the item associated with the specified position in the data set *********//
    
    @Override
    public ProductAttributes getItem(int position) {
        return productAttributes.get(position);
    }
    
    
    //********** Returns the item id associated with the specified position in the list *********//
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    
    
    //********** Returns a View that displays the data at the specified position in the data set *********//

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data model based on Position
        ProductAttributes attribute = productAttributes.get(position);
    
    
        final ViewHolder holder;
        
        // Check if an existing View is being Reused, otherwise Inflate the View with custom Layout
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_product_attributes, parent, false);
            
            holder = new ViewHolder();
    
            holder.attribute = (RelativeLayout) convertView.findViewById(R.id.attribute);
            holder.attribute_name = (TextView) convertView.findViewById(R.id.attribute_name);
            holder.attribute_value = (TextView) convertView.findViewById(R.id.attribute_value);
            
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        
        // Populate the data into the Template View
        holder.attribute_name.setText(attribute.getName());
    
        List<String> attributeValues = new ArrayList<>();
    
        attributeValues.add(context.getString(R.string.choose_option));
        attributeValues.addAll(attribute.getOptions());
    
        holder.attribute_value.setText(attributeValues.get(0));

        
        return convertView;
    }
    
    
    
    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/
    
    static class ViewHolder {
        RelativeLayout attribute;
        TextView attribute_name, attribute_value;
    }
    
}

