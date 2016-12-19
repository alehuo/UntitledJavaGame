/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;

import javax.swing.SwingUtilities;

/**
 *
 * @author alehuo
 */
public class Main {
    public static void main(String[] args) {
        Game javaGame = new Game();
        SwingUtilities.invokeLater(javaGame);
    }
}
