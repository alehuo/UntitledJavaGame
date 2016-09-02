/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;

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

    public Tile(int x, int y, String tileTypeBottom, String tileTypeMask, String tileTypeMask2, String tileTypeFringe1, String tileTypeFringe2, boolean blocked) {
        this.x = x;
        this.y = y;
        this.tileTypeBottom = tileTypeBottom;
        this.tileTypeMask = tileTypeMask;
        this.tileTypeMask2 = tileTypeMask2;
        this.tileTypeFringe1 = tileTypeFringe1;
        this.tileTypeFringe2 = tileTypeFringe2;
        this.blocked = blocked;
    }

    public Tile(int x, int y, String tileTypeBottom, String tileTypeMask, String tileTypeMask2, String tileTypeFringe1, String tileTypeFringe2) {
        this.x = x;
        this.y = y;
        this.tileTypeBottom = tileTypeBottom;
        this.tileTypeMask = tileTypeMask;
        this.tileTypeMask2 = tileTypeMask2;
        this.tileTypeFringe1 = tileTypeFringe1;
        this.tileTypeFringe2 = tileTypeFringe2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getTypeBottom() {
        return tileTypeBottom;
    }

    public String getTypeMask() {
        return tileTypeMask;
    }

    public String getTypeMask2() {
        return tileTypeMask2;
    }

    public String getTypeFringe1() {
        return tileTypeFringe1;
    }

    public String getTypeFringe2() {
        return tileTypeFringe1;
    }

    public void setTypeBottom(String type) {
        tileTypeBottom = type;
    }

    public void setTypeMask(String type) {
        tileTypeMask = type;
    }

    public void setTypeMask2(String type) {
        tileTypeMask2 = type;
    }

    public void setTypeFringe1(String type) {
        tileTypeFringe1 = type;
    }

    public void setTypeFringe2(String type) {
        tileTypeFringe2 = type;
    }

    public boolean isBlocked() {
        return blocked;
    }

    @Override
    public String toString() {
        //x,y,tile_type_bottom,tile_type_mask_1,tile_type_mask2,tile_type_fringe_1,tile_type_fringe_2,blocked
        return x + "," + y + "," + tileTypeBottom + "," + tileTypeMask + "," + tileTypeMask2 + "," + tileTypeFringe1 + "," + tileTypeFringe2 + "," + ((blocked) ? 1 : 0) + "";
    }

}
