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
