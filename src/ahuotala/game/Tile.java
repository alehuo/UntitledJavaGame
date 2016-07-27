/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;

import java.util.ArrayList;

/**
 * Tile class
 *
 * @author Aleksi Huotala
 */
public class Tile {

    private int x;
    private int y;
    private int z;
    private boolean animated;
    private String tileTypeBottom;
    private String tileTypeMask;
    private String tileTypeMask2;
    private String tileTypeFringe1;
    private String tileTypeFringe2;

    public Tile(int x, int y, int z, String tileTypeBottom, String tileTypeMask, String tileTypeMask2, String tileTypeFringe1, String tileTypeFringe2, boolean animated) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.tileTypeBottom = tileTypeBottom;
        this.tileTypeMask = tileTypeMask;
        this.tileTypeMask2 = tileTypeMask2;
        this.tileTypeFringe1 = tileTypeFringe1;
        this.tileTypeFringe2 = tileTypeFringe2;
        this.animated = animated;
    }

    public Tile(int x, int y, int z, String tileTypeBottom, String tileTypeMask, String tileTypeMask2, String tileTypeFringe1, String tileTypeFringe2) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.tileTypeBottom = tileTypeBottom;
        this.tileTypeMask = tileTypeMask;
        this.tileTypeMask2 = tileTypeMask2;
        this.tileTypeFringe1 = tileTypeFringe1;
        this.tileTypeFringe2 = tileTypeFringe2;
        animated = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
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

    public boolean isAnimated() {
        return animated;
    }

}
