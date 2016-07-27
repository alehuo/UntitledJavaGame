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
    private final String tileTypeBottom;
    private final String tileTypeMask;
    private final String tileTypeMask2;
    private final String tileTypeFringe1;
    private final String tileTypeFringe2;

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

}
