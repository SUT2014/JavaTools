/*
 * Copyright (c) 2020.  Kumaran Devaneson
 * Created : 2/ 3/2020
 * All rights reserved
 */

package threading;

import properties.PropertiesLoad;
import monitor.MonitorClass;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CreateThread {
    private static ExecutorService acceptorThread;
    private static ThreadPoolExecutor workerThreadPool;
    private static Thread monitorThread;

    public static void createAcceptorThread() {
        ThreadFactory customThreadFactory = new CustomThreadFactory()
                .setNamePrefix("AcceptThread").setDaemon(false)
                .setPriority(Thread.MAX_PRIORITY).build();
        acceptorThread = Executors.newSingleThreadExecutor(customThreadFactory);
    }

    public static void createWorkerThreadPool() {
        ThreadFactory customThreadFactory = new CustomThreadFactory()
                .setNamePrefix("WorkerThread").setDaemon(false)
                .setPriority(Thread.MAX_PRIORITY).build();
        workerThreadPool = new ThreadPoolExecutor(PropertiesLoad.getThreadPoolSize(), PropertiesLoad.getThreadPoolSize(), 60L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100000), customThreadFactory);
        workerThreadPool.prestartAllCoreThreads();
    }

    public static void createMonitorThread(MonitorClass monitor) {
        monitorThread = new Thread(monitor);
        monitorThread.setName("MonitorThread");
    }

    public static ExecutorService getAcceptorThread() {
        return acceptorThread;
    }

    public static ThreadPoolExecutor getWorkerThreadPool() {
        return workerThreadPool;
    }

    public static Thread getMonitorThread() {
        return monitorThread;
    }
}
