/*
 * Copyright 2017 alehuo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alehuo.untitledjavagame.map;

import com.alehuo.untitledjavagame.entities.GameObject;
import com.alehuo.untitledjavagame.entities.Player;
import com.alehuo.untitledjavagame.Game;
import com.alehuo.untitledjavagame.graphics.Renderer;
import com.alehuo.untitledjavagame.graphics.Sprite;
import com.alehuo.untitledjavagame.graphics.animation.Animation;
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * GameMap renderer class Used to render the game's map
 *
 * @author alehuo
 */
public class GameMap {

    private static final Logger LOG = Logger.getLogger(GameMap.class.getName());

    private ArrayList<String> lines;
    private ArrayList<Tile> tileEntities;
    private ArrayList<GameObject> gameObjects;
    private HashMap<String, Sprite> tiles;
    private HashMap<String, Animation> animations;
    private int scale;
    private int offsetX = 0;
    private int offsetY = 0;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;
    private int currentTileX = Integer.MIN_VALUE;
    private int currentTileY = Integer.MIN_VALUE;
    private int tileCount;

    private boolean optimizeTileRendering = true;

    /**
     *
     * @param name
     */
    public GameMap(String name) {
        LOG.setLevel(Level.SEVERE);
        String line = "";
        this.scale = Game.SCALE;
        tiles = new HashMap<>();
        animations = new HashMap<>();
        tileEntities = new ArrayList<>();
        lines = new ArrayList<>();
        gameObjects = new ArrayList<>();
        /**
         * Load tiles into array
         */
        try {

            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("tiles/tiles.cfg");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                if (stream != null) {
                    while ((line = reader.readLine()) != null) {
                        if (line.trim().startsWith("#") || line.isEmpty()) {
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
                        tiles.put(tileName, Game.spriteSheet.loadSprite(tileName, x, y, width, height));

                        LOG.log(Level.INFO, "Loaded tile: {0}", tileName);

                    }
                    stream.close();
                }
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, null, e);
        }
        /**
         * Load objects into array
         */
        try {

            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("map/objects_" + name + ".obj");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                if (stream != null) {
                    while ((line = reader.readLine()) != null) {
                        if (line.trim().startsWith("#") || line.isEmpty()) {
                            continue;
                        }

                        //Parse variables
                        String[] lineData = line.split(",", -1);

                        String objectName = lineData[0];
                        int x = Integer.parseInt(lineData[1]);
                        int y = Integer.parseInt(lineData[2]);

                        //Add into tiles array
                        gameObjects.add(new GameObject(x, y, tiles.get(objectName)));
                        LOG.log(Level.INFO, "Loaded object: {0}", objectName);

                    }
                    stream.close();
                }
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, null, e);
        }
        /**
         * Load animations into array
         */
        try {

            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("tiles/animatedTiles.cfg");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                if (stream != null) {
                    while ((line = reader.readLine()) != null) {
                        if (line.trim().startsWith("#") || line.isEmpty()) {
                            continue;
                        }

                        //Parse variables
                        String[] lineData = line.split(",", -1);
                        String animationName = lineData[0];
                        String animationFileName = lineData[1];
                        int interval = Integer.parseInt(lineData[2]);

                        //Add into animations
                        animations.put(animationName, new Animation(animationFileName, interval));
                        //Register it to be tickable
                        Game.ANIMATIONTICKER.register(animations.get(animationName));

                        LOG.log(Level.INFO, "Loaded animated tile: {0}", animationName);
                    }
                    stream.close();
                    LOG.log(Level.INFO, "Loaded all tiles.");
                }
            }
        } catch (IOException e) {
            LOG.log(Level.WARNING, null, e);
        }

        try {

            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("map/" + name + ".map");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                if (stream != null) {
                    while ((line = reader.readLine()) != null) {
                        //Skip comments
                        if (line.trim().startsWith("#") || line.isEmpty()) {
                            continue;
                        }
                        lines.add(line);
                        String[] splittedLine = line.split(",", -1);
                        int x = Integer.parseInt(splittedLine[0]);
                        int y = Integer.parseInt(splittedLine[1]);
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

                        String tileTypeBottom = splittedLine[2];
                        String tileTypeMask = splittedLine[3];
                        String tileTypeMask2 = splittedLine[4];
                        String tileTypeFringe1 = splittedLine[5];
                        String tileTypeFringe2 = splittedLine[6];
                        boolean blocked = Integer.parseInt(splittedLine[7]) != 0;
                        tileEntities.add(new Tile(x, y, tileTypeBottom, tileTypeMask, tileTypeMask2, tileTypeFringe1, tileTypeFringe2, blocked));
                    }
                    LOG.log(Level.INFO, "Loaded map file into memory.");
                    LOG.log(Level.INFO, "Min x:{0}, min y:{1}, max x:{2}, max y:{3}", new Object[]{minX, minY, maxX, maxY});
                    stream.close();
                }
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, null, e);
        }

    }

    /**
     * Returns the rendered tile count
     *
     * @return
     */
    public int getRenderedTileCount() {
        return tileCount;
    }

    /**
     * Returns the minimum x coordinate
     *
     * @return X coordinate
     */
    public int getMinX() {
        return minX;
    }

    /**
     * Returns the minimum y coordinate
     *
     * @return Y coordinate
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * Returns the minimum y coordinate
     *
     * @return Y coordinate
     */
    public int getMinY() {
        return minY;
    }

    /**
     * Returns the maximum y coordinate
     *
     * @return Y coordinate
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Returns the tile by coordinates
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return Tile
     */
    public Tile getTile(int x, int y) {
        for (Tile tile : tileEntities) {
            if (tile.getX() == x && tile.getY() == y) {
                return tile;
            }
        }
        return null;
    }

    /**
     * Collision detection
     *
     * @param p
     */
    public void detectCollision(Player p) {
        gameObjects.stream().filter((gameObj) -> (gameObj.collidesWithPlayer(p))).forEachOrdered((_item) -> {
            LOG.log(Level.INFO, "Collision with gameObject");
        }); //If we have a collision
    }

    /**
     * Renders the map
     *
     * @param g Graphics
     * @param r Renderer
     * @param player Player
     */
    public void renderMap(Graphics g, Renderer r, Player player) {
        offsetX = player.getOffsetX();
        offsetY = player.getOffsetY();
        ArrayList<String> tileTypes = new ArrayList<>();
        //Player X and Y
        int playerX = player.getX();
        int playerY = player.getY();
        //Parse the map file line by line
        tileCount = 0;
        tileEntities.forEach((Tile tile) -> {
            //tile per tile, add things
            int x = tile.getX();
            int y = tile.getY();
            //Performance optimization: don't load tiles we don't need
            int radiusX = (int) Math.ceil(0.9 * Game.WINDOW_WIDTH);
            int radiusY = (int) Math.ceil(0.9 * Game.WINDOW_HEIGHT);
            if (!((x < playerX - radiusX || x > playerX + radiusX || y < playerY - radiusY || y > playerY + radiusY) && optimizeTileRendering)) {
                if (tile.collidesWithPlayer(player)) {
                    LOG.log(Level.INFO, "Collision");
                }
                tileTypes.add(tile.getTypeBottom());
                tileTypes.add(tile.getTypeMask());
                tileTypes.add(tile.getTypeMask2());
                tileTypes.add(tile.getTypeFringe1());
                tileTypes.add(tile.getTypeFringe2());
                tileTypes.stream().filter((tileType) -> !(tileType.isEmpty())).map((tileType) -> {
                    /**
                     * #########################################################################
                     */
                    if (y == maxY && tileType.equals("water_ani")) {
                        tileType = "water_bottom";
                    } else if (y == maxY && tileType.equals("grass")) {
                        tileType = "grass_bottom";
                    }
                    return tileType;
                }).map((tileType) -> {
                    if (animations.containsKey(tileType)) {
                        //Draw next frame
                        animations.get(tileType).nextFrame(r, x + offsetX, y + offsetY);
                        if (Game.DEBUG) {
                            if (animations.containsKey(tileType)) {
                                g.fill3DRect(x + offsetX, y + offsetY, animations.get(tileType).getWidth(), animations.get(tileType).getHeight(), false);
                            }
                        }
                        tileCount++;
                    } else if (tiles.containsKey(tileType)) {
                        //Draw image
                        r.renderSprite(tiles.get(tileType), x + offsetX, y + offsetY);
//                    g.drawImage(tiles.get(tileType), x + offsetX, y + offsetY, tiles.get(tileType).getWidth() * scale, tiles.get(tileType).getHeight() * scale, null);
//Debug
                        if (Game.DEBUG) {
                            g.setColor(Color.red);
                            g.draw3DRect(x + offsetX, y + offsetY, tiles.get(tileType).getWidth(), tiles.get(tileType).getHeight(), false);
                        }
                        tileCount++;
                    } else {
                        LOG.log(Level.SEVERE, "Could not find tile ''{0}''", tileType);
                    }
                    return tileType;
                }).filter((tileType) -> ((player.getRealX() >= x + offsetX && player.getRealX() <= x + offsetX + 32 && player.getRealY() >= y + offsetY - 8 && player.getRealY() <= y + offsetY + 24))).map((tileType) -> {
                    currentTileX = x;
                    currentTileY = y;
                    //Set current tile for PLAYER
                    player.setCurrentTile(tileType);
                    return tileType;
                }).filter((tileType) -> (Game.DEBUG)).forEachOrdered((tileType) -> {
                    if (animations.containsKey(tileType)) {
                        g.fill3DRect(x + offsetX, y + offsetY, animations.get(tileType).getWidth() * scale, animations.get(tileType).getHeight() * scale, false);
                    } else if (tiles.containsKey(tileType)) {
                        g.fill3DRect(x + offsetX, y + offsetY, tiles.get(tileType).getWidth() * scale, tiles.get(tileType).getHeight() * scale, false);
                    }
                });
                if (tile.isBlocked()) {
                    Color tmpColor = new Color(1f, 1f, 1f, .2f);
                    g.setColor(tmpColor);
                    g.fill3DRect(x + offsetX, y + offsetY, 32, 32, false);
                }
                //Clear the tileType array
                tileTypes.clear();
                /**
                 * #########################################################################
                 */
            }
        });

        LOG.log(Level.INFO, "{0} of {1} tiles rendered this round", new Object[]{tileCount, lines.size()});

    }

    /**
     * Renders objects
     *
     * @param g Graphics
     * @param r Renderer
     * @param p Player
     */
    public void renderObjects(Graphics g, Renderer r, Player p) {
        gameObjects.stream().map((gameObj) -> {
            gameObj.render(r, p);
            return gameObj;
        }).filter((gameObj) -> (Game.DEBUG)).forEachOrdered((gameObj) -> {
            gameObj.drawBoundaries(g, p);
        });
    }

    /**
     * Returns the current tile x
     *
     * @return Tile x
     */
    public int getCurrentTileX() {
        return currentTileX;
    }

    /**
     * Returns the current tile y
     *
     * @return Tile y
     */
    public int getCurrentTileY() {
        return currentTileY;
    }

    /**
     * Render an object
     *
     * Consider reworking this method.
     *
     * @param r Renderer
     * @param x X
     * @param y Y
     * @param name Name of the sprite
     */
    public void renderObject(Renderer r, int x, int y, String name) {
        r.renderSprite(Game.spriteSheet.getSpriteByName(name), x + offsetX, y + offsetY);
    }

}
