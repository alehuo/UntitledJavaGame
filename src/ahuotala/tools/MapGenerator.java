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

    private static final int BLOCKSIZE = 16;

    public static void main(String[] args) {
        try {
            FileWriter fw = new FileWriter("src/ahuotala/map/map1.map");
            String tmp = "";
            fw.write("# x,y,tile_type,animation\r\n");
            fw.write("# Map 1\r\n");
            for (int x = -63; x <= 64; x++) {
                for (int y = -63; y <= 64; y++) {
                    fw.write(x * BLOCKSIZE + "," + y * BLOCKSIZE + ",grass,0\r\n");
                }
            }
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
