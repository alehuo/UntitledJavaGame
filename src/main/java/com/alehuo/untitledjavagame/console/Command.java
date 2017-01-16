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
package com.alehuo.untitledjavagame.console;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command class
 *
 * @author alehuo
 */
public abstract class Command {

    /**
     *
     */
    protected String name;

    /**
     * Valid argument count
     */
    protected int argCount;

    /**
     * List of valid argument types.
     */
    protected ArgType[] validArgList;

    protected static final Logger LOG = Logger.getLogger(Command.class.getName());

    /**
     *
     * @param name
     * @param args
     */
    public Command(String name, ArgType... args) {
        LOG.setLevel(Level.SEVERE);
        this.name = name;
        this.argCount = args.length;
        validArgList = args;
    }

    /**
     * Validate arguments
     *
     * @param args Arguments
     * @return Arguments if the validation is successfull
     * @throws IllegalArgumentException
     */
    public Object[] validateArgs(Object... args) throws IllegalArgumentException {

        if (args.length != argCount) {
            //Too less or too much argument
            throw new IllegalArgumentException("Invalid arguments: required " + argCount + ", had " + args.length);
        } else {
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                ArgType at = validArgList[i];
                if (at == ArgType.INTEGER) {
                    if (!(arg.getClass().equals(new Integer(42).getClass()))) {
                        //Wrong argument type
                        throw new IllegalArgumentException("Invalid argument type: required type " + validArgList[i] + ", was " + arg.getClass());
                    }
                } else if (at == ArgType.STRING) {
                    if (!(arg.getClass().equals(new String("String").getClass()))) {
                        //Wrong argument type
                        throw new IllegalArgumentException("Invalid argument type: required type " + validArgList[i] + ", was " + arg.getClass());
                    }
                }
            }
        }
        return args;
    }

    /**
     * Execute command
     *
     * @param args Command arguments
     * @return Message
     */
    public abstract String execute(String[] args);

    /**
     * Parse commands into an object array
     *
     * @param cmdArgs
     * @return
     * @throws NumberFormatException
     */
    public Object[] parseCommands(String[] cmdArgs) throws NumberFormatException, IllegalArgumentException {

        if (cmdArgs.length != argCount) {
            //Too many or not enough argument
            throw new IllegalArgumentException("Too many or not enough arguments: required " + argCount + ", had " + cmdArgs.length);
        }

        Object[] parsedCommands = new Object[cmdArgs.length];
        for (int i = 0; i < cmdArgs.length; i++) {

            Object tmpObj = null;

            if (validArgList[i] == ArgType.INTEGER) {
                LOG.info("Parsing param as an integer.");
                tmpObj = Integer.parseInt(cmdArgs[i]);
            } else if (validArgList[i] == ArgType.STRING) {
                LOG.info("Parsing param as a string.");
                tmpObj = cmdArgs[i];
            }

            parsedCommands[i] = tmpObj;
        }

//        for (int i = 0; i < parsedCommands.length; i++) {
//            Object arg = parsedCommands[i];
//            ArgType at = validArgList[i];
//            if (at == ArgType.INTEGER) {
//                if (!(arg.getClass().equals(new Integer(42).getClass()))) {
//                    //Wrong argument type
//                    throw new IllegalArgumentException("Invalid argument type: required type " + validArgList[i] + ", but was " + arg.getClass());
//                }
//            } else if (at == ArgType.STRING) {
//                if (!(arg.getClass().equals(new String("String").getClass()))) {
//                    //Wrong argument type
//                    throw new IllegalArgumentException("Invalid argument type: required type " + validArgList[i] + ", but was " + arg.getClass());
//                }
//            }
//        }

        return parsedCommands;

    }

    public String getName() {
        return name;
    }

    public int getArgCount() {
        return argCount;
    }

    public ArgType[] getValidArgList() {
        return validArgList;
    }
    
    

}
