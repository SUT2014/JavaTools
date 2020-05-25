package com.github.SUT2014.customSampler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainTry {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainTry.class);

    public static void main(String args[]){

        SampleProcessor sp = new SampleProcessor();
        try {
            sp.testFunction("127.0.0.1", "5990", "20");
        }catch (Exception e) {
            LOGGER.error("Request was not successfully processed",e);
         }
    }
}
