/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aleksi
 */
public class Inventory {

    private final int slots = 48;
    private final int perRow = 8;
    private List<ItemStack> inventory;

    public Inventory() {
        inventory = new ArrayList<>();
    }

    public void addStack(ItemStack stack) {

    }

    public void removeStack(int stackId) {

    }

    public void getItemStack(int stackId) {

    }

    public void renderInventory(Graphics g) {

    }
}
