package com.vectorcoder.androidwoocommerce.oauth;

import java.util.Random;

/**
 * This class generate timestamp and nonce
 */
public class TimestampService implements Timestamp
{
    private Timer timer;

    /**
     * Default constructor.
     */
    public TimestampService()
    {
        timer = new Timer();
    }

    /**
     * {@inheritDoc}
     */
    public String getNonce()
    {
        Long ts = getTs();
        return String.valueOf(ts + timer.getRandomInteger());
    }

    /**
     * {@inheritDoc}
     */
    public String getTimestampInSeconds()
    {
        return String.valueOf(getTs());
    }

    private Long getTs()
    {
        return timer.getMilis() / 1000;
    }

    void setTimer(Timer timer)
    {
        this.timer = timer;
    }

    /**
     * Inner class that uses {@link System} for generating the timestamps.
     *
     * @author Pablo Fernandez
     */
    static class Timer
    {
        private final Random rand = new Random();
        Long getMilis()
        {
            return System.currentTimeMillis();
        }

        Integer getRandomInteger()
        {
            return rand.nextInt();
        }
    }

}