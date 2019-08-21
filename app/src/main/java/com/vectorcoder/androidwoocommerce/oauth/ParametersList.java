package com.vectorcoder.androidwoocommerce.oauth;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * @author: Pablo Fernandez
 */
public class ParametersList
{
    private static final char QUERY_STRING_SEPARATOR = '?';
    private static final String PARAM_SEPARATOR = "&";
    private static final String PAIR_SEPARATOR = "=";
    private static final String EMPTY_STRING = "";

    private final List<Parameter> params;

    public ParametersList()
    {
        params = new ArrayList<Parameter>();
    }

    ParametersList(List<Parameter> params)
    {
        this.params = new ArrayList<Parameter>(params);
    }

    public ParametersList(Map<String, String> map)
    {
        this();
        for(Map.Entry<String, String> entry : map.entrySet())
        {
            params.add(new Parameter(entry.getKey(), entry.getValue()));
        }
    }

    public void add(String key, String value)
    {
        params.add(new Parameter(key, value));
    }

    public String appendTo(String url)
    {
//        Preconditions.checkNotNull(url, "Cannot append to null URL");
        String queryString = asFormUrlEncodedString();
        if (queryString.equals(EMPTY_STRING))
        {
            return url;
        }
        else
        {
            url += url.indexOf(QUERY_STRING_SEPARATOR) != -1 ? PARAM_SEPARATOR : QUERY_STRING_SEPARATOR;
            url += queryString;
            return url;
        }
    }

    public String asOauthBaseString()
    {
        return OAuthEncoder.encode(asFormUrlEncodedString());
    }

    public String asFormUrlEncodedString()
    {
        if (params.size() == 0) return EMPTY_STRING;

        StringBuilder builder = new StringBuilder();
        for(Parameter p : params)
        {
            builder.append('&').append(p.asUrlEncodedPair());
        }
        return builder.toString().substring(1);
    }

    public void addAll(ParametersList other)
    {
        params.addAll(other.params);
    }

    public void addQuerystring(String queryString)
    {
        if (queryString != null && queryString.length() > 0)
        {
            for (String param : queryString.split(PARAM_SEPARATOR))
            {
                String pair[] = param.split(PAIR_SEPARATOR);
                String key = OAuthEncoder.decode(pair[0]);
                String value = pair.length > 1 ? OAuthEncoder.decode(pair[1]) : EMPTY_STRING;
                params.add(new Parameter(key, value));
            }
        }
    }

    public boolean contains(Parameter param)
    {
        return params.contains(param);
    }

    public int size()
    {
        return params.size();
    }

    public ParametersList sort()
    {
        ParametersList sorted = new ParametersList(params);

//        Log.d("sorted", sorted.params.get(0).asUrlEncodedPair());
//        Log.d("sorted", sorted.params.get(1).asUrlEncodedPair());
        Collections.sort(sorted.params);
//        Log.d("sorted", sorted.params.get(0).asUrlEncodedPair());
//        Log.d("sorted", sorted.params.get(1).asUrlEncodedPair());
        return sorted;
    }
}
