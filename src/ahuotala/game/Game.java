package ahuotala.game;

import ahuotala.entities.*;
import ahuotala.graphics.animation.*;
import ahuotala.graphics.*;
import ahuotala.map.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFrame;

/**
 *
 * @author Aleksi Huotala
 */
public class Game extends Canvas implements Runnable, Tickable {

    private static final long serialVersionUID = 1L;

    public static boolean DEBUG = false;

    public static boolean DEBUG_PLAYER = false;

    //Window width
    public static final int WINDOW_WIDTH = 1600;

    //Window height
    public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9;

    //Tile scale
    public static final int SCALE = 1;

    //Font scale
    public static final double FONTSCALE = 0.7;

    //Center x coordinate
    public static final int CENTERX = (int) Math.floor(WINDOW_WIDTH / 2);

    //Center y coordinate
    public static final int CENTERY = (int) Math.floor(WINDOW_HEIGHT / 2);

    //Game name
    public static final String NAME = "Untitled Game";

    //Save file name
    private final String saveFileName = "save.sav";

    //JFrame object
    private final JFrame frame;

    //Game state
    public boolean running = false;

    //Tick count
    public int tickCount = 0;

    //Tickrate; amount of game updates per second
    public double tickrate = 60D;

    private final BufferedImage image;

    //Sprite sheet
    public static SpriteSheet spriteSheet = new SpriteSheet("spriteSheet.png");

    //Font
    private Font currentFont;

    //Player
    private final Player player = new Player();

    //NPC test
    private final InteractableNpc npc = new InteractableNpc("TestNPC");

    //Animation ticker
    public static final AnimationTicker animationTicker = new AnimationTicker();

    //NPC ticker
    public static final NpcTicker npcTicker = new NpcTicker();

    //Animations
    private final Animation playerLowHealth;

    //Save
    private SaveGame save;

    //Map
    Map map = new Map("map3");

    //Input handler
    private final PlayerInputHandler inputHandler = new PlayerInputHandler(player, map);

    //Inventory
    public static final Inventory inventory = new Inventory();
    public static boolean SHOW_INVENTORY = false;

    //ItemRegistry
    public static ItemRegistry itemRegistry = new ItemRegistry();

    //Mouse handler
    private final MouseHandler mouseHandler = new MouseHandler(inventory);

    /**
     * Constructor
     */
    public Game() {
        this.image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setMaximumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        //Listeners
        addKeyListener(inputHandler);
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);

        /**
         * Load sprites and animations here
         */
        //Player picture (up)
        spriteSheet.getSprite("player_up", 32, 32, 16);
        //Player picture (down)
        spriteSheet.getSprite("player_down", 32, 16, 16);
        //Player picture (left)
        spriteSheet.getSprite("player_left", 32, 64, 16);
        //Player picture (right)
        spriteSheet.getSprite("player_right", 32, 48, 16);
        //Swimming:
        //Player picture (up)
        spriteSheet.getSprite("player_swimming_up", 80, 32, 16);
        //Player picture (down)
        spriteSheet.getSprite("player_swimming_down", 80, 16, 16);
        //Player picture (left)
        spriteSheet.getSprite("player_swimming_left", 80, 64, 16);
        //Player picture (right)
        spriteSheet.getSprite("player_swimming_right", 80, 48, 16);
        //Full heart
        spriteSheet.getSprite("full_heart", 112, 96, 32);
        //Half heart
        spriteSheet.getSprite("half_a_heart", 144, 96, 32);
        //Player shadow
        spriteSheet.getSprite("player_shadow", 208, 14, 32, 34);
        //Animations
        playerLowHealth = new Animation("PlayerLowHealth", 40);
        //Register animations to be tickable
        animationTicker.register(playerLowHealth);

        //Initialize our JFrame
        frame = new JFrame(NAME);

