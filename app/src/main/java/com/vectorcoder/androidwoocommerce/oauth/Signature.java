package com.vectorcoder.androidwoocommerce.oauth;

/**
 * Signs a base string, returning the OAuth signature
  */
public interface Signature
{
    /**
     * Returns the signature
     *
     * @param baseString url-encoded string to sign
     * @param apiSecret api secret for your app
     * @param tokenSecret token secret (empty string for the request token step)
     *
     * @return signature
     */
    public String getSignature(String baseString, String apiSecret, String tokenSecret);

    /**
     * Returns the signature method/algorithm
     *
     * @return
     */
    public String getSignatureMethod();
}
