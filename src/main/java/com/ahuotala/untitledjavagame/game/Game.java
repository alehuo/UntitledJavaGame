package com.ahuotala.untitledjavagame.game;

import com.ahuotala.untitledjavagame.game.item.Effect;
import com.ahuotala.untitledjavagame.game.item.Inventory;
import com.ahuotala.untitledjavagame.game.item.ItemRegistry;
import com.ahuotala.untitledjavagame.game.item.ItemId;
import com.ahuotala.untitledjavagame.menu.MenuState;
import com.ahuotala.untitledjavagame.menu.Menu;
import com.ahuotala.untitledjavagame.game.handler.MouseHandler;
import com.ahuotala.untitledjavagame.game.handler.PlayerInputHandler;
import com.ahuotala.untitledjavagame.game.singleplayer.Singleplayer;
import com.ahuotala.untitledjavagame.net.Multiplayer;
import com.ahuotala.untitledjavagame.entities.NpcTicker;
import com.ahuotala.untitledjavagame.entities.Player;
import com.ahuotala.untitledjavagame.graphics.SpriteSheet;
import com.ahuotala.untitledjavagame.graphics.animation.Animation;
import com.ahuotala.untitledjavagame.graphics.animation.AnimationTicker;
import com.ahuotala.untitledjavagame.map.Map;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Aleksi Huotala
 */
public class Game extends JFrame implements Runnable, Tickable {

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

    /**
     * Collision detection
     */
    public static boolean COLLISIONDECECTION = true;

    //Graphics device
    /**
     *
     */
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
     * JFrame
     */
    public static JFrame frame;

    /**
     * Tick rate
     */
    public static double tickrate = 60D;

    /**
     * SpriteSheet
     */
    public static SpriteSheet spriteSheet = new SpriteSheet("sprites/spriteSheet.png");

    /**
     * Animation ticker
     */
    public static final AnimationTicker ANIMATIONTICKER = new AnimationTicker();

    /**
     * NPC ticker
     */
    public static final NpcTicker NPCTICKER = new NpcTicker();

    /**
     * Player
     */
    public static final Player PLAYER = new Player();

    /**
     * Inventory
     */
    public static final Inventory INVENTORY = new Inventory();

    /**
     * Inventory state
     */
    public static boolean SHOW_INVENTORY = false;

    /**
     * ItemRegistry
     */
    public static ItemRegistry itemRegistry = new ItemRegistry();

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
    private static final GameTime GT = new GameTime();

    /**
     *
     * @return
     */
    public static GraphicsDevice getGd() {
        return gd;
    }

    /**
     *
     * @param gd
     */
    public static void setGd(GraphicsDevice gd) {
        Game.gd = gd;
    }

    /**
     *
     * @return
     */
    public static SpriteSheet getSpriteSheet() {
        return spriteSheet;
    }

    /**
     *
     * @param spriteSheet
     */
    public static void setSpriteSheet(SpriteSheet spriteSheet) {
        Game.spriteSheet = spriteSheet;
    }

    /**
     *
     * @return
     */
    public static MenuState getMenuState() {
        return menuState;
    }

    /**
     *
     * @param menuState
     */
    public static void setMenuState(MenuState menuState) {
        Game.menuState = menuState;
    }

    /**
     *
     * @return
     */
    public static boolean isPlaying() {
        return playing;
    }

    /**
     *
     * @param playing
     */
    public static void setPlaying(boolean playing) {
        Game.playing = playing;
    }
    /**
     * Save file name
     */
    private String saveFileName = "save.sav";
    /**
     * Game state
     */
    public boolean running = false;
    /**
     * Tick count
     */
    public int tickCount = 0;

    /**
     * Graphics object
     */
    private Graphics g;
    /**
     * Current font
     */
    private Font currentFont;
    /**
     * Animation for PLAYER low health
     */
    private final Animation playerLowHealth;
    /**
     * Map
     */
    public Map map = new Map("map");
    /**
     * Input handler
     */
    private final PlayerInputHandler inputHandler = new PlayerInputHandler(PLAYER, map, this);
    /**
     * MouseHandler
     */
    private final MouseHandler mouseHandler = new MouseHandler(INVENTORY);

    /**
     * Menu
     */
    private final Menu menu;

    /**
     * Multiplayer
     */
    private Multiplayer mp;

    /**
     * Singleplayer
     */
    private Singleplayer sp;

    private GameFrame gameFrame;

