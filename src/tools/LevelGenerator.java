/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

/**
 *
 * @author Aleksi
 */
public class LevelGenerator {

    public static void main(String[] args) {
        String tmp = "";
        for (int x = -32; x < 32; x++) {
            for (int y = -32; y < 32; y++) {
                tmp += x + "," + y + "," + 0 + "\r\n";
            }
        }

        System.out.println(tmp);
    }
}
