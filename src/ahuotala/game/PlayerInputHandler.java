package ahuotala.game;

import ahuotala.entities.Direction;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import ahuotala.entities.Player;
import ahuotala.map.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Aleksi Huotala
 */
public class PlayerInputHandler implements KeyListener, Tickable {

    private final Player player;
    private final Map map;
    private final Game game;
    private String prompt = "";
    public boolean up = false, down = false, left = false, right = false;

    public PlayerInputHandler(Player player, Map map, Game game) {
        this.player = player;
        this.map = map;
        this.game = game;
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
            case KeyEvent.VK_U:
                if (Game.DEBUG_PLAYER) {
                    player.setXp(player.getXp() + 10.5);
                }
                break;
            case KeyEvent.VK_Y:
                if (Game.DEBUG_PLAYER && player.getXp() >= 10.5) {
                    player.setXp(player.getXp() - 10.5);
                }
                break;
            case KeyEvent.VK_F1:
                Game.DEBUG = !Game.DEBUG;
                break;
            case KeyEvent.VK_F2:
                Game.DEBUG_PLAYER = !Game.DEBUG_PLAYER;
                break;
            case KeyEvent.VK_I:
                Game.SHOW_INVENTORY = !Game.SHOW_INVENTORY;
                if (Game.DEBUG) {
                    System.out.println(Game.inventory);
                }
                break;
            case KeyEvent.VK_ESCAPE:
                if (Game.menuState == MenuState.PAUSED) {
                    Game.menuState = MenuState.NONE;
                } else if (Game.menuState == MenuState.NONE) {
                    Game.menuState = MenuState.PAUSED;
                }
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
        int yOffset = 4;
        int yOffsetBottom = 24;
        int xOffset = 8;
        if ((up || down || left || right) && !Game.SHOW_INVENTORY) {
            if (up && player.getY() > map.getMinY() + yOffset) {
                player.setDirection(Direction.UP);
                player.setWalkingState(true);
                player.goUp();
            }
            if (down && player.getY() < map.getMaxY() - yOffsetBottom) {
                player.setDirection(Direction.DOWN);
                player.setWalkingState(true);
                player.goDown();
            }
            if (left && player.getX() > map.getMinX() + xOffset) {
                player.setDirection(Direction.LEFT);
                player.setWalkingState(true);
                player.goLeft();
            }
            if (right && player.getX() < map.getMaxX() - xOffset) {
                player.setDirection(Direction.RIGHT);
                player.setWalkingState(true);
                player.goRight();
            }
        } else {
            player.setWalkingState(false);
        }
    }
}
