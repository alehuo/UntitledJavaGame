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
 * sethealth [health]
 *
 * @author alehuo
 */
public class SetHealth extends Command {

    public SetHealth() {
        //name, (integer) health
        super("sethealth", ArgType.INTEGER);
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
                int health = Integer.parseInt((String) commandArgs[1]);
                if (health >= 0 && health <= Game.PLAYER.getMaxHealth()) {
                    Game.PLAYER.setHealth(health);
                    return "Set player health to " + health;
                } else {
                    return "Health must be between 0 and " + Game.PLAYER.getMaxHealth();
                }
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
