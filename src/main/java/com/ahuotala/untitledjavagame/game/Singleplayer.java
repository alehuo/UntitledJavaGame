/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.game;

import com.ahuotala.untitledjavagame.entities.Player;
import static com.ahuotala.untitledjavagame.game.Game.inventory;
import static com.ahuotala.untitledjavagame.game.Game.player;
import static com.ahuotala.untitledjavagame.game.Game.playing;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Singleplayer class
 *
 * @author alehuo
 */
public class Singleplayer {

    private final Player player;

    private final Game game;

    private String saveFileName;

    /**
     * Save object
     */
    private SaveGame save;

    private static final Logger LOG = Logger.getLogger(Singleplayer.class.getName());

    public Singleplayer(Game g, Player p) {
        player = p;
        game = g;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    /*##############################################*/
    public void save() {

        File saveDir = new File("saves");
        saveDir.mkdir();

        File saveFile = new File(saveFileName);
        //If the file doesn't exist, create it
        if (!saveFile.exists() && playing) {
            try {
                LOG.log(Level.INFO, "Save file doesn't exist; Creating a new file..");
                saveFile.createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(game, "Error saving game: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                LOG.log(Level.SEVERE, null, ex);
                System.exit(0);
            }
        }
        try {
            if (save != null && playing) {
                //Save the game
                LOG.log(Level.INFO, "Saving game..");
                save.saveState(player.getX(), player.getY(), player.getHealth(), player.getXp(), player.getDirection(), game.getGameTime().getTime(), inventory.getInventory());
                try (FileOutputStream fileOutput = new FileOutputStream(saveFileName); ObjectOutputStream out = new ObjectOutputStream(fileOutput)) {
                    out.writeObject(save);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(game, "Error saving game: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    public void loadSaveFile(File file) {
        /**
         * Load from save
         */
        try {
            File saveFile = file;
            saveFileName = saveFile.getAbsolutePath();
            FileInputStream fileInput = new FileInputStream(saveFileName);
            ObjectInputStream in = new ObjectInputStream(fileInput);
            save = (SaveGame) in.readObject();
            in.close();
            fileInput.close();

        } catch (IOException | ClassNotFoundException e) {
            LOG.log(Level.SEVERE, null, e);
        }
        //Set player x, y, health, xp and direction
        player.setX(save.getX());
        player.setY(save.getY());
        player.setHealth(save.getHealth());
        player.setXp(save.getXp());
        player.setDirection(save.getDirection());
        //Set game time
        game.getGameTime().setTime(save.getCurrentGameTime());
        //Set player inventory
        inventory.setInventory(save.getInventory());
    }

    public void newGame(String saveFileName) {
        this.saveFileName = saveFileName;
        if (!saveFileName.trim().endsWith(".sav")) {
            this.saveFileName += ".sav";
        }
        save = new SaveGame();
        //Set player x, y, health, xp and direction
        player.setX(save.getX());
        player.setY(save.getY());
        player.setHealth(save.getHealth());
        player.setXp(save.getXp());
        player.setDirection(save.getDirection());
        //Set player inventory
        inventory.setInventory(save.getInventory());
    }

}
