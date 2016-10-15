package ahuotala.graphics;

import ahuotala.game.Game;
import ahuotala.game.ItemId;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
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

    private String inventoryPath = "inventory.png";
    private BufferedImage image;
    private int[] imagePixels;
    private BufferedImage inventoryImage;
    private int[] inventoryImagePixels;
    private boolean imageLoaded = false;
    private boolean inventoryLoaded = false;
    private HashMap<String, BufferedImage> sprites;

    public SpriteSheet(String spriteSheetPath) {
        //Alustetaan hajautustaulu
        sprites = new HashMap<>();
        //Yritetään ladata kuva
        try {
            /**
             * Spritesheet
             */
            BufferedImage baseImage = ImageIO.read(SpriteSheet.class.getResourceAsStream(spriteSheetPath));
            image = convert(baseImage);
            imageLoaded = true;
            imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

            /**
             * Inventory image
             */
            BufferedImage baseInventoryImage = ImageIO.read(SpriteSheet.class.getResourceAsStream(inventoryPath));
            inventoryImage = convert(baseInventoryImage);
            inventoryLoaded = true;
            inventoryImagePixels = ((DataBufferInt) inventoryImage.getRaster().getDataBuffer()).getData();

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
        return null;
    }

    /**
     * Returns the inventory image pixel array
     *
     * @return BufferedImage Inventory image
     */
    public int[] getInventoryPixels() {
        if (inventoryLoaded) {
            return inventoryImagePixels;
        }
        return null;
    }

    public BufferedImage getItemIcon(ItemId itemId) {
        if (sprites.containsKey(itemId + "")) {
            return sprites.get(itemId + "");
        }
        return null;
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

    public static BufferedImage convert(BufferedImage image) {
        BufferedImage tmpImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = tmpImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return tmpImage;
    }

}
