/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame;

import com.ahuotala.untitledjavagame.game.Console;
import com.ahuotala.untitledjavagame.game.Game;
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
            new Game();
            if (Game.ENABLECONSOLE) {
                new Console();
            }
        });

    }
}
