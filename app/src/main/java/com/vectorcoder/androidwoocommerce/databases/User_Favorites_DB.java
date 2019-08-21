package com.vectorcoder.androidwoocommerce.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


/**
 * User_Favorites_DB creates the table User_Favorites and handles all CRUD operations relevant to User_Favorites
 **/

public class User_Favorites_DB {

    // Table Name
    public static final String TABLE_FAVORITES = "User_Favorites";
    // Table Columns
    public static final String PRODUCT_ID = "products_id";



    //*********** Returns the Query to Create TABLE_FAVORITES ********//

    public static String createTable() {

        return "CREATE TABLE "+ TABLE_FAVORITES
                + "("
                + PRODUCT_ID    +" INTEGER"
                + ")";
    }



    //*********** Insert New Favorite Item ********//

    public void insertFavoriteItem(int products_id) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        SQLiteDatabase db = DB_Manager.getInstance().openDatabase();

        ContentValues values = new ContentValues();

        values.put(PRODUCT_ID,      products_id);

        db.insert(TABLE_FAVORITES, null, values);

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }



    //*********** Fetch All Favorite Items ********//

    public ArrayList<Integer> getUserFavorites(){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        SQLiteDatabase db = DB_Manager.getInstance().openDatabase();

        ArrayList<Integer> recents = new ArrayList<Integer>();

        Cursor cursor =  db.rawQuery( "SELECT "+ PRODUCT_ID +" FROM "+ TABLE_FAVORITES , null);

        if (cursor.moveToFirst()) {
            do {
                recents.add(cursor.getInt(0));

            } while (cursor.moveToNext());
        }


        // close the Database
        DB_Manager.getInstance().closeDatabase();

        return recents;
    }



    //*********** Update existing Favorite Item ********//

    public void updateFavoriteItem(int products_id) {
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        SQLiteDatabase db = DB_Manager.getInstance().openDatabase();

        ContentValues values = new ContentValues();

        values.put(PRODUCT_ID,       products_id);

        db.update(TABLE_FAVORITES, values, PRODUCT_ID +" = ?", new String[]{String.valueOf(products_id)});

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }



    //*********** Delete specific Favorite Item ********//

    public void deleteFavoriteItem(int products_id){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        SQLiteDatabase db = DB_Manager.getInstance().openDatabase();

        db.delete(TABLE_FAVORITES, PRODUCT_ID +" = ?", new String[]{String.valueOf(products_id)});

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }



    //*********** Clear the table TABLE_FAVORITES ********//

    public void clearUserFavorites(){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        SQLiteDatabase db = DB_Manager.getInstance().openDatabase();

        db.delete(TABLE_FAVORITES, null, null);

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }

}
