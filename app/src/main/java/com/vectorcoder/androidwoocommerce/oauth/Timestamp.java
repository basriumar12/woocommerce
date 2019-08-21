package com.vectorcoder.androidwoocommerce.oauth;

/**
 * This interfaces for nonce and timestamp
 */
public interface Timestamp
{
    /**
     * Returns the unix epoch timestamp in seconds
     *
     * @return timestamp
     */
    public String getTimestampInSeconds();

    /**
     * Returns a nonce (unique value for each request)
     *
     * @return nonce
     */
    public String getNonce();
}

