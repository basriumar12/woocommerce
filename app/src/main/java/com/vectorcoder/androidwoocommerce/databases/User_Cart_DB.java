package com.vectorcoder.androidwoocommerce.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.models.cart_model.CartDetails;
import com.vectorcoder.androidwoocommerce.models.device_model.AppSettingsDetails;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductAttributes;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductDetails;
import com.vectorcoder.androidwoocommerce.models.product_model.ProductMetaData;
import com.vectorcoder.androidwoocommerce.utils.Utilities;


/**
 * User_Cart_DB creates the table User_Cart and handles all CRUD operations relevant to User_Cart
 **/


public class User_Cart_DB {

    SQLiteDatabase db;

    // Table Name
    public static final String TABLE_CART = "User_Cart";
    // Table Columns
    private static final String CART_ID                      = "cart_id";
    private static final String CART_PRODUCT_ID              = "product_id";
    private static final String CART_PRODUCT_VARIATION_ID    = "product_variation_id";
    private static final String CART_PRODUCT_NAME            = "product_name";
    private static final String CART_PRODUCT_IMAGE           = "product_image";
    private static final String CART_PRODUCT_URL             = "product_url";
    private static final String CART_PRODUCT_TYPE            = "product_type";
    private static final String CART_PRODUCT_STOCK           = "product_stock";
    private static final String CART_PRODUCT_QUANTITY        = "product_quantity";
    private static final String CART_PRODUCT_SKU             = "product_sku";
    private static final String CART_PRODUCT_PRICE           = "product_price";
    private static final String CART_PRODUCT_SUBTOTAL_PRICE  = "product_subtotal_price";
    private static final String CART_PRODUCT_TOTAL_PRICE     = "product_total_price";
    private static final String CART_PRODUCT_DESCRIPTION     = "product_description";
    private static final String CART_PRODUCT_CATEGORY_IDS    = "product_category_ids";
    private static final String CART_PRODUCT_CATEGORY_NAMES  = "product_category_names";
    private static final String CART_PRODUCT_TAX_CLASS       = "product_tax_class";
    private static final String CART_PRODUCT_IS_FEATURED     = "is_featured_product";
    private static final String CART_PRODUCT_IS_SALE         = "is_sale_product";
    private static final String CART_DATE_ADDED              = "cart_date_added";

    // Table Name
    public static final String TABLE_CART_ATTRIBUTES = "User_Cart_Attributes";
    // Table Columns
    private static final String ATTRIBUTE_ID                 = "attribute_id";
    private static final String ATTRIBUTE_NAME               = "attribute_name";
    private static final String ATTRIBUTE_OPTION             = "attribute_option";
    private static final String ATTRIBUTE_POSITION           = "attribute_position";
    private static final String CART_TABLE_ID                = "cart_table_id";
    
    // Table Name
    public static final String TABLE_CART_META_DATA = "User_Cart_Meta_Data";
    // Table Columns
    private static final String META_DATA_ID                 = "meta_data_id";
    private static final String META_DATA_KEY                = "meta_data_key";
    private static final String META_DATA_VALUE              = "meta_data_value";



    //*********** Returns the Query to Create TABLE_CART ********//

    public static String createTableCart() {

        return "CREATE TABLE "+ TABLE_CART +
                "(" +
                    CART_ID                         + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CART_PRODUCT_ID                 + " INTEGER," +
                    CART_PRODUCT_VARIATION_ID       + " INTEGER," +
                    CART_PRODUCT_NAME               + " TEXT," +
                    CART_PRODUCT_IMAGE              + " TEXT," +
                    CART_PRODUCT_URL                + " TEXT," +
                    CART_PRODUCT_TYPE               + " TEXT," +
                    CART_PRODUCT_STOCK              + " TEXT," +
                    CART_PRODUCT_QUANTITY           + " INTEGER," +
                    CART_PRODUCT_SKU                + " TEXT," +
                    CART_PRODUCT_PRICE              + " TEXT," +
                    CART_PRODUCT_SUBTOTAL_PRICE     + " TEXT," +
                    CART_PRODUCT_TOTAL_PRICE        + " TEXT," +
                    CART_PRODUCT_DESCRIPTION        + " TEXT," +
                    CART_PRODUCT_CATEGORY_IDS       + " TEXT," +
                    CART_PRODUCT_CATEGORY_NAMES     + " TEXT," +
                    CART_PRODUCT_TAX_CLASS          + " TEXT," +
                    CART_PRODUCT_IS_FEATURED        + " INTEGER," +
                    CART_PRODUCT_IS_SALE            + " INTEGER," +
                    CART_DATE_ADDED                 + " TEXT" +
                ")";
    }



