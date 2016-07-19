/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aleksi
 */
public class MapGenerator {

    private static final int BLOCKSIZE = 32;

    public static void main(String[] args) {
        try {
            FileWriter fw = new FileWriter("src/ahuotala/map/map1.map");
            String tmp = "";
            fw.write("# x,y,tile_type,animation\r\n");
            fw.write("# Map 1\r\n");
            //Center x = 20, y = 12 (320, 192)
            for (int x = -44; x <= 84; x++) {
                for (int y = -52; y <= 76; y++) {
                    fw.write(x * BLOCKSIZE + "," + y * BLOCKSIZE + ",grass,0\r\n");
                }
            }
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
