package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;

import java.util.Random;

import com.jogamp.graph.font.Font;

import java.util.*;
import java.io.*;
import java.lang.StringBuilder;


public class App extends PApplet {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;

    public static final int FPS = 60;

    public static final Random randomGenerator = new Random();

    public String configPath;
    
    public PImage brickwall;
    public PImage stonewall;
    public PImage gremlin;
    public PImage slime;
    public PImage fireball;
    public PImage wizard0;
    public PImage wizard1;
    public PImage wizard2;
    public PImage wizard3;
    public PImage exit;

    public int lives;
    public JSONArray levels;
    public JSONObject currentLevel;
    public int level;
    public int tempLevel;
    public String[] layout;

    public Player player;
    public Gremlin Gremlin;
    public List<Gremlin> gremlins = new ArrayList<Gremlin>();
    public float fireballCooldown;
    public float slimeCooldown;

    public List<Fireball> fireballs = new ArrayList<Fireball>();
    public List<Slime> slimes = new ArrayList<Slime>();

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {
        frameRate(FPS);

        // Load images during setup -> Figure out a way to check if they don't exist.
        this.stonewall = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
        this.brickwall = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
        this.gremlin = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
        this.slime = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
        this.fireball = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));
        this.wizard0 = loadImage(this.getClass().getResource("wizard0.png").getPath().replace("%20", " "));
        this.wizard1 = loadImage(this.getClass().getResource("wizard1.png").getPath().replace("%20", " "));
        this.wizard2 = loadImage(this.getClass().getResource("wizard2.png").getPath().replace("%20", " "));
        this.wizard3 = loadImage(this.getClass().getResource("wizard3.png").getPath().replace("%20", " "));
        this.exit = loadImage(this.getClass().getResource("exit.png").getPath().replace("%20", " "));
        
        JSONObject conf = loadJSONObject(new File(this.configPath));
        this.lives = conf.getInt("lives");
        this.levels = conf.getJSONArray("levels");
        
        this.level = 0;
        this.tempLevel = -1;
    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    public void keyPressed(){
        if (key == CODED) {
            if (keyCode == UP) {
                player.setMovement(2, this);
            } else if (keyCode == LEFT) {
                player.setMovement(0, this);
            } else if (keyCode == RIGHT) {
                player.setMovement(1, this);
            } else if (keyCode == DOWN) {
                player.setMovement(3, this);
            }
        } else if (key == ' ') {
            if (player.shoot(this.fireballCooldown) == true) {
                fireballs.add(new Fireball(player.getX(), player.getY(), player.getDir(), this));
                player.shot();
            }
        }
    }
    
    /**
     * Receive key released signal from the keyboard.
    */
    public void keyReleased(){
        if (key == CODED) {
            player.stopMove();
        }
    }

    /**
     * Resetting map layout
     */
    public void resetMap(){
        this.currentLevel = this.levels.getJSONObject(level);
        this.fireballCooldown = this.currentLevel.getFloat("wizard_cooldown");
        this.slimeCooldown = this.currentLevel.getFloat("enemy_cooldown");
        String layoutName = this.currentLevel.getString("layout");
        List<String> listOfStrings = new ArrayList<String>();
        try (Scanner sc = new Scanner(new FileReader(layoutName))) {
            String str;
            while (sc.hasNextLine()) {
                str = sc.nextLine();
                listOfStrings.add(str);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.layout = listOfStrings.toArray(new String[listOfStrings.size()]);
        int y = 0;
        for (String eachString : this.layout) {
            int x = 0;
            for (int i = 0; i < eachString.length(); i++) {
                if (eachString.charAt(i) == 'W') {
                    // Found starting position for player (x, y)
                    player = new Player(x,y, 2, this);
                    // Remove player marker from tempstring
                    StringBuilder tempString = new StringBuilder(this.layout[y/20]);
                    tempString.setCharAt(x/20, ' ');
                    this.layout[y/20] = tempString.toString();
                } else if (eachString.charAt(i) == 'G') {
                    gremlins.add(new Gremlin(x, y, 0, this));

                    StringBuilder tempString = new StringBuilder(this.layout[y/20]);
                    tempString.setCharAt(x/20, ' ');
                    this.layout[y/20] = tempString.toString();
                }
                x = x+20;
            }
            y = y+20;
        }
    }

    /**
     * Get the block that is at the place
     */
    public char tileAt(int x, int y) {
        return this.layout[Math.floorDiv(y,20)].charAt(Math.floorDiv(x,20));
    }

    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        // If die: Could just lower tempLevel by 1.
        background(182, 146, 109);
        // If level finished: Parse base of next level
        // If level not finished: Reparse unbreakable objects everything else have to get from somewhere else.
        if (level<this.levels.size()) {
            if (tempLevel != level) {
                resetMap();
                tempLevel = tempLevel + 1;
            }

            int y = 0;
            for (String eachString : this.layout) {
                int x = 0;
                for (int i = 0; i < eachString.length(); i++) {
                    if (eachString.charAt(i) == 'X') {
                        this.image(this.stonewall, x, y);
                    } else if (eachString.charAt(i) == 'B') {
                        this.image(this.brickwall, x, y);
                    } else if (eachString.charAt(i) == 'E') {
                        this.image(this.exit, x, y);
                    }
                    x = x+20;
                }
                y = y+20;
            }

            player.move(this);
            player.draw(this);
            if (player.shoot(this.fireballCooldown) == false) {
                player.progressBar(this);
            }
            for (Fireball f : fireballs) {
                f.move();
                f.draw(this);
            }
            for (Gremlin g : gremlins) {
                // Lagging code too much so commented for now.
                g.turn(this);
                if (g.shoot(this.slimeCooldown) == true) {
                    slimes.add(new Slime(g.getX(), g.getY(), g.getDir(), this));
                    g.shot();
                }
                g.draw(this);
            }
            for (Slime s : slimes) {
                s.move();
                s.draw(this);
            }
            this.text("Lives: ", 20, 690);
            for (int i = 0; i < lives; i++) {
                int x = (3+i)*20;
                this.image(wizard2, x, 675);
            }
            this.text("Level " + (level+1) + "/" + (this.levels.size()), 170, 690);
        }
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
