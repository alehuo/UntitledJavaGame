/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.map;

import ahuotala.entities.Player;
import ahuotala.game.Game;
import ahuotala.game.Tile;
import ahuotala.graphics.animation.Animation;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Map rendered class Used to render the game's map
 *
 * @author Aleksi Huotala
 */
public class Map {

    private ArrayList<String> lines;
    private ArrayList<Tile> tileEntities;
    private HashMap<String, BufferedImage> tiles;
    private HashMap<String, Animation> animations;
    private int scale;
    private int offsetX = 0;
    private int offsetY = 0;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private int minZ;
    private int maxZ;
    private int currentTileX = Integer.MIN_VALUE;
    private int currentTileY = Integer.MIN_VALUE;
    private int tileCount;

    public Map(String name) {
        String line = "";
        this.scale = Game.SCALE;
        tiles = new HashMap<>();
        animations = new HashMap<>();
        tileEntities = new ArrayList<>();
        lines = new ArrayList<>();
        /**
         * Load tiles into array
         */
        try {

            InputStream stream = getClass().getResourceAsStream("cfg/tiles.cfg");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            if (stream != null) {
                while ((line = reader.readLine()) != null) {
                    if (line.contains("#") || line.isEmpty()) {
                        continue;
                    }

                    //Parse variables
                    String[] lineData = line.split(",");
                    String tileName = lineData[0];

                    int x = Integer.parseInt(lineData[1]);
                    int y = Integer.parseInt(lineData[2]);
                    int width = Integer.parseInt(lineData[3]);
                    int height = Integer.parseInt(lineData[4]);

                    //Add into tiles array
                    tiles.put(tileName, Game.spriteSheet.getSprite(tileName, x, y, width, height));

                    System.out.println("Loaded tile: " + tileName);
                }
                stream.close();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * Load animations into array
         */
        try {

            InputStream stream = getClass().getResourceAsStream("cfg/animatedTiles.cfg");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            if (stream != null) {
                while ((line = reader.readLine()) != null) {
                    if (line.contains("#") || line.isEmpty()) {
                        continue;
                    }

                    //Parse variables
                    String[] lineData = line.split(",");
                    String animationName = lineData[0];
                    String animationFileName = lineData[1];
                    int interval = Integer.parseInt(lineData[2]);

                    //Add into animations
                    animations.put(animationName, new Animation(animationFileName, Game.spriteSheet, interval));
                    //Register it to be tickable
                    Game.ANIMATIONTICKER.register(animations.get(animationName));

                    System.out.println("Loaded animated tile: " + animationName);
                }
                stream.close();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            InputStream stream = getClass().getResourceAsStream(name + ".map");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            if (stream != null) {
                while ((line = reader.readLine()) != null) {
                    if (line.contains("#") || line.isEmpty()) {
                        continue;
                    }

                    //Skip comments
                    if (line.contains("#") || line.isEmpty()) {
                        continue;
                    }
                    lines.add(line);
                    int x = Integer.parseInt(line.split(",")[0]);
                    int y = Integer.parseInt(line.split(",")[1]);
                    int z = Integer.parseInt(line.split(",")[2]);
                    //Calculate max x, y and z
                    if (x < minX) {
                        minX = x;
                    }
                    if (x > maxX) {
                        maxX = x;
                    }
                    if (y < minY) {
                        minY = y;
                    }
                    if (y > maxY) {
                        maxY = y;
                    }
                    if (z < minZ) {
                        minZ = z;
                    }
                    if (z > maxZ) {
                        maxZ = z;
                    }
                    String tileTypeBottom = line.split(",")[3];
                    String tileTypeMask = line.split(",")[4];
                    String tileTypeMask2 = line.split(",")[5];
                    String tileTypeFringe1 = line.split(",")[6];
                    String tileTypeFringe2 = line.split(",")[7];
                    boolean animated = (Integer.parseInt(line.split(",")[8]) == 1);
                    tileEntities.add(new Tile(x, y, z, tileTypeBottom, tileTypeMask, tileTypeMask2, tileTypeFringe1, tileTypeFringe2, animated));
                }
                System.out.println("Loaded map file into memory.");
                System.out.println("Min x:" + minX + ", min y:" + minY + ", max x:" + maxX + ", max y:" + maxY + ", min z:" + minZ + ", max z:" + maxZ);
                stream.close();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getRenderedTileCount() {
        return tileCount;
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public Tile getTile(int x, int y, int z) {
        for (Tile tile : tileEntities) {
            if (tile.getX() == x && tile.getY() == y && tile.getZ() == z) {
                return tile;
            }
        }
        return new Tile(0, 0, 0, "false", null, null, null, null);
    }

    public void renderMap(Graphics g, Player player) {
        offsetX = player.getOffsetX();
        offsetY = player.getOffsetY();
        ArrayList<String> tileTypes = new ArrayList<>();
        int playerX = player.getX();
        int playerY = player.getY();
        int xArea = 16;
        int yArea = 14;

        //Parse the map file line by line
        tileCount = 0;
        for (Tile tile : tileEntities) {

            //tile per tile, add things
            int x = tile.getX();
            int y = tile.getY();
            int z = tile.getZ();
            //Performance optimization: don't load tiles we don't need
            int radiusX = (int) Math.ceil(0.72 * Game.WINDOW_WIDTH);
            int radiusY = (int) Math.ceil(0.72 * Game.WINDOW_HEIGHT);
            if (x < playerX - radiusX || x > playerX + radiusX || y < playerY - radiusY || y > playerY + radiusY) {
                continue;
            }
            tileTypes.add(tile.getTypeBottom());
            tileTypes.add(tile.getTypeMask());
            tileTypes.add(tile.getTypeMask2());
            tileTypes.add(tile.getTypeFringe1());
            tileTypes.add(tile.getTypeFringe2());
            for (String tileType : tileTypes) {

                if (tileType.isEmpty() || tileType == null) {
                    continue;
                }
                /**
                 * #########################################################################
                 */
                if (animations.containsKey(tileType)) {
                    //Draw next frame
                    animations.get(tileType).nextFrame(g, x + offsetX, y + offsetY);
                    if (Game.DEBUG) {
                        if (animations.containsKey(tileType)) {
                            g.fill3DRect(x + offsetX, y + offsetY, animations.get(tileType).getWidth() * scale, animations.get(tileType).getHeight() * scale, false);
                        }
                    }
                    tileCount++;
                } else if (tiles.containsKey(tileType)) {
                    if (y == maxY && tileType.equals("grass")) {
                        tileType = "grass_bottom";
                    }
                    //Draw image
                    g.drawImage(tiles.get(tileType), x + offsetX, y + offsetY, tiles.get(tileType).getWidth() * scale, tiles.get(tileType).getHeight() * scale, null);
                    //Debug
                    if (Game.DEBUG) {
                        g.setColor(Color.red);
                        g.draw3DRect(x + offsetX, y + offsetY, tiles.get(tileType).getWidth() * scale, tiles.get(tileType).getHeight() * scale, false);
                    }
                    tileCount++;
                } else {
                    System.err.println("Could not find tile '" + tileType + "'");
                }
                if ((player.getRealX() >= x + offsetX - xArea && player.getRealX() <= x + offsetX + xArea && player.getRealY() >= y + offsetY - yArea && player.getRealY() <= y + offsetY + yArea)) {
                    currentTileX = x;
                    currentTileY = y;
                    //Set current tile for player
                    player.setCurrentTile(tileType);
                    if (Game.DEBUG) {
                        if (animations.containsKey(tileType)) {
                            g.fill3DRect(x + offsetX, y + offsetY, animations.get(tileType).getWidth() * scale, animations.get(tileType).getHeight() * scale, false);
                        } else if (tiles.containsKey(tileType)) {
                            g.fill3DRect(x + offsetX, y + offsetY, tiles.get(tileType).getWidth() * scale, tiles.get(tileType).getHeight() * scale, false);
                        }
                    }
                }
            }
            //Clean the tileType array
            tileTypes.clear();
            /**
             * #########################################################################
             */
        }
        if (Game.DEBUG) {
            System.out.println(tileCount + " of " + lines.size() + " tiles rendered this round");
        }
    }

    public int getCurrentTileX() {
        return currentTileX;
    }

    public int getCurrentTileY() {
        return currentTileY;
    }

    public void renderObject(Graphics g, int x, int y, String name) {
        g.drawImage(tiles.get(name), x + offsetX, y + offsetY, tiles.get(name).getWidth() * scale, tiles.get(name).getHeight() * scale, null);
    }

}
