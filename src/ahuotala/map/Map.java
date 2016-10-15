package ahuotala.map;

import ahuotala.entities.GameObject;
import ahuotala.entities.Player;
import ahuotala.game.Game;
import ahuotala.game.Renderer;
import ahuotala.game.Tile;
import ahuotala.graphics.Sprite;
import ahuotala.graphics.animation.Animation;
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Map renderer class Used to render the game's map
 *
 * @author Aleksi Huotala
 */
public class Map {

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

    public Map(String name) {
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

            InputStream stream = getClass().getResourceAsStream("cfg/tiles.cfg");
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
         * Load objects into array
         */
        try {

            InputStream stream = getClass().getResourceAsStream("objects_" + name + ".obj");
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

                    System.out.println("Loaded object: " + objectName);
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
                    Game.animationTicker.register(animations.get(animationName));

                    System.out.println("Loaded animated tile: " + animationName);
                }
                stream.close();
                System.out.println("Loaded all tiles.");
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
                    //Skip comments
                    if (line.trim().startsWith("#") || line.isEmpty()) {
                        continue;
                    }
                    lines.add(line);
                    String[] splittedLine = line.split(",", -1);
                    int x = Integer.parseInt(splittedLine[0]) * Game.SCALE;
                    int y = Integer.parseInt(splittedLine[1]) * Game.SCALE;
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
                System.out.println("Loaded map file into memory.");
                System.out.println("Min x:" + minX + ", min y:" + minY + ", max x:" + maxX + ", max y:" + maxY);
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

    public Tile getTile(int x, int y) {
        for (Tile tile : tileEntities) {
            if (tile.getX() == x && tile.getY() == y) {
                return tile;
            }
        }
        return new Tile(0, 0, "false", null, null, null, null, false);
    }

    public void detectCollision(Player p) {
        for (GameObject gameObj : gameObjects) {
            //If we have a collision
            if (gameObj.collidesWithPlayer(p)) {
                if (Game.DEBUG) {
                    System.out.println("Collision with gameObject");
                }
            }
        }
    }

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
            int radiusX = (int) Math.ceil(0.72 * Game.WINDOW_WIDTH);
            int radiusY = (int) Math.ceil(0.72 * Game.WINDOW_HEIGHT);
            if (x < playerX - radiusX || x > playerX + radiusX || y < playerY - radiusY || y > playerY + radiusY) {
                continue;
            }
            if (tile.collidesWithPlayer(player)) {
                if (Game.DEBUG) {
                    System.out.println("Collision");
                }
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
                            g.fill3DRect(x + offsetX, y + offsetY, animations.get(tileType).getWidth() * scale, animations.get(tileType).getHeight() * scale, false);
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
                        g.draw3DRect(x + offsetX, y + offsetY, tiles.get(tileType).getWidth() * scale, tiles.get(tileType).getHeight() * scale, false);
                    }
                    tileCount++;
                } else {
                    System.err.println("Could not find tile '" + tileType + "'");
                }
                if ((player.getRealX() >= x + offsetX && player.getRealX() <= x + offsetX + 32 && player.getRealY() >= y + offsetY - 8 && player.getRealY() <= y + offsetY + 24)) {
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
            if (tile.isBlocked()) {
                Color tmpColor = new Color(1f, 1f, 1f, .2f);
                g.setColor(tmpColor);
                g.fill3DRect(x + offsetX, y + offsetY, 32 * Game.SCALE, 32 * Game.SCALE, false);
            }
            //Clear the tileType array
            tileTypes.clear();
            /**
             * #########################################################################
             */
        }
        if (Game.DEBUG) {
            System.out.println(tileCount + " of " + lines.size() + " tiles rendered this round");
        }
    }

    public void renderObjects(Graphics g, Renderer r, Player p) {
        for (GameObject gameObj : gameObjects) {
            gameObj.render(r, p);
            if (Game.DEBUG) {
                gameObj.drawBoundaries(g, p);
            }
        }
    }

    public int getCurrentTileX() {
        return currentTileX;
    }

    public int getCurrentTileY() {
        return currentTileY;
    }

    public void renderObject(Renderer r, int x, int y, String name) {
        r.renderSprite(Game.spriteSheet.getSpriteByName(name), x + offsetX, y + offsetY);
//        g.drawImage(tiles.get(name), x + offsetX, y + offsetY, tiles.get(name).getWidth() * scale, tiles.get(name).getHeight() * scale, null);
    }

}
