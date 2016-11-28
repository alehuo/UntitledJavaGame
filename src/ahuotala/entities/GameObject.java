package ahuotala.entities;

import ahuotala.game.Renderer;
import ahuotala.graphics.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Aleksi Huotala
 */
public class GameObject {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Sprite sprite;
    private final Rectangle bounds;

    public GameObject(int x, int y, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        width = sprite.getWidth();
        height = sprite.getHeight();
        bounds = new Rectangle(x, y, width, height);
    }

    public boolean collidesWithPlayer(Player player) {
        if (bounds.intersects(player.getBounds())) {
            player.setX(player.getLastX());
            player.setY(player.getLastY());
            return true;
        }
        player.setLastX(player.getX());
        player.setLastY(player.getY());
        return false;
    }

    public void render(Renderer r, Player p) {
        r.renderSprite(sprite, x + p.getOffsetX(), y + p.getOffsetY());
    }

    public void drawBoundaries(Graphics g, Player p) {
        g.setColor(Color.MAGENTA);
        g.draw3DRect(x + p.getOffsetX(), y + p.getOffsetY(), width, height, false);
    }
}
