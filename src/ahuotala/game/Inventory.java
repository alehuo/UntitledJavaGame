/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahuotala.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aleksi
 */
public class Inventory {

    private final int slots = 48;
    private final int cols = 6;
    private final int rows = 8;
    private final ItemStack[] inventory;

    public Inventory() {
        //Initialize the inventory
        inventory = new ItemStack[slots];
    }

    public void addStack(ItemStack stack) {
        for (int i = 0; i < inventory.length; i++) {
            //We have the same item in the stack and the stack is not full
            if (inventory[i] != null && inventory[i].equals(stack) && inventory[i].getAmount() < inventory[i].getMaxSize()) {
                //Check for the quantity and add to it if possible
                int inventoryAmount = inventory[i].getAmount();
                int inventoryMaxAmount = inventory[i].getMaxSize();
                //Amount if items in stack
                int stackAmount = stack.getAmount();
                //Available space in inventory (current stack)
                int availableSpace = inventoryMaxAmount - inventoryAmount;
                //Leftover; amount of items that don't fit
                int leftoverSpace = stackAmount - availableSpace;
                //If the leftoverSpace is greater than zero, we need to create a new itemStack.
                if (leftoverSpace > 0) {
                    System.out.println("Not all items fit the stack.");
                    System.out.println(leftoverSpace);
                    inventory[i].addToStack(availableSpace);
                    int stackId = this.addNewStack(stack);
                    if (stackId != -1) {
                        inventory[stackId].takeFromStack(availableSpace);
                    } else {
                        System.out.println("Inventory full; can't add");
                    }
                    break;
                } else {
                    System.out.println("All items fit the stack.");
                    //If the leftoverSpace is negative, all items fit the stack.
                    inventory[i].addToStack(stackAmount);
                    break;
                }
            } else if (inventory[i] == null) {
                //If the stack is empty
                inventory[i] = stack;
                break;
            }
        }
    }

    public int addNewStack(ItemStack stack) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = stack;
                return i;
            }
        }
        return -1;
    }

    public int addToStack(int stackId, ItemStack stack) {
        return -1;
    }

    public void removeStack(int stackId) {
        inventory[stackId] = null;
    }

    public ItemStack getItemStack(int stackId) {
        if (inventory.length - 1 > stackId) {
            return inventory[stackId];
        }
        return inventory[-1];
    }

    public void renderInventory(Graphics g) {
        //Base image
        BufferedImage inventoryImage = Game.spriteSheet.getInventory();
        int x = (int) Math.floor((Game.WINDOW_WIDTH - inventoryImage.getWidth()) / 2);
        int y = (int) Math.floor((Game.WINDOW_HEIGHT - inventoryImage.getHeight()) / 2);
        g.drawImage(inventoryImage, x, y, null);
        //Item stacks
        //Each inventory slot is 72x72 pixels
        //First inv slot has an x-offset of 4 and an y-offset of 4.
        //X-offset of 80 afterwards.
        //1: (4,4) , 2: (84,4), 3: (164,4), ...
        //9: (4,84), 10: (84,84), 11: (164,84), ...
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                g.drawImage(Game.spriteSheet.getSprite("tmp_inv", 208, 16, 36), x + 4 + 80 * row, y + 4 + 80 * col, 72, 72, null);
            }
        }

    }

    @Override
    public String toString() {
        String tmp = "";
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                tmp += i + ": " + inventory[i].toString() + "\r\n";
            } else {
                tmp += i + ": empty\r\n";
            }

        }
        return tmp;
    }
}
