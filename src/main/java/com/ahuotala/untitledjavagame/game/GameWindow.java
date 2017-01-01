package com.ahuotala.untitledjavagame.game;

import java.awt.*;
import java.awt.image.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ahuotala.untitledjavagame.entities.MpPlayer;
import com.ahuotala.untitledjavagame.entities.NpcTicker;
import com.ahuotala.untitledjavagame.entities.Player;
import static com.ahuotala.untitledjavagame.game.Game.CENTERX;
import static com.ahuotala.untitledjavagame.game.Game.CENTERY;
import static com.ahuotala.untitledjavagame.game.Game.DEBUG_PLAYER;
import static com.ahuotala.untitledjavagame.game.Game.FONTSCALE;
import static com.ahuotala.untitledjavagame.game.Game.NAME;
import static com.ahuotala.untitledjavagame.game.Game.WINDOW_HEIGHT;
import static com.ahuotala.untitledjavagame.game.Game.WINDOW_WIDTH;
import static com.ahuotala.untitledjavagame.game.Game.frame;
import static com.ahuotala.untitledjavagame.game.Game.tickrate;
import com.ahuotala.untitledjavagame.game.postprocess.filters.DarkenFilter;
import com.ahuotala.untitledjavagame.graphics.SpriteSheet;
import com.ahuotala.untitledjavagame.graphics.animation.Animation;
import com.ahuotala.untitledjavagame.graphics.animation.AnimationTicker;
import com.ahuotala.untitledjavagame.map.Map;
import com.ahuotala.untitledjavagame.net.Client;
import com.ahuotala.untitledjavagame.net.PlayerList;
import javax.swing.JOptionPane;

/**
 *
 * @author Aleksi Huotala
 */
public class GameWindow extends Canvas implements Runnable, Tickable {

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
    public static SpriteSheet spriteSheet = new SpriteSheet("spriteSheet.png");

    /**
     * Current font
     */
    private Font currentFont;

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
    public GameWindow() {
        renderer = new Renderer(WINDOW_WIDTH, WINDOW_HEIGHT, pixels);

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

        start();
        
    }

    public void init() {

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
//                menu.render(g);
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
                player.render(renderer, g, map);

                //Render MP players
                if (isConnectedToServer) {
                    PlayerList pList = client.getPlayerList();
                    for (MpPlayer plr : pList.getPlayerList().values()) {
                        try {
                            if (plr.getUuid().equals(client.getUuid())) {
                                continue;
                            }
                            plr.renderMpPlayer(g, renderer, player);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "Error rendering player(s): " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                        }

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
                    g.drawString("gameTime (0 - 2359) " + gameTime.getTime() + ", " + String.format("%.5f", gameTime.getFactor()), 5, 127);
                }
            }

        } finally {
            //Empty buffer
            g.dispose();
        }

        //Show frame
        bs.show();
    }

}
