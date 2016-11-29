package ahuotala.game;

import ahuotala.entities.*;
import ahuotala.game.postprocess.filters.*;
import ahuotala.graphics.animation.*;
import ahuotala.graphics.*;
import ahuotala.map.*;
import ahuotala.net.Client;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import ahuotala.net.ClientStatus;
import ahuotala.net.MpPlayer;
import ahuotala.net.PlayerList;

/**
 *
 * @author Aleksi Huotala
 */
public class Game extends Canvas implements Runnable, Tickable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Debug mode
     */
    public static boolean DEBUG = false;

    /**
     * Player debug mode
     */
    public static boolean DEBUG_PLAYER = false;

    //Graphics device
    public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    /**
     * Window width
     */
    public static final int WINDOW_WIDTH = (int) Math.floor(gd.getDisplayMode().getWidth() * 0.24);

    /**
     * Window height
     */
    public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9;

    /**
     * Tile scale
     */
    public static final int SCALE = 2;

    /**
     * Font scale
     */
    public static final double FONTSCALE = 0.7;

    /**
     * Center X coordinate
     */
    public static final int CENTERX = WINDOW_WIDTH / 2;

    /**
     * Center Y coordinate
     */
    public static final int CENTERY = WINDOW_HEIGHT / 2;

    /**
     * Game title
     */
    public static final String NAME = "Untitled Game";

    /**
     * Save file name
     */
    private String saveFileName = "save.sav";

    /**
     * JFrame
     */
    private final JFrame frame;

    /**
     * Game state
     */
    public boolean running = false;

    /**
     * Tick count
     */
    public int tickCount = 0;

    /**
     * Tick rate
     */
    public static double tickrate = 60D;

    /**
     * Image data
     */
    private final BufferedImage image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);

    /**
     * Pixel array
     */
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    /**
     * Renderer
     */
    private Renderer renderer;

    /**
     * Graphics object
     */
    private Graphics g;

    /**
     * SpriteSheet
     */
    public static SpriteSheet spriteSheet = new SpriteSheet("spriteSheet_2.png");

    /**
     * Current font
     */
    private Font currentFont;

    /**
     * NPC test
     */
    private final InteractableNpc npc = new InteractableNpc("TestNPC");

    /**
     * Animation ticker
     */
    public static final AnimationTicker animationTicker = new AnimationTicker();

    /**
     * NPC ticker
     */
    public static final NpcTicker npcTicker = new NpcTicker();

    /**
     * Animation for player low health
     */
    private final Animation playerLowHealth;

    /**
     * Save object
     */
    private SaveGame save;

    /**
     * Map
     */
    public Map map = new Map("map3");

    /**
     * Player
     */
    public static final Player player = new Player();

    /**
     * Input handler
     */
    private final PlayerInputHandler inputHandler = new PlayerInputHandler(player, map, this);

    /**
     * Inventory
     */
    public static final Inventory inventory = new Inventory();

    /**
     * Inventory state
     */
    public static boolean SHOW_INVENTORY = false;

    /**
     * ItemRegistry
     */
    public static ItemRegistry itemRegistry = new ItemRegistry();

    /**
     * MouseHandler
     */
    private final MouseHandler mouseHandler = new MouseHandler(inventory);

    /**
     * Client
     */
    private Client client;

    /**
     * Connection state Note: Multiplayer functionality is not currently
     * implemented.
     */
    public static boolean isConnectedToServer = false;

    /**
     * Menu
     */
    private Menu menu;

    /**
     * Menu state
     */
    public static MenuState menuState = MenuState.MAINMENU;

    /**
     * Is the menu open?
     */
    public static boolean isInMenu = true;

    /**
     * Is the save loaded?
     */
    public static boolean playing = false;

    /**
     * Logger
     */
    private static final Logger LOG = Logger.getLogger(Game.class.getName());

    /**
     * GameTime
     */
    private static final GameTime gameTime = new GameTime();

    /**
     * Constructor
     */
    public Game() {
        renderer = new Renderer(WINDOW_WIDTH, WINDOW_HEIGHT, pixels);

        //Default time is 12:00
        gameTime.setGametime(1200);

        super.setMinimumSize(new Dimension(WINDOW_WIDTH * SCALE, WINDOW_HEIGHT * SCALE));
        super.setMaximumSize(new Dimension(WINDOW_WIDTH * SCALE, WINDOW_HEIGHT * SCALE));
        super.setPreferredSize(new Dimension(WINDOW_WIDTH * SCALE, WINDOW_HEIGHT * SCALE));

        //Listeners
        super.addKeyListener(inputHandler);
        super.addMouseListener(mouseHandler);
        super.addMouseMotionListener(mouseHandler);

        /**
         * Load sprites and animations here
         */
        //Player picture (up)
        spriteSheet.loadSprite("player_up", 32, 32, 16);
        //Player picture (down)
        spriteSheet.loadSprite("player_down", 32, 16, 16);
        //Player picture (left)
        spriteSheet.loadSprite("player_left", 32, 64, 16);
        //Player picture (right)
        spriteSheet.loadSprite("player_right", 32, 48, 16);
        //Swimming:
        //Player picture (up)
        spriteSheet.loadSprite("player_swimming_up", 80, 32, 16);
        //Player picture (down)
        spriteSheet.loadSprite("player_swimming_down", 80, 16, 16);
        //Player picture (left)
        spriteSheet.loadSprite("player_swimming_left", 80, 64, 16);
        //Player picture (right)
        spriteSheet.loadSprite("player_swimming_right", 80, 48, 16);
        //Full heart
        spriteSheet.loadSprite("full_heart", 112, 96, 32);
        //Half heart
        spriteSheet.loadSprite("half_a_heart", 144, 96, 32);
        //Player shadow
        spriteSheet.loadSprite("player_shadow", 208, 14, 32, 34);
        //Animations
        playerLowHealth = new Animation("PlayerLowHealth", 30);
        //Register animations to be tickable
        animationTicker.register(playerLowHealth);

        //Initialize our JFrame
        frame = new JFrame(NAME);

        //Listeners
        frame.addKeyListener(inputHandler);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (isConnectedToServer) {
                    isConnectedToServer = false;
                    client.send(ClientStatus.CLIENT_DISCONNECTED.toString() + ";" + client.getUuid());
                    client.disconnect();
                }
                save();
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
    }

    /**
     * Establish a new connection
     *
     * @param host Server IP address
     * @param port Server port
     */
    public void connectToServer(String host, int port) {
        client = new Client(player, host, port);
        client.start();
        client.send(ClientStatus.CLIENT_CONNECTED.toString() + ";" + client.getUuid());
        newGame("multiplayer.sav");
        Game.menuState = MenuState.NONE;
    }

    public void init() {
        //Set test NPC x and y
        npc.setX(244);
        npc.setY(270);
        npc.setInteractionRadiusX(16);
        npc.setInteractionRadiusY(16);
        //Register the new NPC to be tickable
        npcTicker.register(npc);
        //Register items to the game
        itemRegistry.registerItem(ItemId.POTIONOFHEALING_20LP).setName("Potion of healing").setEffect(Effect.HEAL_20LP, "Heals the player for 20 lifepoints").setInteractable(true);
        itemRegistry.registerItem(ItemId.POTIONOFHEALING_60LP).setName("Grand potion of healing").setEffect(Effect.HEAL_60LP, "Heals the player for 60 lifepoints").setInteractable(true);
        itemRegistry.registerItem(ItemId.FOOD).setName("Raw beef").setEffect(Effect.HEAL_40LP, "Heals the player for 40 lifepoints").setInteractable(false);
        menu = new Menu(this);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void save() {
        File saveFile = new File(saveFileName);
        //If the file doesn't exist, create it
        if (!saveFile.exists() && playing) {
            try {
                LOG.log(Level.INFO, "Save file doesn't exist; Creating a new file..");
                saveFile.createNewFile();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
        try {
            if (save != null && playing) {
                //Save the game
                LOG.log(Level.INFO, "Saving game..");
                save.saveState(player.getX(), player.getY(), player.getHealth(), player.getXp(), player.getDirection(), gameTime.getGametime(), inventory.getInventory());
                FileOutputStream fileOutput = new FileOutputStream(saveFileName);
                ObjectOutputStream out = new ObjectOutputStream(fileOutput);
                out.writeObject(save);
                out.close();
                fileOutput.close();
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    public void loadSaveFile(File file) {
        /**
         * Load from save
         */
        try {
            File saveFile = file;
            saveFileName = saveFile.getAbsolutePath();
            FileInputStream fileInput = new FileInputStream(saveFileName);
            ObjectInputStream in = new ObjectInputStream(fileInput);
            save = (SaveGame) in.readObject();
            in.close();
            fileInput.close();

        } catch (IOException | ClassNotFoundException e) {
            LOG.log(Level.SEVERE, null, e);
        }
        //Set player x, y, health, xp and direction
        player.setX(save.getX());
        player.setY(save.getY());
        player.setHealth(save.getHealth());
        player.setXp(save.getXp());
        player.setDirection(save.getDirection());
        //Set game time
        gameTime.setGametime(save.getCurrentGameTime());
        //Set player inventory
        inventory.setInventory(save.getInventory());
    }

    public void newGame(String saveFileName) {
        this.saveFileName = saveFileName;
        if (!saveFileName.trim().endsWith(".sav")) {
            this.saveFileName += ".sav";
        }
        save = new SaveGame();
        //Set player x, y, health, xp and direction
        player.setX(save.getX());
        player.setY(save.getY());
        player.setHealth(save.getHealth());
        player.setXp(save.getXp());
        player.setDirection(save.getDirection());
        //Set player inventory
        inventory.setInventory(save.getInventory());
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

        //Init
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
                    LOG.log(Level.SEVERE, null, ex);
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
        //Tick animations, input, npcs and player
        if (Game.menuState != MenuState.PAUSED) {
            animationTicker.tick();
            inputHandler.tick();
            npcTicker.tick();
            player.tick();
            //Tick tick..
            if (tickCount % 40 == 0) {
                gameTime.tick();
            }
        }
        //Tick the server if we are connected
        if (isConnectedToServer) {
            client.tick();
        }
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            //Triple buffering
            createBufferStrategy(3);
            return;
        }
        try {
            //Get draw graphics
            g = bs.getDrawGraphics();
            //Clear
            renderer.clear();
            //Render base image
            renderer.render();

            //Font
            currentFont = g.getFont();
            g.setFont(new Font(currentFont.getName(), Font.BOLD, (int) Math.floor(18 * FONTSCALE)));
            if (Game.menuState != MenuState.NONE) {
                //Image
                g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                menu.render(g);
            } else {
                //If the game is running, render map & npcs

                //Apply darken filter
                renderer.setFilters(new DarkenFilter(gameTime.getFactor()));

                //Map
                map.renderMap(g, renderer, player);
                //Other objects
                map.renderObjects(g, renderer, player);
                map.detectCollision(player);

                //NPCs here
                //Draw npc
                npc.renderNpc(g, renderer, player);

                player.render(renderer, g, map);

                //Render players
                if (isConnectedToServer) {
                    PlayerList pList = client.getPlayerList();
                    for (MpPlayer plr : pList.getPlayerList().values()) {
                        if (plr.getUuid().equals(client.getUuid())) {
                            continue;
                        }
                        Npc tmpNpc = new Npc(plr.toString());
                        tmpNpc.setX(plr.getX());
                        tmpNpc.setY(plr.getY());
                        tmpNpc.setDirection(plr.getDirection());
                        tmpNpc.renderNpc(g, renderer, player);
                    }
                }

                //Reset filter
                renderer.resetFilter();

                //Player health system
                int playerFullHearts = (int) Math.floor(player.getHealth() / 20);
                int playerHalfHearts = (int) Math.floor((player.getHealth() - playerFullHearts * 20) / 10);
                int heartX = CENTERX - 256;
                int heartY = 5;
                g.drawString(player.getHealth() + " / " + player.getMaxHealth() + " LP", heartX - 78, heartY + 22);
                g.drawString(player.getXp() + " xp", heartX - 78, heartY + 44);
                if (playerFullHearts == 0 && playerHalfHearts == 0 && player.getHealth() > 0) {
                    playerLowHealth.nextFrame(renderer, heartX, heartY);
                } else if (player.getHealth() == 0) {
                    g.setColor(Color.red);
                    //Black background
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                    g.drawString("YOU DIED", CENTERX - 48, CENTERY);
                } else {
                    for (int hearts = 0; hearts < playerFullHearts; hearts++) {
                        spriteSheet.paint(renderer, "full_heart", heartX, heartY);
                        heartX += 26;
                    }
                    if (playerHalfHearts > 0) {
                        spriteSheet.paint(renderer, "half_a_heart", heartX, heartY);
                    }
                }

                if (SHOW_INVENTORY) {
                    inventory.renderInventory(g, renderer);
                }

                //Final image
                g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

                //Debug for player
                g.setColor(Color.white);

                if (DEBUG_PLAYER) {
                    g.drawString("x " + player.getX(), 5, 15);
                    g.drawString("y " + player.getY(), 5, 31);
                    g.drawString("tileCount " + map.getRenderedTileCount(), 5, 47);
                    g.drawString("tileX " + map.getCurrentTileX(), 5, 63);
                    g.drawString("tileY " + map.getCurrentTileY(), 5, 79);
                    g.drawString("windowWidth " + Game.WINDOW_WIDTH, 5, 95);
                    g.drawString("windowHeight " + Game.WINDOW_HEIGHT, 5, 111);
                    g.drawString("gameTime (0 - 2359) " + gameTime.getGametime() + ", " + String.format("%.5f", gameTime.getFactor()), 5, 127);
                }
            }

        } finally {
            //Empty buffer
            g.dispose();
        }

        //Show frame
        bs.show();
    }

    public static void main(String[] args) {
        //Start the game
        new Game().start();
        new Console().run();
    }
}
