/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alehuo
 */
public class MultiplayerDebug {

    public static void main(String[] args) throws UnknownHostException, InterruptedException {

//        for (int i = 0; i < 10; i++) {
//            Thread t = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Multiplayer m = new Multiplayer();
//                    try {
//                        m.connect(InetAddress.getByName("localhost"), 9876);
//                    } catch (UnknownHostException ex) {
//                        Logger.getLogger(MultiplayerDebug.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    if (m.isConnected()) {
//                        try {
//                            m.update();
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(MultiplayerDebug.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                }
//            });
//            t.start();
//        }
        Multiplayer m = new Multiplayer();
        try {
            m.connect(InetAddress.getByName("localhost"), 9876);
        } catch (UnknownHostException ex) {
            Logger.getLogger(MultiplayerDebug.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (m.isConnected()) {
            try {
                m.update();
            } catch (InterruptedException ex) {
                Logger.getLogger(MultiplayerDebug.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
