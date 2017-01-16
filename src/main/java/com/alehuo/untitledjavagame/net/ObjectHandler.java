/*
 * Copyright 2017 alehuo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alehuo.untitledjavagame.net;

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
