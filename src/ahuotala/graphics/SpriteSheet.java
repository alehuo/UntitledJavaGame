/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Aleksi Huotala
 */
public class SpriteSheet {

    public String path;
    public int width;
    public int height;
    public int rows;
    public int columns;
    public int scale;
    public BufferedImage image;
    public ArrayList<BufferedImage> sprites;

    public SpriteSheet(String path, int scale) {

        //Leveys, korkeus
        this.width = width;
        this.height = height;
        this.scale = scale;

        //Yritetään ladata kuva
        try {
            image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Alustetaan hajautustaulu
        sprites = new ArrayList<>();

    }

    /**
     * Metodi, jolla piirretään näyttöön haluttu kuva
     *
     * @param g Grafiikka-olio
     * @param index Indeksi
     * @param x X-koord. johon kuva ladataan
     * @param y Y-koord. johon kuva ladataan
     */
    public void paint(Graphics g, int index, int x, int y) {
        BufferedImage tmpImg = sprites.get(index);
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
    public BufferedImage getSprite(int x, int y, int width, int height) {
        BufferedImage tmpImg = image.getSubimage(x, y, width, height);
        if (!sprites.contains(tmpImg)) {
            sprites.add(tmpImg);
        }
        return tmpImg;
    }

    /**
     * Metodi, jolla ladataan pikkukuva muistiin isommasta kuvasta
     *
     * @param x Kuvan x-koordinaatti [0,leveys]
     * @param y Kuvan y-koodrinaatti [0,korkeus]
     * @param wH Kuva-alueen leveys ja korkeus
     * @return BufferedImage
     */
    public BufferedImage getSprite(int x, int y, int widthHeight) {
        BufferedImage tmpImg = image.getSubimage(x, y, widthHeight, widthHeight);
        sprites.add(tmpImg);
        return tmpImg;
    }

}
