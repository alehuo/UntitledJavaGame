/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Aleksi Huotala
 */
public class Player implements Entity {

    private int x;
    private int y;
    private int step = 2;
    private String name;
    private Direction direction = Direction.DOWN;
    private boolean walking = false;

    public Player(String name) {
        this.name = name;
        x = 0;
        y = 0;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void goUp() {
        y -= step;
    }

    public void goDown() {
        y += step;
    }

    public void goLeft() {
        x -= step;
    }

    public void goRight() {
        x += step;
    }

    public void setX(int x) {
        this.x = x;
    }

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
