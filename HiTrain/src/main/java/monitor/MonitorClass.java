/*
 * Copyright (c) 2020.  Kumaran Devaneson
 * Created : 2/ 3/2020
 * All rights reserved
 */

package monitor;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import properties.PropertiesLoad;
import threading.CreateThread;

public class MonitorClass implements Runnable
{
    private static int iAcceptorMonInterval, iWorkerMonInterval;
    private static Logger LOGGER;
    private boolean bStopThread = false;

    public MonitorClass(Logger LOG) {
        LOGGER = LOG;
        iAcceptorMonInterval = PropertiesLoad.getAcceptorMonitorInterval();
        iWorkerMonInterval = PropertiesLoad.getWorkerMonitorInterval();
    }

    public void shutdown() {
        this.bStopThread = true;
    }

    @Override
    public void run() {
        int iNumSeconds = iAcceptorMonInterval;
        while(!bStopThread){
            try {
                if (iNumSeconds == iAcceptorMonInterval) {
                    LOGGER.info("Acceptor Thread Status: " + ((CreateThread.getAcceptorThread().isShutdown())?"STOPPED":"RUNNING"));
                    iNumSeconds = 0;
                }
                LOGGER.info(
                        String.format("[ThreadPool: %d/%d %s], Active: %d, Completed: %d, Total: %d, QueueSize: %d",
                                CreateThread.getWorkerThreadPool().getPoolSize(),
                                CreateThread.getWorkerThreadPool().getCorePoolSize(),
                                ((CreateThread.getWorkerThreadPool().isShutdown())?"STOPPED":"RUNNING"),
                                CreateThread.getWorkerThreadPool().getActiveCount(),
                                CreateThread.getWorkerThreadPool().getCompletedTaskCount(),
                                CreateThread.getWorkerThreadPool().getTaskCount(),
                                CreateThread.getWorkerThreadPool().getQueue().size()));
                Thread.sleep(iWorkerMonInterval * 1000);
                iNumSeconds += iWorkerMonInterval;
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
    }
}