        //Listeners
        frame.addKeyListener(inputHandler);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                File saveFile = new File(saveFileName);
                //If the file doesn't exist, create it
                if (!saveFile.exists()) {
                    try {
                        saveFile.createNewFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    //Save the game
                    save.saveState(player.getX(), player.getY(), player.getHealth(), player.getXp(), player.getDirection(), inventory.getInventory());
                    FileOutputStream fileOutput = new FileOutputStream(saveFileName);
                    ObjectOutputStream out = new ObjectOutputStream(fileOutput);
                    out.writeObject(save);
                    out.close();
                    fileOutput.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        frame.addMouseListener(mouseHandler);
        frame.addMouseMotionListener(mouseHandler);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.requestFocusInWindow();
        //Register items to the game
        itemRegistry.registerItem(ItemId.POTIONOFHEALING_20LP).setName("Potion of healing").setEffect(Effect.HEAL_20LP, "Heals the player for 20 lifepoints").setInteractable(true);
        itemRegistry.registerItem(ItemId.POTIONOFHEALING_60LP).setName("Grand potion of healing").setEffect(Effect.HEAL_60LP, "Heals the player for 60 lifepoints").setInteractable(true);
        itemRegistry.registerItem(ItemId.FOOD).setName("Raw beef").setEffect(Effect.HEAL_40LP, "Heals the player for 40 lifepoints").setInteractable(false);
        /**
         * Load from save
         */
        try {
            File saveFile = new File(saveFileName);
            if (saveFile.exists()) {
                FileInputStream fileInput = new FileInputStream(saveFileName);
                ObjectInputStream in = new ObjectInputStream(fileInput);
                save = (SaveGame) in.readObject();
                in.close();
                fileInput.close();
            } else {
                //If the save file is not found, start a new game
                save = new SaveGame();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Set player inventory
        inventory.setInventory(save.getInventory());
//        inventory.addStack(new ItemStack(itemRegistry.getItem(ItemId.POTIONOFHEALING_20LP), 12));
//        inventory.addStack(new ItemStack(itemRegistry.getItem(ItemId.POTIONOFHEALING_60LP), 12));
//        inventory.addStack(new ItemStack(itemRegistry.getItem(ItemId.FOOD), 1));
        //Set player x, y, health, xp and direction
        player.setX(save.getX());
        player.setY(save.getY());
        player.setHealth(save.getHealth());
        player.setXp(save.getXp());
        player.setDirection(save.getDirection());
    }

    public void init() {
        //Set test NPC x and y
        npc.setX(244);
        npc.setY(270);
        npc.setInteractionRadiusX(16);
        npc.setInteractionRadiusY(16);
        //Register the new NPC to be tickable
        npcTicker.register(npc);
    }

    private synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    private synchronized void stop() {
        running = false;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / tickrate;

        int frames = 0;
        int ticks = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        init();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            //Use false to limit fps to the number of ticks
            boolean shouldRender = false;
            while (delta >= 1) {
                //Tick
                ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }
            if (shouldRender) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            if (shouldRender) {
                //Render a new frame
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                frame.setTitle(NAME + " (" + frames + " frames, " + ticks + " ticks)");
                frames = 0;
                ticks = 0;
            }
        }
    }

    @Override
    public void tick() {
        tickCount++;
        animationTicker.tick();
        inputHandler.tick();
        npcTicker.tick();
        player.tick();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            //Triple buffering
            createBufferStrategy(3);
            return;
        }

        //Get draw graphics
        Graphics g = bs.getDrawGraphics();

        //Font
        currentFont = g.getFont();
        g.setFont(new Font(currentFont.getName(), Font.BOLD, (int) Math.floor(18 * FONTSCALE)));

        //Black background
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        //Map
        map.renderMap(g, player);
        //Other objects
        map.renderObjects(g, player);

        //NPCs here
        //Draw npc
        npc.renderNpc(g, player);

        //Player x and y
        int playerX = player.getRealX();
        int playerY = player.getRealY();

        player.render(g, map);

        //Player health system
        int playerFullHearts = (int) Math.floor(player.getHealth() / 20);
        int playerHalfHearts = (int) Math.floor((player.getHealth() - playerFullHearts * 20) / 10);
        int heartX = CENTERX - 256;
        int heartY = 5;
        g.drawString(player.getHealth() + " / " + player.getMaxHealth() + " LP", heartX - 78, heartY + 22);
        g.drawString(player.getXp() + " xp", heartX - 78, heartY + 44);
        if (playerFullHearts == 0 && playerHalfHearts == 0 && player.getHealth() > 0) {
            playerLowHealth.nextFrame(g, heartX, heartY);
        } else if (player.getHealth() == 0) {
            g.setColor(Color.red);
            //Black background
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            g.drawString("YOU DIED", CENTERX - 48, CENTERY);
        } else {
            for (int hearts = 0; hearts < playerFullHearts; hearts++) {
                spriteSheet.paint(g, "full_heart", heartX, heartY);
                heartX += 26;
            }
            if (playerHalfHearts > 0) {
                spriteSheet.paint(g, "half_a_heart", heartX, heartY);
            }
        }

        map.detectCollision(player);

        if (SHOW_INVENTORY) {
            inventory.renderInventory(g);
        }

        //Empty buffer
        g.dispose();
        //Show frame
        bs.show();
    }

    public static void main(String[] args) {
        //Start the game
        new Game().start();
    }
}
