/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.entities;

import ahuotala.game.Game;
import ahuotala.game.Tickable;

/**
 *
 * @author Aleksi Huotala
 */
public class Player implements Entity, Tickable {

    private int x;
    private int y;

    public static int realX;
    public static int realY;

    //Base stepping speed
    private int step = 1;

    private final String name;
    private int health = 120;
    private int maxHealth = 120;

    private Direction direction = Direction.DOWN;

    private boolean walking = false;
    private boolean jumping = false;
    private boolean canJump = true;
    private boolean swimming = false;

    private final int radiusX;
    private final int radiusY;

    private final int cY;
    private final int cX;

    public int offsetX = Game.CENTERX;
    public int offsetY = Game.CENTERY;

    public String currentTile = "";

    private int jumpTicks = 40;

    public Player(String name) {
        this.name = name;
        //Center Y
        this.cY = Game.CENTERY;
        //Center X
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

    public void damagePlayer(int health) {
        if (health >= 0) {
            this.health -= health;
        }
    }

    public void increaseHealth(int health) {
        if (this.health + health <= maxHealth) {
            this.health += health;
        } else {
            health = maxHealth;
        }
    }

    public int getHealth() {
        return health;
    }

    public void goUp() {
        y -= step;
        offsetY += step;
//        if (y - step >= cY - radiusY) {
//            y -= step;
//            realY -= step;
//        } else {
//            //Offset the map here
//            offsetY += step;
//            realY -= step;
//            System.out.println("Cannot go up; Moving the map instead");
//        }
    }

    public void goDown() {
        y += step;
        offsetY -= step;
//        if (y + step <= cY + radiusY) {
//            y += step;
//            realY += step;
//        } else {
//            //Offset the map here
//            offsetY -= step;
//            realY += step;
////            System.out.println("Cannot go down; Moving the map instead");
//        }
    }

    public void goLeft() {
        x -= step;
        offsetX += step;
//        if (x - step >= cX - radiusX) {
//            x -= step;
//            realX -= step;
//        } else {
//            //Offset the map here
//            offsetX += step;
//            realX -= step;
////            System.out.println("Cannot go left; Moving the map instead");
//        }
    }

    public void goRight() {
        x += step;
        offsetX -= step;
//        if (x + step <= cX + radiusX) {
//            x += step;
//            realX += step;
//        } else {
//            //Offset the map here
//            offsetX -= step;
//            realX += step;
////            System.out.println("Cannot go right; Moving the map instead");
//        }
    }

    @Override
    public void setX(int x) {
        offsetX = cX - x;
        this.x = x;
    }

    @Override
    public void setY(int y) {
        offsetY = cY - y;
        this.y = y;
    }

    public void setCurrentTile(String tile) {
        currentTile = tile;
    }

    public String getCurrentTile(String tile) {
        return currentTile;
    }

    public void setStep(int step) {
        this.step = step;
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

    public void SetJumpingState(boolean jumping) {
        this.jumping = jumping;
    }

    public void setSwimmingState(boolean swimming) {
        this.swimming = swimming;
    }

    public boolean isWalking() {
        return walking;
    }

    public boolean isSwimming() {
        return swimming;
    }

    public boolean canJump() {
        return canJump;
    }

    @Override
    public String toString() {
        return this.getX() + "," + this.getY();
    }

    public void jump() {
        jumping = true;
    }

    @Override
    public void tick() {
        //Slow the player down if we are walking on a sand
        switch (currentTile) {
            case "sand":
                canJump = true;
                swimming = false;
                step = 2;
                break;
            case "water_ani":
                swimming = true;
                canJump = false;
                jumping = false;
                step = 1;
                break;
            case "lava_ani":
                swimming = true;
                canJump = false;
                jumping = false;
                step = 1;
                break;
            default:
                canJump = true;
                swimming = false;
                step = 3;
                break;
        }
        if (jumping && canJump) {
            if (jumpTicks == 40) {
                realY -= 20;
            }
            if (jumpTicks > 30) {
                realY += 2;
                jumpTicks--;
            }
            if (jumpTicks == 30) {
                jumpTicks = 40;
                jumping = false;
            }
        }
    }

}
