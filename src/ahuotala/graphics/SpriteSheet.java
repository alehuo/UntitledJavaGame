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

    public String path;
    public int rows;
    public int columns;
    public int scale;
    public BufferedImage image;
    public HashMap<String, BufferedImage> sprites;

    public SpriteSheet(String path) {

        this.scale = Game.SCALE;

        //Yritetään ladata kuva
        try {
            image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Alustetaan hajautustaulu
        sprites = new HashMap<>();

    }

    /**
     * Metodi, jolla piirretään näyttöön haluttu kuva
     *
     * @param g Grafiikka-olio
     * @param index Indeksi
     * @param x X-koord. johon kuva ladataan
     * @param y Y-koord. johon kuva ladataan
     */
    public void paint(Graphics g, String name, int x, int y) {
        BufferedImage tmpImg = sprites.get(name);
        g.drawImage(tmpImg, x, y, scale * tmpImg.getWidth(), scale * tmpImg.getHeight(), null);
    }

    /**
     * Metodi, jolla ladataan pikkukuva muistiin isommasta kuvasta
     *
     * @param x Kuvan x-koordinaatti [0,leveys]
     * @param y Kuvan y-koodrinaatti [0,korkeus]
     * @param width Kuva-alueen leveys
     * @param height Kuva-alueen korkeus
     * @return BufferedImage
     */
    public BufferedImage getSprite(String name, int x, int y, int width, int height) {

        if (!sprites.containsKey(name)) {
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
     * @param x Kuvan x-koordinaatti [0,leveys]
     * @param y Kuvan y-koodrinaatti [0,korkeus]
     * @param wH Kuva-alueen leveys ja korkeus
     * @return BufferedImage
     */
    public BufferedImage getSprite(String name, int x, int y, int widthheight) {

        if (!sprites.containsKey(name)) {
            BufferedImage tmpImg = image.getSubimage(x, y, widthheight, widthheight);
            sprites.put(name, tmpImg);
            return tmpImg;
        } else {
            return sprites.get(name);
        }

    }

}
