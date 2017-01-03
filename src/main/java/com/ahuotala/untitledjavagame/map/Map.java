package com.ahuotala.untitledjavagame.map;

import com.ahuotala.untitledjavagame.entities.GameObject;
import com.ahuotala.untitledjavagame.entities.Player;
import com.ahuotala.untitledjavagame.game.Game;
import com.ahuotala.untitledjavagame.graphics.Renderer;
import com.ahuotala.untitledjavagame.graphics.Sprite;
import com.ahuotala.untitledjavagame.graphics.animation.Animation;
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
 * Map renderer class Used to render the game's map
 *
 * @author Aleksi Huotala
 */
public class Map {

    private static final Logger LOG = Logger.getLogger(Map.class.getName());

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

    private boolean optimizeTileRendering = false;

    /**
     *
     * @param name
     */
    public Map(String name) {
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
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

                    LOG.log(Level.INFO, "Loaded tile: " + tileName);

                }
                stream.close();
            }
            reader.close();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, null, e);
        }
        /**
         * Load objects into array
         */
        try {

            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("map/objects_" + name + ".obj");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
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
                    LOG.log(Level.INFO, "Loaded object: " + objectName);

                }
                stream.close();
            }
            reader.close();
        } catch (IOException e) {
            LOG.log(Level.SEVERE, null, e);
        }
        /**
         * Load animations into array
         */
        try {

            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("tiles/animatedTiles.cfg");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
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

                    LOG.log(Level.INFO, "Loaded animated tile: " + animationName);
                }
                stream.close();
                LOG.log(Level.INFO, "Loaded all tiles.");
            }
            reader.close();
        } catch (IOException e) {
            LOG.log(Level.WARNING, null, e);
        }

        try {

            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("map/" + name + ".map");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
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
                LOG.log(Level.INFO, "Min x:" + minX + ", min y:" + minY + ", max x:" + maxX + ", max y:" + maxY);
                stream.close();
            }
            reader.close();
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
     * Collision detector
     *
     * @param p
     */
    public void detectCollision(Player p) {
        for (GameObject gameObj : gameObjects) {
            //If we have a collision
            if (gameObj.collidesWithPlayer(p)) {
                LOG.log(Level.INFO, "Collision with gameObject");
            }
        }
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
            //Performance optimization: don't load tiles we don't need
            int radiusX = (int) Math.ceil(0.9 * Game.WINDOW_WIDTH);
            int radiusY = (int) Math.ceil(0.9 * Game.WINDOW_HEIGHT);
            if ((x < playerX - radiusX || x > playerX + radiusX || y < playerY - radiusY || y > playerY + radiusY) && optimizeTileRendering) {
                continue;
            }
            if (tile.collidesWithPlayer(player)) {
                LOG.log(Level.INFO, "Collision");
            }
            tileTypes.add(tile.getTypeBottom());
            tileTypes.add(tile.getTypeMask());
            tileTypes.add(tile.getTypeMask2());
            tileTypes.add(tile.getTypeFringe1());
            tileTypes.add(tile.getTypeFringe2());
            for (String tileType : tileTypes) {

                if (tileType.isEmpty()) {
                    continue;
                }
                /**
                 * #########################################################################
                 */
                if (y == maxY && tileType.equals("water_ani")) {
                    tileType = "water_bottom";
                } else if (y == maxY && tileType.equals("grass")) {
                    tileType = "grass_bottom";
                }
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
                    LOG.log(Level.SEVERE, "Could not find tile '" + tileType + "'");
                }
                if ((player.getRealX() >= x + offsetX && player.getRealX() <= x + offsetX + 32 && player.getRealY() >= y + offsetY - 8 && player.getRealY() <= y + offsetY + 24)) {
                    currentTileX = x;
                    currentTileY = y;
                    //Set current tile for PLAYER
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

        LOG.log(Level.INFO, tileCount + " of " + lines.size() + " tiles rendered this round");

    }

    /**
     * Renders objects
     *
     * @param g Graphics
     * @param r Renderer
     * @param p Player
     */
    public void renderObjects(Graphics g, Renderer r, Player p) {
        for (GameObject gameObj : gameObjects) {
            gameObj.render(r, p);
            if (Game.DEBUG) {
                gameObj.drawBoundaries(g, p);
            }
        }
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
