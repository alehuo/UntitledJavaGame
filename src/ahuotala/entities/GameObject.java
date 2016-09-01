package ahuotala.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Aleksi Huotala
 */
public class GameObject {
    
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int radiusX = 5;
    private final int radiusY = 5;
    private final BufferedImage sprite;
    private final Rectangle bounds;
    
    public GameObject(int x, int y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        width = sprite.getWidth();
        height = sprite.getHeight();
        bounds = new Rectangle(x, y, width, height);
    }
    
    public boolean collidesWithPlayer(Player player) {
//        if (player.getX() == x - radiusX && player.getY() >= y - radiusY && player.getY() <= y + sprite.getHeight() + radiusY) {
//            //Left side
//            player.setCollisionDirection(Direction.RIGHT);
//            return true;
//        } else if (player.getX() == x + sprite.getWidth() - radiusX && player.getY() >= y - radiusY && player.getY() <= y + sprite.getHeight() + radiusY) {
//            //Right side
//            player.setCollisionDirection(Direction.LEFT);
//            return true;
//        } else if (player.getX() >= x - radiusX && player.getX() <= x + sprite.getWidth() + radiusX && player.getY() == y) {
//            //Bottom side
//            player.setCollisionDirection(Direction.DOWN);
//            return true;
//        } else if (player.getX() >= x - radiusX && player.getX() <= x + sprite.getWidth() + radiusX && player.getY() == y + radiusY + sprite.getHeight()) {
//            //Top side
//            player.setCollisionDirection(Direction.UP);
//            return true;
//        }
//        player.setCollisionDirection(Direction.NAN);
//        return false;
        if (bounds.intersects(player.getBounds())) {
            if (null != player.getDirection()) {
                switch (player.getDirection()) {
                    case DOWN:
                        player.setCollisionDirection(Direction.DOWN);
                        break;
                    case UP:
                        player.setCollisionDirection(Direction.UP);
                        break;
                    case LEFT:
                        player.setCollisionDirection(Direction.LEFT);
                        break;
                    case RIGHT:
                        player.setCollisionDirection(Direction.RIGHT);
                        break;
                    default:
                        break;
                }
            }
            return true;
        }
        player.setCollisionDirection(Direction.NAN);
        return false;
    }
    
    public void render(Graphics g, Player p) {
        g.drawImage(sprite, x + p.getOffsetX(), y + p.getOffsetY(), null);
    }
    
    public void drawBoundaries(Graphics g, Player p) {
        g.setColor(Color.MAGENTA);
        g.draw3DRect(x + p.getOffsetX() - radiusX, y + p.getOffsetY() - radiusY, sprite.getWidth() + 2 * radiusX, sprite.getHeight() + 2 * radiusY, false);
    }
}
