/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.console.commands;

import com.ahuotala.untitledjavagame.Game;
import com.ahuotala.untitledjavagame.console.ArgType;
import com.ahuotala.untitledjavagame.console.Command;
import com.ahuotala.untitledjavagame.item.Item;
import com.ahuotala.untitledjavagame.item.ItemId;
import com.ahuotala.untitledjavagame.item.ItemStack;

/**
 * addstack [itemId] [amount]
 *
 * @author alehuo
 */
public class AddStack extends Command {

    public AddStack() {
        //name, (integer) itemId, (integer) amount
        super("addStack", ArgType.STRING, ArgType.INTEGER);
    }

    /**
     * Execute a command
     *
     * @param args Arguments
     * @return
     */
    @Override
    public String execute(String[] args) {
        //Parse commands
        try {
            Object[] commandArgs = parseCommands(args);

            if (commandArgs != null) {
                //Code here is the only thing (in addition with command name & param types) required to change between different commands
                ItemId itemId = ItemId.valueOf((String) commandArgs[0]);
                int amount = (int) commandArgs[1];
                //Add stack of items
                Game.INVENTORY.addStack(new ItemStack(new Item(itemId), amount));
                return "Added a new stack of item " + itemId + ", amount: " + amount;
            } else {
                return "Invalid arguments for command " + getName();
            }
        } catch (NumberFormatException e) {
            return "NumberFormatException " + e.getMessage();
        } catch (IllegalArgumentException e) {
            return "IllegalArgumentException: " + e.getMessage();
        }
    }

}
