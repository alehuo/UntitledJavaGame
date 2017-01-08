/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.singleplayer;

import com.ahuotala.untitledjavagame.entities.Player;
import com.ahuotala.untitledjavagame.Game;
import static com.ahuotala.untitledjavagame.Game.playing;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static com.ahuotala.untitledjavagame.Game.INVENTORY;

/**
 * Singleplayer class
 *
 * @author alehuo
 */
public class Singleplayer {

    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(Singleplayer.class.getName());

    /**
     * Player
     */
    private final Player player;

    /**
     * Game
     */
    private final Game game;

    /**
     * Name of the save file
     */
    private String saveFileName;

    /**
     * Save object
     */
    private SaveGame save;

    /**
     *
     * @param g
     * @param p
     */
    public Singleplayer(Game g, Player p) {
        player = p;
        game = g;
    }

    /**
     *
     * @return
     */
    public String getSaveFileName() {
        return saveFileName;
    }

    /**
     *
     * @param saveFileName
     */
    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    /*##############################################*/
    /**
     *
     */
    public void save() {

        File saveDir = new File("saves");
        saveDir.mkdir();

        File saveFile = new File(saveFileName);
        //If the file doesn't exist, create it
        if (!saveFile.exists() && playing) {
            try {
                LOG.log(Level.INFO, "Saving game.. Save file doesn't exist; Creating a new file..");
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
                save.saveState(player.getX(), player.getY(), player.getHealth(), player.getXp(), player.getDirection(), game.getGameTime().getTime(), INVENTORY.getInventory());
                try (FileOutputStream fileOutput = new FileOutputStream(saveFileName); ObjectOutputStream out = new ObjectOutputStream(fileOutput)) {
                    out.writeObject(save);
                    JOptionPane.showMessageDialog(game, "Game saved successfully.", Game.NAME, JOptionPane.INFORMATION_MESSAGE);
                    LOG.log(Level.INFO, "Game saved successfully.");
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(game, "Error saving game: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param file
     */
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
            LOG.log(Level.INFO, "Loaded a gamesave from ''{0}''", saveFileName);
        } catch (IOException | ClassNotFoundException e) {
            LOG.log(Level.SEVERE, null, e);
        }
        //Set PLAYER x, y, health, xp and direction
        player.setX(save.getX());
        player.setY(save.getY());
        player.setHealth(save.getHealth());
        player.setXp(save.getXp());
        player.setDirection(save.getDirection());
        //Set game time
        game.getGameTime().setTime(save.getCurrentGameTime());
        //Set PLAYER INVENTORY
        INVENTORY.setInventory(save.getInventory());
    }

    /**
     *
     * @param saveFileName
     */
    public void newGame(String saveFileName) {
        this.saveFileName = saveFileName;
        if (!saveFileName.trim().endsWith(".sav")) {
            this.saveFileName += ".sav";
        }
        save = new SaveGame();
        LOG.log(Level.INFO, "Player started a new game: ''{0}''", saveFileName);
        LOG.log(Level.INFO, "Player starting from coordinate ({0},{1})", new Object[]{save.getX(), save.getY()});
        //Set PLAYER x, y, health, xp and direction
        player.setX(save.getX());
        player.setY(save.getY());
        player.setHealth(save.getHealth());
        player.setXp(save.getXp());
        player.setDirection(save.getDirection());
        //Set PLAYER INVENTORY
        INVENTORY.setInventory(save.getInventory());
    }

}
