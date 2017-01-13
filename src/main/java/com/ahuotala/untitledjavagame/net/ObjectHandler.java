/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alehuo
 */
public class ObjectHandler {

    private static final Logger LOG = Logger.getLogger(ObjectHandler.class.getName());

    /**
     * Convert object to bytearray
     *
     * @param obj
     * @return
     */
    public static byte[] prepare(Object obj) {

        byte[] byteArr = new byte[8192];

        //Send in player list from server
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            byteArr = bos.toByteArray();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
        return byteArr;
    }

    /**
     * Receive a bytearray and convert to object
     *
     * @param dp
     * @return
     */
    public static Object receive(DatagramPacket dp) {

        Object tmpObject = new Object();

        ByteArrayInputStream baos = new ByteArrayInputStream(dp.getData(), dp.getOffset(), dp.getLength());
        try {
            ObjectInputStream oos = new ObjectInputStream(baos);

            tmpObject = (Object) oos.readObject();
            return tmpObject;
        } catch (IOException | ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return tmpObject;
    }
}
