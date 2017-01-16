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
package com.alehuo.untitledjavagame.tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**
 *
 * @author alehuo
 */
public class MapGenerator {

    private static final int BLOCKSIZE = 32;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            String uuid = UUID.randomUUID().toString().substring(0, 6);
            FileWriter fw = new FileWriter("map_" + uuid + ".map");
            String tmp = "";
            fw.write("# x,y,tile_type_bottom,tile_type_mask_1,tile_type_mask2,tile_type_fringe_1,tile_type_fringe_2\r\n");
            fw.write("# Map " + uuid + "\r\n");
            //Center x = 20, y = 12 (320, 192)
            for (int x = -44; x <= 84; x++) {
                for (int y = -52; y <= 76; y++) {
                    fw.write(x * BLOCKSIZE + "," + y * BLOCKSIZE + ",grass,,,,\r\n");
                }
            }
            fw.close();
            System.out.println("Map file saved to map_" + uuid + ".map");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
