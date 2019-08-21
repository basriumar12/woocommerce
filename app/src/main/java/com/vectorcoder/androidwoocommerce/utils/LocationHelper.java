package com.vectorcoder.androidwoocommerce.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.models.location_model.Countries;
import com.vectorcoder.androidwoocommerce.models.location_model.Zones;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muneeb.vectorcoder@gmail.com on 22/03/2018.
 */


public class LocationHelper {
    
    private Context context;
    private JSONObject jsonObject;
    
    
    public LocationHelper(Context context) {
        this.context = context;
        
        String jsonString = readStringFromJsonFile();               // Get Json String from Json File
        jsonObject = getJsonFromString(jsonString);                 // Convert Json String to Object
    }
    
    
    
    //********** Get Countries from JsonObject *******//
    
    public List<Countries> getCountries() {
        List<Countries> countriesList = new ArrayList<>();
    
        try {
            JSONArray countriesArray = jsonObject.getJSONArray("countries");
    
            countriesList = new Gson().fromJson(countriesArray.toString(), new TypeToken<List<Countries>>(){}.getType());
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
        return countriesList;
    }
    
    
    //********** Get Country from Country Code from JsonObject *******//
    
    public Countries getCountryFromCode(String country_code) {
        Countries country = new Countries();
        List<Countries> countriesList = new ArrayList<>();
        
        try {
            JSONArray countriesArray = jsonObject.getJSONArray("countries");
            
            countriesList = new Gson().fromJson(countriesArray.toString(), new TypeToken<List<Countries>>(){}.getType());
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        
        for (int i=0;  i<countriesList.size();  i++) {
            if (country_code.equalsIgnoreCase(countriesList.get(i).getValue())) {
                country = countriesList.get(i);
                break;
            }
        }
        
        return country;
    }
    
    
    //********** Get Zone from Country Code and Zone Code from JsonObject *******//
    
    public Zones getStateFromCode(String country_code, String state_code) {
        Zones zone = new Zones();
        List<Zones> zonesList = new ArrayList<>();
    
        try {
            JSONObject statesList = jsonObject.getJSONObject("states");
            JSONArray zonesArray = statesList.getJSONArray(country_code);
        
            zonesList = new Gson().fromJson(zonesArray.toString(), new TypeToken<List<Zones>>(){}.getType());
        
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        
        for (int i=0;  i<zonesList.size();  i++) {
            if (state_code.equalsIgnoreCase(zonesList.get(i).getValue())) {
                zone = zonesList.get(i);
                break;
            }
        }
        
        return zone;
    }
    
    
    //********** Get States of given Country from JsonObject *******//
    
    public List<Zones> getStates(String country_code) {
        List<Zones> zonesList = new ArrayList<>();
    
        try {
            JSONObject statesList = jsonObject.getJSONObject("states");
            JSONArray zonesArray = statesList.getJSONArray(country_code);
    
            zonesList = new Gson().fromJson(zonesArray.toString(), new TypeToken<List<Zones>>(){}.getType());
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
    
        return zonesList;
    }
    
    
    
    //********** Get Continent Code of given Country Code from JsonObject *******//
    
    public String getContinentCode(String country_code) {
        String continentCode = "";
        
        try {
            JSONObject country_continent = jsonObject.getJSONObject("country_continent");
            continentCode = country_continent.getString(country_code);
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return continentCode;
    }
    
    
    
    //********** Get Continent of given Country Code from JsonObject *******//
    
    public String getContinent(String continent_code) {
        String continent = "";
        
        try {
            JSONObject continents = jsonObject.getJSONObject("continents");
            continent = continents.getString(continent_code);
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return continent;
    }
    
    
    
    //********** Converts JsonString to JsonObject *******//
    
    private JSONObject getJsonFromString(String jsonString) {
    
        JSONObject jsonObject = new JSONObject();
    
        try {
        
            jsonObject = new JSONObject(jsonString);
        
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        
        
        return jsonObject;
    
    }
    
    
    
    //********** Read and Convert Json to JsonString from JsonFile *******//
    
    private String readStringFromJsonFile() {
        String jsonString = "";
    
        InputStream is = context.getResources().openRawResource(R.raw.countries_states);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            
            is.close();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    
        jsonString = writer.toString();
        
        return jsonString;
    }
    
}
