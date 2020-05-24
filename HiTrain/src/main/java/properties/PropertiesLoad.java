/*
 * Copyright (c) 2020.  Kumaran Devaneson
 * Created : 29/ 2/2020
 * All rights reserved
 */

package properties;

import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoad {
    private static int LISTENER_PORT,ACCEPTOR_MONITOR_INTERVAL,WORKER_MONITOR_INTERVAL;
    private static int THREAD_POOL_SIZE;

    public static boolean loadProp(Logger LOGGER){
        InputStream inStream = null;
        try {
            Properties prop = new Properties();
            inStream =  PropertiesLoad.class.getClassLoader().getResourceAsStream("config.properties");

            if (inStream == null){
                LOGGER.error("Unable to find config.properties");
                return false;
            }
            prop.load(inStream);
            LISTENER_PORT = Integer.parseInt(prop.getProperty("LISTENER_PORT"));
            ACCEPTOR_MONITOR_INTERVAL = Integer.parseInt(prop.getProperty("ACCEPTOR_MONITOR_INTERVAL"));
            WORKER_MONITOR_INTERVAL = Integer.parseInt(prop.getProperty("WORKER_MONITOR_INTERVAL"));
            THREAD_POOL_SIZE = Integer.parseInt(prop.getProperty("THREAD_POOL_SIZE"));

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }
        finally {
            try {
                if(inStream != null)
                    inStream.close();
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
        return true;
    }

    public static int getListenerPort(){
        return LISTENER_PORT;
    }
    public static int getAcceptorMonitorInterval(){
        return ACCEPTOR_MONITOR_INTERVAL;
    }
    public static int getWorkerMonitorInterval(){
        return WORKER_MONITOR_INTERVAL;
    }
    public static int getThreadPoolSize(){
        return THREAD_POOL_SIZE;
    }

}
