/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.entities;

import ahuotala.game.Game;

/**
 *
 * @author Aleksi Huotala
 */
public class Player implements Entity {

    private int x;
    private int y;
    public static int realX;
    public static int realY;
    private final int step = 1;
    private final String name;
    private Direction direction = Direction.DOWN;
    private boolean walking = false;
    private final int radiusX;
    private final int radiusY;
    private final int cY;
    private final int cX;
    public static int offsetX = 0;
    public static int offsetY = 0;

    public Player(String name) {
        this.name = name;
        this.cY = Game.CENTERY;
        this.cX = Game.CENTERX;
        realX = cX;
        realY = cY;
        radiusX = (int) Math.floor(0.2 * cX);
        radiusY = (int) Math.floor(0.2 * cY);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public int getRealX() {
        return realX;
    }

    public int getRealY() {
        return realY;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void goUp() {
        if (y - step >= cY - radiusY) {
            y -= step;
            realY -= step;
        } else {
            //Offset the map here
            offsetY += step;
            realY -= step;
//            System.out.println("Cannot go up; Moving the map instead");
        }
    }

    public void goDown() {
        if (y + step <= cY + radiusY) {
            y += step;
            realY += step;
        } else {
            //Offset the map here
            offsetY -= step;
            realY += step;
//            System.out.println("Cannot go down; Moving the map instead");
        }
    }

    public void goLeft() {
        if (x - step >= cX - radiusX) {
            x -= step;
            realX -= step;
        } else {
            //Offset the map here
            offsetX += step;
            realX -= step;
//            System.out.println("Cannot go left; Moving the map instead");
        }
    }

    public void goRight() {
        if (x + step <= cX + radiusX) {
            x += step;
            realX += step;
        } else {
            //Offset the map here
            offsetX -= step;
            realX += step;
//            System.out.println("Cannot go right; Moving the map instead");
        }
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean getWalkingState() {
        return walking;
    }

    public void setWalkingState(boolean walking) {
        this.walking = walking;
    }

    @Override
    public String toString() {
        return this.getX() + "," + this.getY();
    }

}
