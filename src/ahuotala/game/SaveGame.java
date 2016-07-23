/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;

import ahuotala.entities.Player;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Aleksi
 */
public class SaveGame {

    private Properties prop;
    private InputStream input = null;

    public SaveGame(String file) {
        prop = new Properties();
        try {
            input = new FileInputStream("src/ahuotala/game/saves/" + file);
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getX() {
        return Integer.parseInt(prop.getProperty("x", "0"));
    }

    public int getY() {
        return Integer.parseInt(prop.getProperty("y", "0"));
    }

    public int getHealth() {
        return Integer.parseInt(prop.getProperty("health", Player.maxHealth + ""));
    }

    public void saveState(int x, int y, int health) {
        try {
            prop.setProperty("x", x + "");
            prop.setProperty("y", y + "");
            prop.setProperty("health", health + "");

            File file = new File("src/ahuotala/game/saves/save.sav");
            OutputStream out = new FileOutputStream(file);
            prop.store(out, "Save file");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
