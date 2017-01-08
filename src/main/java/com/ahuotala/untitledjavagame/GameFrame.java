/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame;

import com.ahuotala.untitledjavagame.graphics.Renderer;
import com.ahuotala.untitledjavagame.entities.MpPlayer;
import static com.ahuotala.untitledjavagame.Game.CENTERX;
import static com.ahuotala.untitledjavagame.Game.CENTERY;
import static com.ahuotala.untitledjavagame.Game.DEBUG_PLAYER;
import static com.ahuotala.untitledjavagame.Game.FONTSCALE;
import static com.ahuotala.untitledjavagame.Game.SCALE;
import static com.ahuotala.untitledjavagame.Game.SHOW_INVENTORY;
import static com.ahuotala.untitledjavagame.Game.WINDOW_HEIGHT;
import static com.ahuotala.untitledjavagame.Game.WINDOW_WIDTH;
import static com.ahuotala.untitledjavagame.Game.spriteSheet;
import com.ahuotala.untitledjavagame.postprocess.filters.DarkenFilter;
import com.ahuotala.untitledjavagame.map.GameMap;
import com.ahuotala.untitledjavagame.net.PlayerList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JOptionPane;
import static com.ahuotala.untitledjavagame.Game.INVENTORY;
import static com.ahuotala.untitledjavagame.Game.PLAYER;
import java.awt.Canvas;

/**
 * Game frame class
 *
 * @author alehuo
 */
public class GameFrame extends Canvas {

    /**
     * Game
     */
    private final Game game;

    /**
     * Renderer
     */
    private final Renderer renderer;

    /**
     * Image
     */
    private final BufferedImage image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);

    /**
     * Pixel array from image
     */
    private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    /**
     * Graphics object
     */
    private Graphics grphics;

    /**
     *
     * @param g
     */
    public GameFrame(Game g) {
        game = g;
        renderer = new Renderer(WINDOW_WIDTH, WINDOW_HEIGHT, pixels);
        super.setSize(new Dimension(WINDOW_WIDTH * SCALE, WINDOW_HEIGHT * SCALE));
        super.setMinimumSize(new Dimension(WINDOW_WIDTH * SCALE, WINDOW_HEIGHT * SCALE));
        super.setMaximumSize(new Dimension(WINDOW_WIDTH * SCALE, WINDOW_HEIGHT * SCALE));
        super.setPreferredSize(new Dimension(WINDOW_WIDTH * SCALE, WINDOW_HEIGHT * SCALE));
    }

    /**
     *
     */
    public void render() {
        //Create buffer strategy
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            //Triple buffering
            createBufferStrategy(3);
            return;
        }
        try {
            //Get draw graphics
            grphics = bs.getDrawGraphics();
            //Get map
            GameMap map = game.getGameMap();
            GameTime gt = game.getGameTime();
            //Clear
            renderer.clear();
            //Render base image
            renderer.render();
            grphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            //Font
            Font currentFont = grphics.getFont();
            grphics.setFont(new Font(currentFont.getName(), Font.BOLD, (int) Math.floor(18 * FONTSCALE)));

            //Apply darken filter
            renderer.setFilters(new DarkenFilter(gt.getFactor()));

            //Map
            map.renderMap(grphics, renderer, PLAYER);
            //Other objects
            map.renderObjects(grphics, renderer, PLAYER);
            map.detectCollision(PLAYER);

            //NPCs here
            PLAYER.render(renderer, grphics, map);

            //Render MP players
            if (game.getMp().isConnected()) {
                PlayerList pList = game.getMp().getUdpClient().getPlayerList();
                for (MpPlayer plr : pList.getPlayerList().values()) {
                    try {
                        if (plr.getUuid().equals(game.getMp().getUdpClient().getUuid())) {
                            continue;
                        }
                        plr.renderMpPlayer(grphics, renderer, PLAYER);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Error rendering player(s): " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }

            //Draw UI
            drawUi(grphics, gt, map);

            //Draw final image
            grphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);

            //Reset filter
            renderer.resetFilter();

        } finally {
            //Empty buffer
            grphics.dispose();
        }
        //Show frame
        bs.show();
    }

    public void drawUi(Graphics graphics, GameTime gt, GameMap map) {

        //Player health system
        //################
        int playerFullHearts = (int) Math.floor(PLAYER.getHealth() / 20);
        int playerHalfHearts = (int) Math.floor((PLAYER.getHealth() - playerFullHearts * 20) / 10);
        int heartX = CENTERX - 256;
        int heartY = 5;
        
        grphics.drawString(PLAYER.getHealth() + " / " + PLAYER.getMaxHealth() + " LP", heartX - 78, heartY + 22);
        grphics.drawString(PLAYER.getXp() + " xp", heartX - 78, heartY + 44);
        
        if (playerFullHearts == 0 && playerHalfHearts == 0 && PLAYER.getHealth() > 0) {
            game.getPlayerLowHealth().nextFrame(renderer, heartX, heartY);
        } else if (PLAYER.getHealth() == 0) {
            grphics.setColor(Color.red);
            //Black background
            grphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            grphics.drawString("YOU DIED", CENTERX - 48, CENTERY);
        } else {
            for (int hearts = 0; hearts < playerFullHearts; hearts++) {
                spriteSheet.paint(renderer, "full_heart", heartX, heartY);
                heartX += 26;
            }
            if (playerHalfHearts > 0) {
                spriteSheet.paint(renderer, "half_a_heart", heartX, heartY);
            }
        }
        //################

        //Show inventory
        //################
        if (SHOW_INVENTORY) {
            INVENTORY.renderInventory(grphics, renderer);
        }
        //################

        //Debug for PLAYER
        //################
        
        grphics.setColor(Color.white);

        if (DEBUG_PLAYER) {
            grphics.drawString("x " + PLAYER.getX(), 5, 15);
            grphics.drawString("y " + PLAYER.getY(), 5, 31);
            grphics.drawString("tileCount " + map.getRenderedTileCount(), 5, 47);
            grphics.drawString("tileX " + map.getCurrentTileX(), 5, 63);
            grphics.drawString("tileY " + map.getCurrentTileY(), 5, 79);
            grphics.drawString("windowWidth " + Game.WINDOW_WIDTH, 5, 95);
            grphics.drawString("windowHeight " + Game.WINDOW_HEIGHT, 5, 111);
            grphics.drawString("gameTime (0 - 2359) " + gt.getTime() + ", " + String.format("%.5f", gt.getFactor()), 5, 127);
        }
        
        //################
    }

}
