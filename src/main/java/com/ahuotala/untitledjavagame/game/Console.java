package com.ahuotala.untitledjavagame.game;

import com.ahuotala.untitledjavagame.game.item.Item;
import com.ahuotala.untitledjavagame.game.item.ItemStack;
import com.ahuotala.untitledjavagame.game.item.ItemId;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Aleksi Huotala
 */
public class Console extends JFrame {

//    private final Scanner sc;
    private final JFrame frame;

    private JTextField commandInputField;
    private final JButton executeButton;

    private boolean running = false;

    private final int width = 240;
    private final int height = 500;

    /**
     * Console
     */
    public Console() {

        //Command list
        String[] commands = {"addstack [itemId] [amount]", "removestack [stackId]", "sethealth [health]", "setxp [xp]", "sethsb [h] [s] [b]", "listitems", "setloc [x] [y]"};

        //Initialize our JFrame
        frame = new JFrame("Console");

        super.setMinimumSize(new Dimension(width, height));
        super.setMaximumSize(new Dimension(width, height));
        super.setPreferredSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());

        Container c = frame.getContentPane();

        commandInputField = new JTextField();

        c.add(commandInputField, BorderLayout.NORTH);

        executeButton = new JButton("Execute [Enter]");
        executeButton.addActionListener((ActionEvent e) -> {
            String command = commandInputField.getText();
            commandInputField.setText(cmd(command));
        });

        c.add(executeButton, BorderLayout.SOUTH);

        JList commandList = new JList(commands);
        commandList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        commandList.setLayoutOrientation(JList.VERTICAL);
        commandList.setVisibleRowCount(-1);
        commandList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    Rectangle r = list.getCellBounds(0, list.getLastVisibleIndex());
                    if (r != null && r.contains(evt.getPoint())) {
                        int index = list.locationToIndex(evt.getPoint());
                        commandInputField.setText(commands[index]);
                    }
                }
            }
        });

        JScrollPane listScroller = new JScrollPane(commandList);
        listScroller.setPreferredSize(new Dimension(250, 80));

        c.add(listScroller, BorderLayout.CENTER);

        frame.getRootPane().setDefaultButton(executeButton);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.requestFocusInWindow();

    }

    public String cmd(String command) {

        if (command.startsWith("addstack")) {
            String[] cmdArray = command.split(" ");
            if (cmdArray.length != 3) {
                return "Invalid arguments for command 'addstack'";
            } else {
                try {
                    try {
                        ItemId itemId = ItemId.valueOf(cmdArray[1]);
                        int amount = Integer.parseInt(cmdArray[2]);

                        //Add stack of items
                        Game.INVENTORY.addStack(new ItemStack(new Item(itemId), amount));
                        return "Added a new stack of item " + itemId + ", amount: " + amount;
                    } catch (IllegalArgumentException e) {
                        return "Invalid ItemId!";
                    }
                } catch (NumberFormatException e) {
                    return "Invalid arguments for command 'addstack'";
                }

            }
        } else if (command.startsWith("removestack")) {

            String[] cmdArray = command.split(" ");
            if (cmdArray.length == 2) {
                try {
                    int stackId = Integer.parseInt(cmdArray[1]);
                    Game.INVENTORY.removeStack(stackId);
                    return "Removed itemStack from inventory slot " + stackId;
                } catch (NumberFormatException e) {
                    return "Invalid arguments for command 'removestack'";
                }
            } else {
                return "Invalid arguments for command 'removestack'";
            }

        } else if (command.startsWith("sethealth")) {
            String[] cmdArray = command.split(" ");
            if (cmdArray.length == 2) {
                try {
                    int health = Integer.parseInt(cmdArray[1]);
                    if (health >= 0 && health <= Game.PLAYER.getMaxHealth()) {
                        Game.PLAYER.setHealth(health);
                        return "Set player health to " + health;
                    } else {
                        return "Health must be between 0 and " + Game.PLAYER.getMaxHealth();
                    }
                } catch (NumberFormatException e) {
                    return "Invalid arguments for command 'sethealth'";
                }

            } else {
                return "Invalid arguments for command 'sethealth'";
            }
        } else if (command.startsWith("setxp")) {
            String[] cmdArray = command.split(" ");
            if (cmdArray.length == 2) {
                try {
                    double xp = Double.parseDouble(cmdArray[1]);
                    if (xp >= 0) {
                        Game.PLAYER.setXp(xp);
                        return "Set player XP to " + xp;
                    } else {
                        return "XP must be positive!";
                    }
                } catch (NumberFormatException e) {
                    return "Invalid arguments for command 'setxp'";
                }

            } else {
                return "Invalid arguments for command 'setxp'";
            }
        } else if (command.startsWith("sethsb")) {
            String[] cmdArray = command.split(" ");
            if (cmdArray.length == 4) {
                try {
                    float h = Float.parseFloat(cmdArray[1]);
                    float s = Float.parseFloat(cmdArray[2]);
                    float b = Float.parseFloat(cmdArray[3]);
                    return "HSB set to [" + h + "," + s + "," + b + "]";
                } catch (NumberFormatException e) {
                    return "Invalid arguments for command 'sethsb'";
                }

            }
        } else if (command.equals("listitems")) {

            String items = "";
            items = Game.itemRegistry.getItems().keySet().stream().map((itemId) -> itemId + "\r\n").reduce(items, String::concat);
            JOptionPane.showMessageDialog(frame, "List of items registered ingame:\r\n" + items);
            return "";
        } else if (command.startsWith("setloc")) {
            String[] cmdArray = command.split(" ");
            if (cmdArray.length == 3) {
                try {
                    int x = Integer.parseInt(cmdArray[1]);
                    int y = Integer.parseInt(cmdArray[2]);
                    Game.PLAYER.setX(x);
                    Game.PLAYER.setY(y);
                    return "Player location set to [" + x + ", " + y + "]";
                } catch (NumberFormatException ex) {
                    return "Invalid arguments for command 'setloc'";
                }
            } else {
                return "Invalid arguments for command 'setloc'";
            }
        } else {
            return "Command not found";
        }

        return "";
    }

}
