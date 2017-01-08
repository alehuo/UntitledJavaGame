/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame;

import com.ahuotala.untitledjavagame.console.Console;
import javax.swing.SwingUtilities;

/**
 *
 * @author alehuo
 */
public class Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            //New instance of Game
            new Game();

            if (args.length == 0) {
                if (Game.ENABLECONSOLE) {
                    //New instance of Console
                    new Console();
                }
            } else {
                //Iterate through args
                for (int i = 0; i < args.length; i++) {
                    //Open console if we want to
                    if (args[i].equalsIgnoreCase("-console")) {
                        //New instance of Console
                        new Console();
                        //Break out from loop
                        break;
                    }
                }
            }

        });

    }
}
