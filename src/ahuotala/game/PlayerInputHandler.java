/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;

import ahuotala.entities.Direction;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import ahuotala.entities.Player;
import ahuotala.map.Map;

/**
 *
 * @author Aleksi Huotala
 */
public class PlayerInputHandler implements KeyListener, Tickable {

    private final Player player;
    private final Map map;
    public boolean up = false, down = false, left = false, right = false;

    public PlayerInputHandler(Player player, Map map) {
        this.player = player;
        this.map = map;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                down = true;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                right = true;
                break;
            case KeyEvent.VK_J:
                if (Game.DEBUG_PLAYER) {
                    player.increaseHealth(5);
                }
                break;
            case KeyEvent.VK_H:
                if (Game.DEBUG_PLAYER) {
                    player.damagePlayer(5);
                }
                break;
            case KeyEvent.VK_F1:
                Game.DEBUG = !Game.DEBUG;
                break;
            case KeyEvent.VK_F2:
                Game.DEBUG_PLAYER = !Game.DEBUG_PLAYER;
                break;
            case KeyEvent.VK_E:
                Game.SHOW_INVENTORY = !Game.SHOW_INVENTORY;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                right = false;
                break;
            default:
                break;
        }
    }

    @Override
    public void tick() {
        int yOffset = 2;
        int xOffset = 8;
        if ((up || down || left || right) && !Game.SHOW_INVENTORY) {
            if (up && player.getY() > map.getMinY() + yOffset && player.getCollisionDirection() != Direction.UP) {
                player.setDirection(Direction.UP);
                player.setWalkingState(true);
                player.goUp();
            }
            if (down && player.getY() < map.getMaxY() - yOffset && player.getCollisionDirection() != Direction.DOWN) {
                player.setDirection(Direction.DOWN);
                player.setWalkingState(true);
                player.goDown();
            }
            if (left && player.getX() > map.getMinX() + xOffset && player.getCollisionDirection() != Direction.LEFT) {
                player.setDirection(Direction.LEFT);
                player.setWalkingState(true);
                player.goLeft();
            }
            if (right && player.getX() < map.getMaxX() - xOffset && player.getCollisionDirection() != Direction.RIGHT) {
                player.setDirection(Direction.RIGHT);
                player.setWalkingState(true);
                player.goRight();
            }
        } else {
            player.setWalkingState(false);
        }
    }
}
