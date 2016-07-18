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

/**
 *
 * @author Aleksi Huotala
 */
public class PlayerInputHandler implements KeyListener,Tickable {

    private Player player;
    public boolean up = false, down = false, left = false, right = false;

    public PlayerInputHandler(Player player) {
        this.player = player;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        }
    }

    public void tick() {
        if (up || down || left || right) {
            if (up) {
                player.setDirection(Direction.UP);
                player.setWalkingState(true);
                player.goUp();
            }
            if (down) {
                player.setDirection(Direction.DOWN);
                player.setWalkingState(true);
                player.goDown();
            }
            if (left) {
                player.setDirection(Direction.LEFT);
                player.setWalkingState(true);
                player.goLeft();
            }
            if (right) {
                player.setDirection(Direction.RIGHT);
                player.setWalkingState(true);
                player.goRight();
            }
        } else {
            player.setWalkingState(false);
        }
    }
}
