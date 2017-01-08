/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ahuotala.untitledjavagame.console.commands;

import com.ahuotala.untitledjavagame.Game;
import com.ahuotala.untitledjavagame.console.ArgType;
import com.ahuotala.untitledjavagame.console.Command;

/**
 * removestack [stackId]
 *
 * @author alehuo
 */
public class RemoveStack extends Command {

    public RemoveStack() {
        //name, (integer) stackId
        super("removestack", ArgType.INTEGER);
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
                int stackId = Integer.parseInt((String) commandArgs[1]);
                Game.INVENTORY.removeStack(stackId);
                return "Removed itemStack from inventory slot " + stackId;
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
