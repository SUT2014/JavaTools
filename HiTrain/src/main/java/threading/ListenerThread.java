/*
 * Copyright (c) 2020.  Kumaran Devaneson
 * Created : 2/ 3/2020
 * All rights reserved
 */

package threading;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import monitor.MonitorClass;
import java.util.Arrays;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListenerThread implements Runnable{
    private final int iPort;
    private final Logger LOGGER;
    private DatagramSocket udpServer;
    private MonitorClass monitor;

    public ListenerThread(int iPort, Logger LOGGER) {
        this.iPort = iPort;
        this.LOGGER = LOGGER;
    }
    @Override
    public void run() {
        acceptRequest();
    }

    private void acceptRequest() {
        try
        {
            udpServer = new DatagramSocket(iPort);
            byte[] buffer = new byte[1024];
            DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
            LOGGER.info("Acceptor thread \"" + Thread.currentThread().getName() + "\" started and listening on port " + this.iPort);

            CreateThread.createWorkerThreadPool();
            monitor = new MonitorClass(LOGGER);
            CreateThread.createMonitorThread(monitor);
            CreateThread.getMonitorThread().start();

            while(true) {
                udpServer.receive(inPacket);
                byte[] bDataFullBuff = inPacket.getData();
                byte[] sUdpRequest = Arrays.copyOf(bDataFullBuff, inPacket.getLength());

                CreateThread.getWorkerThreadPool().submit(new WorkerThread(inPacket.getAddress().getHostAddress(), inPacket.getPort(), new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()), sUdpRequest, LOGGER));
            }
        }
        catch(Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        finally {
            LOGGER.error("Stopping Monitor Thread");
            monitor.shutdown();
            LOGGER.error("Stopping WorkerThread Pool");
            CreateThread.getWorkerThreadPool().shutdown();
            while (!CreateThread.getWorkerThreadPool().isTerminated()) {
            }
            LOGGER.error("Stopping Acceptor Thread");
            udpServer.disconnect();
            udpServer.close();
            //this block can be uncommented to return threads back
            //acceptRequest();
        }
    }
}
