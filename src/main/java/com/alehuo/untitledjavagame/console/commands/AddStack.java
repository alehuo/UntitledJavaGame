/*
 * Copyright 2017 alehuo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alehuo.untitledjavagame.console.commands;

import com.alehuo.untitledjavagame.Game;
import com.alehuo.untitledjavagame.console.ArgType;
import com.alehuo.untitledjavagame.console.Command;
import com.alehuo.untitledjavagame.item.Item;
import com.alehuo.untitledjavagame.item.ItemId;
import com.alehuo.untitledjavagame.item.ItemStack;

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
