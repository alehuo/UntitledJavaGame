/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.game;

import com.ahuotala.untitledjavagame.graphics.Renderer;
import com.ahuotala.untitledjavagame.entities.MpPlayer;
import static com.ahuotala.untitledjavagame.game.Game.CENTERX;
import static com.ahuotala.untitledjavagame.game.Game.CENTERY;
import static com.ahuotala.untitledjavagame.game.Game.DEBUG_PLAYER;
import static com.ahuotala.untitledjavagame.game.Game.FONTSCALE;
import static com.ahuotala.untitledjavagame.game.Game.SCALE;
import static com.ahuotala.untitledjavagame.game.Game.SHOW_INVENTORY;
import static com.ahuotala.untitledjavagame.game.Game.WINDOW_HEIGHT;
import static com.ahuotala.untitledjavagame.game.Game.WINDOW_WIDTH;
import static com.ahuotala.untitledjavagame.game.Game.spriteSheet;
import com.ahuotala.untitledjavagame.game.postprocess.filters.DarkenFilter;
import com.ahuotala.untitledjavagame.map.Map;
import com.ahuotala.untitledjavagame.net.PlayerList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JOptionPane;
import static com.ahuotala.untitledjavagame.game.Game.INVENTORY;
import static com.ahuotala.untitledjavagame.game.Game.PLAYER;
import java.awt.Canvas;

/**
 * Game frame class
 *
 * @author alehuo
 */
public class GameFrame extends Canvas {

    private final Renderer renderer;

    /**
     * Image data
     */
    private final BufferedImage image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);

    /**
     * Pixel array
     */
    private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    /**
     * Graphics object
     */
    private Graphics g;

    /**
     * Current font
     */
    private Font currentFont;

    private final Game game;

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
            g = bs.getDrawGraphics();
            //Get map
            Map map = game.getMap();
            GameTime gt = game.getGameTime();
            //Clear
//            renderer.clear();
            //Render base image
            renderer.render();
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            //Font
            currentFont = g.getFont();
            g.setFont(new Font(currentFont.getName(), Font.BOLD, (int) Math.floor(18 * FONTSCALE)));
//            if (Game.menuState != MenuState.NONE) {
//                //Image
//                g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
////                menu.render(g);
//            } else {
            //If the game is running, render map & npcs

            //Apply darken filter
            renderer.setFilters(new DarkenFilter(gt.getFactor()));

            //Map
            map.renderMap(g, renderer, PLAYER);
            //Other objects
            map.renderObjects(g, renderer, PLAYER);
            map.detectCollision(PLAYER);

            //NPCs here
            PLAYER.render(renderer, g, map);

            //Render MP players
            if (game.getMp().isConnected()) {
                PlayerList pList = game.getMp().getUdpClient().getPlayerList();
                for (MpPlayer plr : pList.getPlayerList().values()) {
                    try {
                        if (plr.getUuid().equals(game.getMp().getUdpClient().getUuid())) {
                            continue;
                        }
                        plr.renderMpPlayer(g, renderer, PLAYER);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Error rendering player(s): " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
            
            //Draw final image
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

            //Reset filter
            renderer.resetFilter();

            //Player health system
            int playerFullHearts = (int) Math.floor(PLAYER.getHealth() / 20);
            int playerHalfHearts = (int) Math.floor((PLAYER.getHealth() - playerFullHearts * 20) / 10);
            int heartX = CENTERX - 256;
            int heartY = 5;
            g.drawString(PLAYER.getHealth() + " / " + PLAYER.getMaxHealth() + " LP", heartX - 78, heartY + 22);
            g.drawString(PLAYER.getXp() + " xp", heartX - 78, heartY + 44);
            if (playerFullHearts == 0 && playerHalfHearts == 0 && PLAYER.getHealth() > 0) {
                game.getPlayerLowHealth().nextFrame(renderer, heartX, heartY);
            } else if (PLAYER.getHealth() == 0) {
                g.setColor(Color.red);
                //Black background
                g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                g.drawString("YOU DIED", CENTERX - 48, CENTERY);
            } else {
                for (int hearts = 0; hearts < playerFullHearts; hearts++) {
                    spriteSheet.paint(renderer, "full_heart", heartX, heartY);
                    heartX += 26;
                }
                if (playerHalfHearts > 0) {
                    spriteSheet.paint(renderer, "half_a_heart", heartX, heartY);
                }
            }

            if (SHOW_INVENTORY) {
                INVENTORY.renderInventory(g, renderer);
            }

            //Debug for PLAYER
            g.setColor(Color.white);

            if (DEBUG_PLAYER) {
                g.drawString("x " + PLAYER.getX(), 5, 15);
                g.drawString("y " + PLAYER.getY(), 5, 31);
                g.drawString("tileCount " + map.getRenderedTileCount(), 5, 47);
                g.drawString("tileX " + map.getCurrentTileX(), 5, 63);
                g.drawString("tileY " + map.getCurrentTileY(), 5, 79);
                g.drawString("windowWidth " + Game.WINDOW_WIDTH, 5, 95);
                g.drawString("windowHeight " + Game.WINDOW_HEIGHT, 5, 111);
                g.drawString("gameTime (0 - 2359) " + gt.getTime() + ", " + String.format("%.5f", gt.getFactor()), 5, 127);
            }

        } finally {
            //Empty buffer
            g.dispose();
        }
        //Show frame
        bs.show();
    }

}
