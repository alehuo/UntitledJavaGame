/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.graphics;

import ahuotala.game.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
        //Yritetään ladata kuva
        try {
            image = ImageIO.read(SpriteSheet.class.getResourceAsStream(spriteSheetPath));
            imageLoaded = true;
            inventoryImage = ImageIO.read(SpriteSheet.class.getResourceAsStream(inventoryPath));
            inventoryLoaded = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Alustetaan hajautustaulu
        sprites = new HashMap<>();

    }

    /**
     * Returns the inventory image
     * @return BufferedImage Inventory image
     */
    public BufferedImage getInventory() {
        if (inventoryLoaded) {
            return inventoryImage;
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
