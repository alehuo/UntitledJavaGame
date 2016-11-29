package ahuotala.entities;

import static ahuotala.game.Game.spriteSheet;
import ahuotala.game.Renderer;
import ahuotala.game.Tickable;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Aleksi
 */
public class Npc implements Entity, Tickable {

    //x
    private int x = 0;
    //y
    private int y = 0;

    //NPC name
    private final String name;

    //Direction
    private Direction direction = Direction.DOWN;

    private int startX;
    private int startY;

    //Npc id
    private int npcId;

    //Can the NPC move around?
    private boolean canMove = true;

    //Interaction radius
    private int rY;
    private int rX;

    public Npc(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
        startX = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
        startY = y;
    }

    public void setMovementState(boolean state) {
        canMove = state;
    }

    public void renderNpc(Graphics g, Renderer r, Player player) {
//        spriteSheet.paint(g, "player_shadow", this.getX() + player.getOffsetX() - 8, this.getY() + player.getOffsetY() - 13);

        switch (this.getDirection()) {
            case UP:
                spriteSheet.paint(r, "player_up", this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                break;
            case DOWN:
                spriteSheet.paint(r, "player_down", this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                break;
            case LEFT:
                spriteSheet.paint(r, "player_left", this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                break;
            case RIGHT:
                spriteSheet.paint(r, "player_right", this.getX() + player.getOffsetX(), getY() + player.getOffsetY());
                break;
            default:
                break;
        }

    }

    public void drawInteractionBoundaries(Graphics g, int oX, int oY) {
        g.setColor(Color.blue);
        g.draw3DRect(x - rX + oX, y - rY + oY, 4 * rX, 4 * rY, false);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void tick() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
