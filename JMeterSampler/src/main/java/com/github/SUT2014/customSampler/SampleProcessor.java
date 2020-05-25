package com.github.SUT2014.customSampler;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class SampleProcessor {
    public byte[] intToByteArray(int num){
        byte[] intBytes = new byte[4];
        intBytes[0] = (byte) (num >>> 24);
        intBytes[1] = (byte) (num >>> 16);
        intBytes[2] = (byte) (num >>> 8);
        intBytes[3] = (byte) num;
        return intBytes;
    }

    public String testFunction(String IP_ADDR, String PORT, String delay) throws Exception {
        Random rand = new Random();
        //InetAddress address = InetAddress.getLocalHost();
        InetAddress address = InetAddress.getByName(IP_ADDR);
        DatagramSocket socket = new DatagramSocket();

        byte[] buffer = intToByteArray(rand.nextInt(Integer.parseInt(delay)));
        //buffer = ByteBuffer.allocate(4).putInt(rand.nextInt(30)).array();
        //byte[] buffer = intToByteArray(10);
        DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, Integer.parseInt(PORT));
        socket.send(request);
        return("Success");
    }
}
