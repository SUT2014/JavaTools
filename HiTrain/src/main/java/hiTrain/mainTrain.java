
/*
 * Copyright (c) 2020.  Kumaran Devaneson
 * Created : 29/ 2/2020
 * All rights reserved
 */

package hiTrain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import properties.PropertiesLoad;
import threading.CreateThread;
import threading.ListenerThread;

public class mainTrain {
    private static final Logger LOGGER = LogManager.getLogger(mainTrain.class);

    public static void main(String args[])
    {
        LOGGER.debug("There is no spoon");
        try {
            if (PropertiesLoad.loadProp(LOGGER)) {
                System.out.println(PropertiesLoad.getListenerPort());

                CreateThread.createAcceptorThread();
                CreateThread.getAcceptorThread().submit(new ListenerThread(PropertiesLoad.getListenerPort(), LOGGER));
                while (true) {
                    Thread.sleep(PropertiesLoad.getAcceptorMonitorInterval() * 1000);
                }

            }
        }
        catch (Exception ex){
            LOGGER.error(ex.getMessage(), ex);
        }

    }
}
