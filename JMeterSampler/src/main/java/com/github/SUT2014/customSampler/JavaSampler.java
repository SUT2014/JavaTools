package com.github.SUT2014.customSampler;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;

public class JavaSampler extends AbstractJavaSamplerClient implements Serializable {
        private static final String ARG1_TAG = "IP";
        private static final String ARG2_TAG = "PORT";
        private static final String ARG3_TAG = "MAX_DELAY";
        private static final Logger LOGGER = LoggerFactory.getLogger(JavaSampler.class);
        @Override
        public Arguments getDefaultParameters() {
            Arguments defaultParameters = new Arguments();
            defaultParameters.addArgument(ARG1_TAG,"127.0.0.1");
            defaultParameters.addArgument(ARG2_TAG,"5990");
            defaultParameters.addArgument(ARG3_TAG,"20");
            return defaultParameters;
        }
        @Override
        public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
            String arg1 = javaSamplerContext.getParameter(ARG1_TAG);
            String arg2 = javaSamplerContext.getParameter(ARG2_TAG);
            String arg3 = javaSamplerContext.getParameter(ARG3_TAG);
            SampleProcessor functionalityForSampling = new SampleProcessor();
            SampleResult sampleResult = new SampleResult();
            sampleResult.sampleStart();
            try {
                String message = functionalityForSampling.testFunction(arg1,arg2,arg3);
                sampleResult.sampleEnd();;
                sampleResult.setSuccessful(Boolean.TRUE);
                sampleResult.setResponseCodeOK();
                sampleResult.setResponseMessage(message);
            } catch (Exception e) {
                LOGGER.error("Request was not successfully processed",e);
                sampleResult.sampleEnd();
                sampleResult.setResponseMessage(e.getMessage());
                sampleResult.setSuccessful(Boolean.FALSE);
            }
            return sampleResult;
        }
    }