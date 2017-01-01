package com.ahuotala.untitledjavagame.game;

/**
 *
 * @author Aleksi Huotala
 */
public class Debug {

    private static boolean ENABLED = false;

    /**
     *
     * @param message
     */
    public static void log(String message) {
        if (ENABLED) {
            System.out.println("# " + message);
        }
    }
}
