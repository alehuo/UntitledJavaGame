package com.ahuotala.untitledjavagame.tools;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Aleksi Huotala
 */
public class MapGenerator {

    private static final int BLOCKSIZE = 32;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            FileWriter fw = new FileWriter("src/ahuotala/map/map3.map");
            String tmp = "";
            fw.write("# x,y,tile_type_bottom,tile_type_mask_1,tile_type_mask2,tile_type_fringe_1,tile_type_fringe_2\r\n");
            fw.write("# Map 3\r\n");
            //Center x = 20, y = 12 (320, 192)
            for (int x = -44; x <= 84; x++) {
                for (int y = -52; y <= 76; y++) {
                    fw.write(x * BLOCKSIZE + "," + y * BLOCKSIZE + ",grass,,,,\r\n");
                }
            }
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
