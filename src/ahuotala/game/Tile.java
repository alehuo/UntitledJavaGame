/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;

/**
 *
 * @author Aleksi
 */
public class Tile {

    private int x;
    private int y;
    private int z;
    private String type;
    private boolean animated;

    public Tile(int x, int y, int z, String type, boolean animated) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
        this.animated = animated;
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

    public String getType() {
        return type;
    }

    public boolean isAnimated() {
        return animated;
    }

}
