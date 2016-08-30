/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.graphics;

import ahuotala.game.Game;
import ahuotala.game.ItemId;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Aleksi Huotala
 */
public class SpriteSheet {

    private String spriteSheetPath;
    private String inventoryPath = "inventory.png";
    private BufferedImage image;
    private BufferedImage inventoryImage;
    private boolean imageLoaded = false;
    private boolean inventoryLoaded = false;
    private HashMap<String, BufferedImage> sprites;

    public SpriteSheet(String spriteSheetPath) {
        //Alustetaan hajautustaulu
        sprites = new HashMap<>();
        //Yritetään ladata kuva
        try {
            image = ImageIO.read(SpriteSheet.class.getResourceAsStream(spriteSheetPath));
//            BufferedImage tmpImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g2d = tmpImage.createGraphics();
//            g2d.drawImage(image, 0, 0, null);
//            g2d.dispose();
//            image = tmpImage;
//            tmpImage = null;
            imageLoaded = true;
            
            inventoryImage = ImageIO.read(SpriteSheet.class.getResourceAsStream(inventoryPath));
//            BufferedImage tmpInventoryImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g2dInv = tmpInventoryImage.createGraphics();
//            g2dInv.drawImage(inventoryImage, 0, 0, null);
//            g2dInv.dispose();
//            inventoryImage = tmpInventoryImage;
//            tmpInventoryImage = null;
            inventoryLoaded = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String line = "";
            InputStream stream = getClass().getResourceAsStream("items.itm");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            if (stream != null) {
                while ((line = reader.readLine()) != null) {
                    if (line.contains("#") || line.isEmpty()) {
                        continue;
                    }

                    //Parse variables
                    String[] lineData = line.split(",", -1);
                    String name = lineData[0];
                    int x = Integer.parseInt(lineData[1]);
                    int y = Integer.parseInt(lineData[2]);
                    int width = Integer.parseInt(lineData[3]);
                    int height = Integer.parseInt(lineData[4]);
                    sprites.put(name, image.getSubimage(x, y, width, height));
                }
                stream.close();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the inventory image
     *
     * @return BufferedImage Inventory image
     */
    public BufferedImage getInventory() {
        if (inventoryLoaded) {
            return inventoryImage;
        }
        return new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
    }

    public BufferedImage getItemIcon(ItemId itemId) {
        if (sprites.containsKey(itemId + "")) {
            return sprites.get(itemId + "");
        }
        return new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Metodi, jolla piirretään näyttöön haluttu kuva
     *
     * @param g Grafiikka
     * @param name Nimi
     * @param x X-koord. johon kuva ladataan
     * @param y Y-koord. johon kuva ladataan
     */
    public void paint(Graphics g, String name, int x, int y) {
        if (sprites.containsKey(name)) {
            BufferedImage tmpImg = sprites.get(name);
            g.drawImage(tmpImg, x, y, Game.SCALE * tmpImg.getWidth(), Game.SCALE * tmpImg.getHeight(), null);
        }
    }

    /**
     * Metodi, jolla ladataan pikkukuva muistiin isommasta kuvasta
     *
     * @param name
     * @param x Kuvan x-koordinaatti [0,leveys]
     * @param y Kuvan y-koodrinaatti [0,korkeus]
     * @param width Kuva-alueen leveys
     * @param height Kuva-alueen korkeus
     * @return BufferedImage
     */
    public BufferedImage getSprite(String name, int x, int y, int width, int height) {

        if (!sprites.containsKey(name) && imageLoaded) {
            BufferedImage tmpImg = image.getSubimage(x, y, width, height);
            sprites.put(name, tmpImg);
            return tmpImg;
        } else {
            return sprites.get(name);
        }

    }

    /**
     * Metodi, jolla ladataan pikkukuva muistiin isommasta kuvasta
     *
     * @param name
     * @param x Kuvan x-koordinaatti [0,leveys]
     * @param y Kuvan y-koodrinaatti [0,korkeus]
     * @param widthHeight Kuva-alueen leveys ja korkeus
     * @return BufferedImage
     */
    public BufferedImage getSprite(String name, int x, int y, int widthHeight) {

        if (!sprites.containsKey(name) && imageLoaded) {
            BufferedImage tmpImg = image.getSubimage(x, y, widthHeight, widthHeight);
            sprites.put(name, tmpImg);
            return tmpImg;
        } else {
            return sprites.get(name);
        }

    }

}
