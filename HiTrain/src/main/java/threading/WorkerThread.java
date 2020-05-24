/*
 * Copyright (c) 2020.  Kumaran Devaneson
 * Created : 2/ 3/2020
 * All rights reserved
 */

package threading;

import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class WorkerThread implements Runnable {
    private final String sHostName, sTimeStamp;
    private final int iPort;
    private final byte[] sUdpRequest;
    private final Logger LOGGER;

    public WorkerThread(String sHostName, int iPort, String sTimeStamp, byte[] sUdpRequest, Logger LOGGER){
        this.sHostName = sHostName;
        this.iPort = iPort;
        this.sTimeStamp = sTimeStamp;
        this.sUdpRequest = sUdpRequest;
        this.LOGGER = LOGGER;
    }

    @Override
    public void run() {
        try {
            processCommand();
        } catch (InterruptedException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
    int fromByteArray(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8 ) |
                ((bytes[3] & 0xFF) << 0 );
    }

    private void processCommand() throws InterruptedException{
        try {

            Charset cset = Charset.forName("UTF-8");
            ByteBuffer message = ByteBuffer.wrap(sUdpRequest);
            int delay = Integer.parseInt(cset.decode(message).toString());
            //int delay = fromByteArray(sUdpRequest);
            LOGGER.debug("Received: " + delay + " Sleeping...");
            Thread.sleep(delay * 1000);
            LOGGER.debug("Slept : " + delay + " seconds... exiting...");

        } catch (Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        }
    }
}