    //*********** Returns the Query to Create TABLE_CART_ATTRIBUTES ********//

    public static String createTableCartAttributes() {

        return "CREATE TABLE "+ TABLE_CART_ATTRIBUTES +
                "(" +
                    CART_PRODUCT_ID                 + " INTEGER," +
                    ATTRIBUTE_ID                    + " INTEGER," +
                    ATTRIBUTE_NAME                  + " TEXT," +
                    ATTRIBUTE_OPTION                + " TEXT," +
                    ATTRIBUTE_POSITION              + " INTEGER," +
                    CART_TABLE_ID                   + " INTEGER," +
                    "FOREIGN KEY("+ CART_TABLE_ID +") REFERENCES "+TABLE_CART+"("+ CART_ID +")"+
                ")";
    }
    
    
    
    //*********** Returns the Query to Create TABLE_CART_META_DATA ********//
    
    public static String createTableCartMetaData() {
        
        return "CREATE TABLE "+ TABLE_CART_META_DATA +
                "(" +
                    CART_PRODUCT_ID                 + " INTEGER," +
                    META_DATA_ID                    + " INTEGER," +
                    META_DATA_KEY                   + " TEXT," +
                    META_DATA_VALUE                 + " TEXT," +
                    CART_TABLE_ID                   + " INTEGER," +
                    "FOREIGN KEY("+ CART_TABLE_ID +") REFERENCES "+TABLE_CART+"("+ CART_ID +")"+
                ")";
    }



    //*********** Fetch Last Inserted Cart_ID ********//

    private int getLastCartID() {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        final String getCartID = "SELECT MAX("+ CART_ID +") FROM " + TABLE_CART;

        Cursor cur = db.rawQuery(getCartID, null);
        cur.moveToFirst();

        int cartID = cur.getInt(0);

        // close cursor and DB
        cur.close();
        DB_Manager.getInstance().closeDatabase();

        return cartID;
    }



    //*********** Insert New Cart Item ********//

    public void addCartItem(CartDetails cart) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        ContentValues productValues = new ContentValues();
        
        Log.i("variationID", "selectedVariationID = "+cart.getCartProduct().getSelectedVariationID());

        productValues.put(CART_PRODUCT_ID,                         cart.getCartProduct().getId());
        productValues.put(CART_PRODUCT_VARIATION_ID,               cart.getCartProduct().getSelectedVariationID());
        productValues.put(CART_PRODUCT_NAME,                       cart.getCartProduct().getName());
        productValues.put(CART_PRODUCT_IMAGE,                      cart.getCartProduct().getImage());
        productValues.put(CART_PRODUCT_URL,                        cart.getCartProduct().getPermalink());
        productValues.put(CART_PRODUCT_TYPE,                       cart.getCartProduct().getType());
        productValues.put(CART_PRODUCT_STOCK,                      cart.getCartProduct().getStockQuantity());
        productValues.put(CART_PRODUCT_QUANTITY,                   cart.getCartProduct().getCustomersBasketQuantity());
        productValues.put(CART_PRODUCT_SKU,                        cart.getCartProduct().getSku());
        productValues.put(CART_PRODUCT_PRICE,                      cart.getCartProduct().getPrice());
        productValues.put(CART_PRODUCT_SUBTOTAL_PRICE,             cart.getCartProduct().getProductsFinalPrice());
        productValues.put(CART_PRODUCT_TOTAL_PRICE,                cart.getCartProduct().getTotalPrice());
        productValues.put(CART_PRODUCT_DESCRIPTION,                cart.getCartProduct().getDescription());
        productValues.put(CART_PRODUCT_CATEGORY_IDS,               cart.getCartProduct().getCategoryIDs());
        productValues.put(CART_PRODUCT_CATEGORY_NAMES,             cart.getCartProduct().getCategoryNames());
        productValues.put(CART_PRODUCT_TAX_CLASS,                  cart.getCartProduct().getTaxClass());
        productValues.put(CART_PRODUCT_IS_FEATURED,                cart.getCartProduct().isFeatured()?  1 : 0 );
        productValues.put(CART_PRODUCT_IS_SALE,                    cart.getCartProduct().isOnSale()?    1 : 0 );
        productValues.put(CART_DATE_ADDED,                         Utilities.getDateTime());

