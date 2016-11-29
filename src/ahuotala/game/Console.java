package ahuotala.game;

import java.util.Scanner;

/**
 *
 * @author Aleksi Huotala
 */
public class Console implements Runnable {

    private final Scanner sc;

    /**
     * Console
     */
    public Console() {
        sc = new Scanner(System.in);
    }

    @Override
    public void run() {
        System.out.println("Console is running...");
        while (true) {
            System.out.print("> ");
            String command = sc.nextLine();
            if (command.isEmpty()) {
                continue;
            }
            if (command.startsWith("addstack")) {
                String[] cmdArray = command.split(" ");
                if (cmdArray.length != 3) {
                    System.out.println("Invalid arguments for command 'addstack'");
                } else {
                    ItemId itemId = ItemId.valueOf(cmdArray[1]);
                    int amount = Integer.parseInt(cmdArray[2]);

                    //Add stack of items
                    Game.inventory.addStack(new ItemStack(new Item(itemId), amount));
                    System.out.println("Added a new stack of item " + itemId + ", amount: " + amount);
                }
            } else if (command.startsWith("removestack")) {

                String[] cmdArray = command.split(" ");
                if (cmdArray.length == 2) {
                    int stackId = Integer.parseInt(cmdArray[1]);
                    Game.inventory.removeStack(stackId);
                    System.out.println("Removed itemStack from inventory slot " + stackId);
                } else {
                    System.out.println("Invalid arguments for command 'removestack'");
                }

            } else if (command.startsWith("sethealth")) {
                String[] cmdArray = command.split(" ");
                if (cmdArray.length == 2) {
                    int health = Integer.parseInt(cmdArray[1]);
                    if (health >= 0 && health <= Game.player.getMaxHealth()) {
                        Game.player.setHealth(health);
                    } else {
                        System.out.println("Health must be between 0 and " + Game.player.getMaxHealth());
                    }
                } else {
                    System.out.println("Invalid arguments for command 'sethealth'");
                }
            } else if (command.startsWith("setxp")) {
                String[] cmdArray = command.split(" ");
                if (cmdArray.length == 2) {
                    double xp = Double.parseDouble(cmdArray[1]);
                    if (xp >= 0) {
                        Game.player.setXp(xp);
                    } else {
                        System.out.println("Xp must be positive!");
                    }
                } else {
                    System.out.println("Invalid arguments for command 'sethealth'");
                }
            } else if (command.startsWith("sethsb")) {
                String[] cmdArray = command.split(" ");
                if (cmdArray.length == 4) {
                    try {
                        float h = Float.parseFloat(cmdArray[1]);
                        float s = Float.parseFloat(cmdArray[2]);
                        float b = Float.parseFloat(cmdArray[3]);

                    } catch (Exception e) {

                    }

                }
            } else if (command.equals("listitems")) {
                System.out.println("List of items registered ingame: ");
                for (ItemId itemId : Game.itemRegistry.getItems().keySet()) {
                    System.out.println(itemId);
                }
            } else if (command.equals("help") || command.equals("?")) {
                System.out.println("### LIST OF COMMANDS ###");
                System.out.println("sethealth [health]");
                System.out.println("setxp [xp]");
                System.out.println("addstack [itemname] [amount]");
                System.out.println("removestack [stackid]");
                System.out.println("listitems");
            } else {
                System.out.println("Command not found");
            }

        }
    }

}
