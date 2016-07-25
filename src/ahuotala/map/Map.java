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
 *
 * @author Aleksi
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
    private int tileCount;

    public Map(String name) {
        String line = "";
        this.scale = Game.SCALE;
        tiles = new HashMap<>();
        animations = new HashMap<>();
        tileEntities = new ArrayList<>();
        lines = new ArrayList<>();
//        tiles.put("grass", Game.spriteSheet.getSprite("grass", 32, 80, 16));
        tiles.put("grass", Game.spriteSheet.getSprite("grass", 32, 176, 32));
        tiles.put("grass_bottom", Game.spriteSheet.getSprite("grass_bottom", 32, 208, 32, 60));
        tiles.put("grass_z_1", Game.spriteSheet.getSprite("grass_z_1", 80, 160, 16, 30));
        tiles.put("house", Game.spriteSheet.getSprite("house", 112, 32, 96, 64));
        tiles.put("water", Game.spriteSheet.getSprite("water", 160, 0, 16));
        tiles.put("sand", Game.spriteSheet.getSprite("sand", 32, 304, 32));
        tiles.put("rock", Game.spriteSheet.getSprite("rock", 64, 80, 16));
        animations.put("water_ani", new Animation("Water", Game.spriteSheet, 60));
        animations.put("lava_ani", new Animation("Lava", Game.spriteSheet, 60));
        Game.ANIMATIONTICKER.register(animations.get("water_ani"));
        Game.ANIMATIONTICKER.register(animations.get("lava_ani"));
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
                    String tileType = line.split(",")[3];
                    boolean animated = (Integer.parseInt(line.split(",")[4]) == 1);
                    tileEntities.add(new Tile(x, y, z, tileType, animated));
                }
                System.out.println("Loaded map file into memory.");
                System.out.println("Min x:" + minX + ", min y:" + minY + ", max x:" + maxX + ", max y:" + maxY + ", min z:" + minZ + ", max z:" + maxZ);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            InputStream stream = getClass().getResourceAsStream(name + ".ani");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
//            int frameCount = 0;
//            if (stream != null) {
//                while ((line = reader.readLine()) != null) {
//                    if (line.contains("#") || line.isEmpty()) {
//                        continue;
//                    }
//                    //Skip comments
//                    String[] lineData;
//                    lineData = line.split(",");
//                    int x = Integer.parseInt(lineData[0]);
//                    int y = Integer.parseInt(lineData[1]);
//                    int width = Integer.parseInt(lineData[2]);
//                    int height = Integer.parseInt(lineData[3]);
//                    //Add the frame
//                    frames.add(spritesheet.getSprite("animation_" + name + "_frame" + frameCount, x, y, width, height));
//                    frameCount++;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
        return new Tile(0, 0, 0, "false", false);
    }

    public void renderMap(Graphics g, Player player) {
        offsetX = player.getOffsetX();
        offsetY = player.getOffsetY();
        int playerX = player.getX();
        int playerY = player.getY();

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
            String tileType = tile.getType();

            boolean isAnimated = tile.isAnimated();
            int xArea = 16;
            int yArea = 14;
            if (isAnimated) {
                if (!animations.containsKey(tileType)) {
                    System.err.println("Animation tile '" + tileType + "' is missing at x = " + x + ",y = " + y);
                }
                animations.get(tileType).nextFrame(g, x + offsetX, y + offsetY);
                if (Game.DEBUG) {
                    g.draw3DRect(x + offsetX, y + offsetY, animations.get(tileType).getWidth() * scale, animations.get(tileType).getHeight() * scale, false);
                }
                if ((player.getRealX() >= x + offsetX - xArea && player.getRealX() <= x + offsetX + xArea && player.getRealY() >= y + offsetY - yArea && player.getRealY() <= y + offsetY + yArea)) {
                    player.setCurrentTile(tileType);
                    System.out.println(player.getDirection());
                    if (Game.DEBUG) {
                        g.fill3DRect(x + offsetX, y + offsetY, animations.get(tileType).getWidth() * scale, animations.get(tileType).getHeight() * scale, false);
                    }
                }

            } else {
                if (y == maxY && tileType.equals("grass")) {
                    tileType = "grass_bottom";
                }
                if (z == 1) {
                    tileType = "grass_z_1";
                    y -= 28;
                }
                if (!tiles.containsKey(tileType)) {
                    System.err.println("Tile '" + tileType + "' is missing at x = " + x + ",y = " + y);
                } else {
                    g.drawImage(tiles.get(tileType), x + offsetX, y + offsetY, tiles.get(tileType).getWidth() * scale, tiles.get(tileType).getHeight() * scale, null);
                }

                if (Game.DEBUG) {
                    g.setColor(Color.red);
                    g.draw3DRect(x + offsetX, y + offsetY, tiles.get(tileType).getWidth() * scale, tiles.get(tileType).getHeight() * scale, false);
                }

                if (player.getRealX() >= x + offsetX - xArea && player.getRealX() <= x + offsetX + xArea && player.getRealY() >= y + offsetY - yArea && player.getRealY() <= y + offsetY + yArea) {
                    player.setCurrentTile(tileType);
                    switch (player.getDirection()) {
                        case UP:
                            break;
                        case DOWN:
                            break;
                        case LEFT:
                            break;
                        case RIGHT:
                            Tile tmpTile = this.getTile(x + offsetX + 32, y + offsetY + 32, z + 32);
                            break;
                    }
                    if (z != player.getZ()) {
                        player.setZ(z);
                    }

                    if (Game.DEBUG) {
                        g.fill3DRect(x + offsetX, y + offsetY, tiles.get(tileType).getWidth() * scale, tiles.get(tileType).getHeight() * scale, false);
                    }
                }

            }
            tileCount++;
        }
        if (Game.DEBUG) {
            System.out.println(tileCount + " of " + lines.size() + " tiles rendered this round");
        }

    }

    public void renderObject(Graphics g, int x, int y, String name) {
        g.drawImage(tiles.get(name), x + offsetX, y + offsetY, tiles.get(name).getWidth() * scale, tiles.get(name).getHeight() * scale, null);
    }

}