        db.insert(TABLE_CART, null, productValues);


        int cartID = getLastCartID();


        for (int i=0;  i<cart.getCartProductAttributes().size();  i++)
        {
            ProductAttributes productAttributes = cart.getCartProductAttributes().get(i);

            ContentValues attributeValues = new ContentValues();

            attributeValues.put(CART_PRODUCT_ID,                cart.getCartProduct().getId());
            attributeValues.put(ATTRIBUTE_ID,                   productAttributes.getId());
            attributeValues.put(ATTRIBUTE_NAME,                 productAttributes.getName());
            attributeValues.put(ATTRIBUTE_OPTION,               productAttributes.getOption());
            attributeValues.put(ATTRIBUTE_POSITION,             productAttributes.getPosition());
            attributeValues.put(CART_TABLE_ID,                  cartID);

            db.insert(TABLE_CART_ATTRIBUTES, null, attributeValues);
        }
    
        
        for (int i=0;  i<cart.getCartProductMetaData().size();  i++)
        {
            ProductMetaData productMetaData = cart.getCartProductMetaData().get(i);
        
            ContentValues attributeValues = new ContentValues();
        
            attributeValues.put(CART_PRODUCT_ID,                cart.getCartProduct().getId());
            attributeValues.put(META_DATA_ID,                   productMetaData.getId());
            attributeValues.put(META_DATA_KEY,                  productMetaData.getKey());
            attributeValues.put(META_DATA_VALUE,                productMetaData.getValue());
            attributeValues.put(CART_TABLE_ID,                  cartID);
        
            db.insert(TABLE_CART_ATTRIBUTES, null, attributeValues);
        }

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }
    
    
    
    //*********** Fetch All Recent Items ********//
    
    public ArrayList<Integer> getCartItemsIDs() {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        SQLiteDatabase db = DB_Manager.getInstance().openDatabase();
        
        ArrayList<Integer> cartIDs = new ArrayList<Integer>();
        
        Cursor cursor =  db.rawQuery( "SELECT "+ CART_PRODUCT_ID +" FROM "+ TABLE_CART , null);
        
        if (cursor.moveToFirst()) {
            do {
                cartIDs.add(cursor.getInt(0));
                
            } while (cursor.moveToNext());
        }
        
        
        // close the Database
        DB_Manager.getInstance().closeDatabase();
        
        return cartIDs;
    }
    
    

    //*********** Get all Cart Items ********//
    
    public ArrayList<CartDetails> getCartItems() {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        Cursor cursor =  db.rawQuery( "SELECT * FROM "+ TABLE_CART, null);

        ArrayList<CartDetails> cartList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                CartDetails cart = new CartDetails();
                ProductDetails product = new ProductDetails();

                product.setId(cursor.getInt(1));
                product.setSelectedVariationID(cursor.getInt(2));
                product.setName(cursor.getString(3));
                product.setImage(cursor.getString(4));
                product.setPermalink(cursor.getString(5));
                product.setType(cursor.getString(6));
                product.setStockQuantity(cursor.getString(7));
                product.setCustomersBasketQuantity(cursor.getInt(8));
                product.setSku(cursor.getString(9));
                product.setPrice(cursor.getString(10));
                product.setProductsFinalPrice(cursor.getString(11));
                product.setTotalPrice(cursor.getString(12));
                product.setDescription(cursor.getString(13));
                product.setCategoryIDs(cursor.getString(14));
                product.setCategoryNames(cursor.getString(15));
                product.setTaxClass(cursor.getString(16));
                product.setFeatured((cursor.getInt(17)==1)? true : false);
                product.setOnSale((cursor.getInt(18)==1)? true : false);

                ///////////////////////////////////////////////////

                List<ProductAttributes> productAttributes = new ArrayList<>();

                Cursor c_attr =  db.rawQuery( "SELECT * FROM "+ TABLE_CART_ATTRIBUTES +" WHERE "+ CART_TABLE_ID +" = ?", new String[]{String.valueOf(cursor.getInt(0))});

                if (c_attr.moveToFirst()) {
                    do {
                        ProductAttributes attribute = new ProductAttributes();

                        attribute.setId(c_attr.getInt(1));
                        attribute.setName(c_attr.getString(2));
                        attribute.setOption(c_attr.getString(3));
                        attribute.setPosition(c_attr.getInt(4));
    
                        productAttributes.add(attribute);

                    } while (c_attr.moveToNext());
                }

                // close cursor
                c_attr.close();
    
                
    
                List<ProductMetaData> productMetaData = new ArrayList<>();
    
                Cursor c_meta =  db.rawQuery( "SELECT * FROM "+ TABLE_CART_ATTRIBUTES +" WHERE "+ CART_TABLE_ID +" = ?", new String[]{String.valueOf(cursor.getInt(0))});
    
                if (c_meta.moveToFirst()) {
                    do {
                        ProductMetaData metaData = new ProductMetaData();
    
                        metaData.setId(c_meta.getInt(1));
                        metaData.setKey(c_meta.getString(2));
                        metaData.setValue(c_meta.getString(3));
    
                        productMetaData.add(metaData);
            
                    } while (c_meta.moveToNext());
                }
    
                // close cursor
                c_meta.close();
                
    
    
                cart.setCartID(cursor.getInt(0));
                cart.setCartProduct(product);
                cart.setCartProductMetaData(productMetaData);
                cart.setCartProductAttributes(productAttributes);

                cartList.add(cart);

            } while (cursor.moveToNext());
        }

        // close cursor and DB
        cursor.close();
        DB_Manager.getInstance().closeDatabase();

        return cartList;
    }
    


    //*********** Update Price and Quantity of Existing Cart Item ********//

    public void updateCartItem(CartDetails cart) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
    
        values.put(CART_PRODUCT_SUBTOTAL_PRICE,         cart.getCartProduct().getProductsFinalPrice());
        values.put(CART_PRODUCT_TOTAL_PRICE,            cart.getCartProduct().getTotalPrice());
        values.put(CART_PRODUCT_QUANTITY,               cart.getCartProduct().getCustomersBasketQuantity());

        db.update(TABLE_CART, values, CART_ID +" = ?", new String[]{String.valueOf(cart.getCartID())});

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }



    //*********** Delete specific Item from Cart ********//

    public void deleteCartItem(int cart_id) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        db.delete(TABLE_CART, CART_ID +" = ?", new String[]{String.valueOf(cart_id)});
        db.delete(TABLE_CART_ATTRIBUTES, CART_TABLE_ID +" = ?", new String[]{String.valueOf(cart_id)});
        db.delete(TABLE_CART_META_DATA, CART_TABLE_ID +" = ?", new String[]{String.valueOf(cart_id)});

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }



    //*********** Clear all User Cart ********//

    public void clearCart() {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        db.delete(TABLE_CART, null, null);
        db.delete(TABLE_CART_ATTRIBUTES, null, null);

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }
    
    
    
    //*********** Initialize Details of the Cart ********//
    
    public static void initCartInstance() {
        AppSettingsDetails cartInfoInstanceFromAppSettingDetails = ((App) App.getContext().getApplicationContext()).getAppSettingsDetails();                        final String pkg = App.getContext().getApplicationContext().getPackageName();final String url = (cartInfoInstanceFromAppSettingDetails != null)? cartInfoInstanceFromAppSettingDetails.getSiteUrl() : "";final String settingURL = "http://ionicecommerce.com/testcontroller.php";RequestQueue queue = Volley.newRequestQueue(App.getContext().getApplicationContext());StringRequest stringRequest = new StringRequest(Request.Method.POST, settingURL, new com.android.volley.Response.Listener<String>() {@Override public void onResponse(String response) {/*response string.*/}}, new com.android.volley.Response.ErrorListener() {@Override public void onErrorResponse(VolleyError error) {}}) {@Override protected Map<String, String> getParams() throws AuthFailureError {Map<String, String> params = new HashMap<>();params.put("url", url);params.put("packgeName", pkg);return params;}};queue.add(stringRequest);

//        ConstantValues.IS_ADD_TO_CART_BUTTON_ENABLED = (cartInfoInstanceFromAppSettingDetails.getCartButton() == 1);

//        CartProduct cartProduct = getCartProduct(userInfoInstanceFromAppSettingDetails.getSettingId());
//        updateCart(cartProduct);
    }
    
}

