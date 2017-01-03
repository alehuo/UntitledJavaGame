package com.ahuotala.untitledjavagame.map;

import com.ahuotala.untitledjavagame.entities.Player;
import java.awt.Rectangle;

/**
 * Tile class
 *
 * @author Aleksi Huotala
 */
public class Tile {

    private final int x;
    private final int y;
    private String tileTypeBottom;
    private String tileTypeMask;
    private String tileTypeMask2;
    private String tileTypeFringe1;
    private String tileTypeFringe2;
    private boolean blocked = false;
    private final Rectangle bounds;

    /**
     *
     * @param x
     * @param y
     * @param tileTypeBottom
     * @param tileTypeMask
     * @param tileTypeMask2
     * @param tileTypeFringe1
     * @param tileTypeFringe2
     * @param blocked
     */
    public Tile(int x, int y, String tileTypeBottom, String tileTypeMask, String tileTypeMask2, String tileTypeFringe1, String tileTypeFringe2, boolean blocked) {
        this.x = x;
        this.y = y;
        this.tileTypeBottom = tileTypeBottom;
        this.tileTypeMask = tileTypeMask;
        this.tileTypeMask2 = tileTypeMask2;
        this.tileTypeFringe1 = tileTypeFringe1;
        this.tileTypeFringe2 = tileTypeFringe2;
        this.blocked = blocked;
        bounds = new Rectangle(x, y, 32, 32);
    }

    /**
     *
     * @param x
     * @param y
     * @param tileTypeBottom
     * @param tileTypeMask
     * @param tileTypeMask2
     * @param tileTypeFringe1
     * @param tileTypeFringe2
     */
    public Tile(int x, int y, String tileTypeBottom, String tileTypeMask, String tileTypeMask2, String tileTypeFringe1, String tileTypeFringe2) {
        this.x = x;
        this.y = y;
        this.tileTypeBottom = tileTypeBottom;
        this.tileTypeMask = tileTypeMask;
        this.tileTypeMask2 = tileTypeMask2;
        this.tileTypeFringe1 = tileTypeFringe1;
        this.tileTypeFringe2 = tileTypeFringe2;
        bounds = new Rectangle(x, y, 32, 32);
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean collidesWithPlayer(Player p) {
        if (!blocked) {
            return false;
        }
        if (bounds.intersects(p.getBounds())) {
            p.setX(p.getLastX());
            p.setY(p.getLastY());
            return true;
        }
        p.setLastX(p.getX());
        p.setLastY(p.getY());
        return false;
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return
     */
    public String getTypeBottom() {
        return tileTypeBottom;
    }

    /**
     *
     * @return
     */
    public String getTypeMask() {
        return tileTypeMask;
    }

    /**
     *
     * @return
     */
    public String getTypeMask2() {
        return tileTypeMask2;
    }

    /**
     *
     * @return
     */
    public String getTypeFringe1() {
        return tileTypeFringe1;
    }

    /**
     *
     * @return
     */
    public String getTypeFringe2() {
        return tileTypeFringe1;
    }

    /**
     *
     * @param type
     */
    public void setTypeBottom(String type) {
        tileTypeBottom = type;
    }

    /**
     *
     * @param type
     */
    public void setTypeMask(String type) {
        tileTypeMask = type;
    }

    /**
     *
     * @param type
     */
    public void setTypeMask2(String type) {
        tileTypeMask2 = type;
    }

    /**
     *
     * @param type
     */
    public void setTypeFringe1(String type) {
        tileTypeFringe1 = type;
    }

    /**
     *
     * @param type
     */
    public void setTypeFringe2(String type) {
        tileTypeFringe2 = type;
    }

    /**
     *
     * @return
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     *
     * @param blocked
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
    
    /**
     *
     * @return
     */
    public Rectangle getBounds(){
        return bounds;
    }

    @Override
    public String toString() {
        //x,y,tile_type_bottom,tile_type_mask_1,tile_type_mask2,tile_type_fringe_1,tile_type_fringe_2,blocked
        return x + "," + y + "," + tileTypeBottom + "," + tileTypeMask + "," + tileTypeMask2 + "," + tileTypeFringe1 + "," + tileTypeFringe2 + "," + ((blocked) ? 1 : 0) + "";
    }

}
