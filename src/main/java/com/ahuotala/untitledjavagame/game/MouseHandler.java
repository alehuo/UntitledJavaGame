package com.ahuotala.untitledjavagame.game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 *
 * @author Aleksi Huotala
 */
public class MouseHandler extends MouseAdapter {

    private final Inventory i;

    public static int x = 0;
    public static int y = 0;
    public static boolean mouseClicked = false;
    public static boolean pressed = false;
    public static int slot = -1;

    public MouseHandler(Inventory i) {
        this.i = i;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        if (Game.SHOW_INVENTORY) {
//            mouseClicked = !mouseClicked;
//        }
    }

    public static void setClickedSlot(int slot) {
        MouseHandler.slot = slot;
    }

    public static int getClickedSlot() {
        return slot;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (Game.SHOW_INVENTORY || Game.isInMenu) {
            mouseClicked = !mouseClicked;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        e.getScrollAmount();
    }

}