    /**
     * Image data
     */
    private final BufferedImage image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);

    /**
     * Pixel array
     */
    private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    /**
     * Constructor
     */
    public Game() {

        gameFrame = new GameFrame(this);
        gameFrame.setVisible(false);

        //Menu
        menu = new Menu(this);

        //Multiplayer
        mp = new Multiplayer(this, PLAYER);
        //Singleplayer
        sp = new Singleplayer(this, PLAYER);

        //Default time is 12:00
        GT.setTime(1200);

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
        ANIMATIONTICKER.register(playerLowHealth);

        //Initialize our JFrame
        frame = new JFrame(NAME);

        //Listeners
        frame.addKeyListener(inputHandler);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (mp.isConnected()) {
                    mp.disconnect();
                }
                //save();
            }
        });
        frame.addMouseListener(mouseHandler);
        frame.addMouseMotionListener(mouseHandler);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());

        //Add game frame
        frame.add(gameFrame, BorderLayout.CENTER);

        //Add menu
        frame.add(menu, BorderLayout.CENTER);

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.requestFocusInWindow();

        this.start();

    }

    public BufferedImage getImage() {
        return image;
    }

    public int[] getPixels() {
        return pixels;
    }

    /**
     * Establish a new connection
     *
     * @param host Server IP address
     * @param port Server port
     * @throws java.net.SocketException
     * @throws java.net.UnknownHostException
     */
    public void connectToServer(String host, int port) throws SocketException, UnknownHostException {
        if (mp.connect(InetAddress.getByName(host), port)) {
            LOG.log(Level.INFO, "Successfully connected to {0}:{1}", new Object[]{host, port});
            //Set game to render etc.
        } else {
            LOG.log(Level.SEVERE, "Error connecting to {0}:{1}", new Object[]{host, port});
            //Back to main menu
        }
    }

    /**
     *
     */
    public void init() {
        //Register items to the game
        itemRegistry.registerItem(ItemId.POTIONOFHEALING_20LP).setName("Potion of healing").setEffect(Effect.HEAL_20LP, "Heals the player for 20 lifepoints").setInteractable(true);
        itemRegistry.registerItem(ItemId.POTIONOFHEALING_60LP).setName("Grand potion of healing").setEffect(Effect.HEAL_60LP, "Heals the player for 60 lifepoints").setInteractable(true);
        itemRegistry.registerItem(ItemId.FOOD).setName("Raw beef").setEffect(Effect.HEAL_40LP, "Heals the player for 40 lifepoints").setInteractable(false);
        //Init anims
        PLAYER.initAnimations();
    }

    /**
     *
     * @return
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     *
     * @return
     */
    public Multiplayer getMp() {
        return mp;
    }

    /**
     *
     * @param mp
     */
    public void setMp(Multiplayer mp) {
        this.mp = mp;
    }

    /**
     *
     * @return
     */
    public Singleplayer getSp() {
        return sp;
    }

    /**
     *
     * @param sp
     */
    public void setSp(Singleplayer sp) {
        this.sp = sp;
    }

    /**
     *
     * @return
     */
    public GameTime getGameTime() {
        return GT;
    }

    /**
     *
     * @return
     */
    public boolean isRunning() {
        return running;
    }

    /**
     *
     * @param running
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     *
     * @return
     */
    public Graphics getG() {
        return g;
    }

    /**
     *
     * @param g
     */
    public void setG(Graphics g) {
        this.g = g;
    }

    /**
     *
     * @return
     */
    public Font getCurrentFont() {
        return currentFont;
    }

    /**
     *
     * @param currentFont
     */
    public void setCurrentFont(Font currentFont) {
        this.currentFont = currentFont;
    }

    /**
     *
     * @return
     */
    public Map getMap() {
        return map;
    }

    /**
     *
     * @param map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     *
     * @return
     */
    public Animation getPlayerLowHealth() {
        return playerLowHealth;
    }

    /*##############################################*/
    private synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    private synchronized void stop() {
        running = false;
    }

    /**
     * Run method Tick and render
     */
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

            //Tick
            while (delta >= 1) {
                ticks++;
                tick();
                delta -= 1;
                //Render after we have ticked
                shouldRender = true;
            }

            //Sleep for 1 millisecond
            if (shouldRender) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }

            //Render a new frame
            if (shouldRender) {
                frames++;
                gameFrame.render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                frame.setTitle(NAME + " (" + frames + " frames, " + ticks + " ticks)");
                frames = 0;
                ticks = 0;
            }
        }
    }

    /**
     *
     */
    @Override
    public void tick() {
        tickCount++;
        //Tick animations, input, npcs and PLAYER
        if (Game.menuState != MenuState.PAUSED) {
            ANIMATIONTICKER.tick();
            inputHandler.tick();
            NPCTICKER.tick();
            PLAYER.tick();
            //Tick tick..
            if (Game.menuState == MenuState.SINGLEPLAYER_PLAYING && tickCount % 40 == 0) {
                GT.tick();
            }
        }

        //Tick the UDP client if we are connected
        if (mp.isConnected()) {
            mp.getUdpClient().tick();
        }
    }

    /**
     *
     */
    public void loadSp() {
        menu.setVisible(false);
        gameFrame.setVisible(true);
        super.revalidate();
        super.repaint();
        Game.menuState = MenuState.SINGLEPLAYER_PLAYING;
    }

    /**
     *
     */
    public void loadMenu() {
        menu.setVisible(true);
        gameFrame.setVisible(false);
        super.revalidate();
        super.repaint();
        Game.menuState = MenuState.PAUSED;
    }

    /**
     *
     */
    public void loadMp() {
        /*
        Todo
         */
    }

}
