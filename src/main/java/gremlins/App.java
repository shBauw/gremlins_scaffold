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
    public PImage brickwall0;
    public PImage brickwall1;
    public PImage brickwall2;
    public PImage brickwall3;
    public PImage freeze;
    public PImage bomb;
    

    public int lives;
    public JSONArray levels;
    public JSONObject currentLevel;
    public int level;
    public int tempLevel;
    public int won;
    public String[] layout;

    public Player player;
    public Gremlin Gremlin;
    public List<Gremlin> gremlins;
    public float fireballCooldown;
    public float slimeCooldown;

    public List<Fireball> fireballs;
    public List<Slime> slimes;
    public List<brickTile> broken;
    public exitTile exitTile;
    public freezeTile freezeTile;
    public int freezing;

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
        this.brickwall0 = loadImage(this.getClass().getResource("brickwall_destroyed0.png").getPath().replace("%20", " "));
        this.brickwall1 = loadImage(this.getClass().getResource("brickwall_destroyed1.png").getPath().replace("%20", " "));
        this.brickwall2 = loadImage(this.getClass().getResource("brickwall_destroyed2.png").getPath().replace("%20", " "));
        this.brickwall3 = loadImage(this.getClass().getResource("brickwall_destroyed3.png").getPath().replace("%20", " "));
        this.freeze = loadImage(this.getClass().getResource("freeze.png").getPath().replace("%20", " "));
        this.bomb = loadImage(this.getClass().getResource("bomb.png").getPath().replace("%20", " "));

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
        if (this.lives == 0 || this.won == 1) {
            JSONObject conf = loadJSONObject(new File(this.configPath));
            lives = conf.getInt("lives");
            this.level = 0;
            this.tempLevel = -1;
            this.won = 0;
        } else if (key == CODED) {
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
        } else if (key == 'r' || key == 'R') {
            this.lives -= 1;
            this.tempLevel -= 1;
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
        fireballs = new ArrayList<Fireball>();
        slimes = new ArrayList<Slime>();
        gremlins = new ArrayList<Gremlin>();
        broken = new ArrayList<brickTile>();

        this.freezing = 0;

        this.currentLevel = this.levels.getJSONObject(this.level);
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
                } else if (eachString.charAt(i) == 'E') {
                    exitTile = new exitTile(x, y, 0, this);

                    StringBuilder tempString = new StringBuilder(this.layout[y/20]);
                    tempString.setCharAt(x/20, ' ');
                    this.layout[y/20] = tempString.toString();
                } else if (eachString.charAt(i) == 'F') {
                    freezeTile = new freezeTile(x, y, 0, this);

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
     * Make my life easier
     */
    public int grid(int x) {
        int temp = x - (x%20);
        return temp/20;
    }

    /**
     * Get the block that is at the place
     */
    public char tileAt(int x, int y) {
        return this.layout[grid(y)].charAt(grid(x));
    }

    public void destroy(int x, int y) {
        broken.add(new brickTile(x, y, 0));

        StringBuilder tempString = new StringBuilder(this.layout[y/20]);
        tempString.setCharAt(x/20, 'D');
        this.layout[y/20] = tempString.toString();
    }

    /**
     * Check for collisions
     */
    public void collisions() {

        //Check for player on tiles

        if (exitTile.onTile(player, this)) {
            if (this.level + 1 == this.levels.size()) {
                this.won = 1;
            } else {
                this.level += 1;
            }
        } else if (freezeTile.onTile(player, this)) {
            this.freezing += 1;
        }

        // Do all the movements
        player.move(this);
        for (Fireball f : fireballs) {
            f.move();
        }

        for (Gremlin g : gremlins) {
            if (this.freezing == 0) {
                g.turn(this);
                if (g.shoot(this.slimeCooldown) == true) {
                    slimes.add(new Slime(g.getX(), g.getY(), g.getDir(), this));
                    g.shot();
                }
            }

            if ((grid(player.getX()) == grid(g.getX())) && (grid(player.getY()) == grid(g.getY()))) {
                this.lives -= 1;
                this.tempLevel -= 1;
            }
        }
        if (this.freezing != 0) {
            this.freezing += 1;
            this.rect(600,698,100,12);
            float len = 96*((float) this.freezing/(600));
            this.fill(153, 255, 255);
            this.rect(602,700,len,8);
            this.fill(255);
            
            if (this.freezing == 600) {
                this.freezing = 0;
            }
        }
        for (Slime s : slimes) {
            s.move();
        }

        // Check for collisions
        List<Fireball> toRemoveF = new ArrayList<Fireball>();
        List <Slime> toRemoveS = new ArrayList<Slime>();

        for (Fireball f : fireballs) {
            if (tileAt(f.getX(), f.getY()) != ' ') {
                if (tileAt(f.getX(), f.getY()) == 'B') {
                    int x = f.getX() - (f.getX()%20);
                    int y = f.getY() - (f.getY()%20);
                    destroy(x,y);
                } else if (tileAt(f.getX(), f.getY()) == 'V') {
                    int x = f.getX() - (f.getX()%20);
                    int y = f.getY() - (f.getY()%20);

                    StringBuilder tempString = new StringBuilder(this.layout[y/20]);
                    tempString.setCharAt(x/20, ' ');
                    this.layout[y/20] = tempString.toString();
                    
                    if (tileAt(x-20, y) == 'B') {
                        destroy(x-20,y);
                    }
                    if (tileAt(x-20, y-20) == 'B') {
                        destroy(x-20, y-20);
                    }
                    if (tileAt(x, y-20) == 'B') {
                        destroy(x, y-20);
                    }
                    if (tileAt(x+20, y-20) == 'B') {
                        destroy(x+20, y-20);
                    }
                    if (tileAt(x+20, y) == 'B') {
                        destroy(x+20, y);
                    }
                    if (tileAt(x+20, y+20) == 'B') {
                        destroy(x+20, y+20);
                    }
                    if (tileAt(x, y+20) == 'B') {
                        destroy(x, y+20);
                    }
                    if (tileAt(x-20, y+20) == 'B') {
                        destroy(x-20, y+20);
                    }


                    for (Slime s : slimes) {
                        if ((grid(x)-1 <= grid(s.getX())) && (grid(y)-1 <= grid(s.getY())) &&
                            (grid(x)+1 >= grid(s.getX())) && (grid(y)+1 >= grid(s.getY()))) {
                            toRemoveS.add(s);
                        }
                    }
                    for (Gremlin g : gremlins) {
                        if ((grid(x)-1 <= grid(g.getX())) && (grid(y)-1 <= grid(g.getY())) &&
                            (grid(x)+1 >= grid(g.getX())) && (grid(y)+1 >= grid(g.getY()))) {
                            g.respawn(this, player);
                        }
                    }
                    if ((grid(x)-1 <= grid(player.getX())) && (grid(y)-1 <= grid(player.getY())) &&
                        (grid(x)+1 >= grid(player.getX())) && (grid(y)+1 >= grid(player.getY()))) {
                        this.lives -= 1;
                        this.tempLevel -= 1;
                    }
                }
                toRemoveF.add(f);
            }
            for (Slime s : slimes) {
                if ((grid(f.getX()) == grid(s.getX())) && (grid(f.getY()) == grid(s.getY()))) {
                    if (toRemoveF.contains(f) == false) {
                        toRemoveF.add(f);
                    }
                    toRemoveS.add(s);
                }
            }
            for (Gremlin g : gremlins) {
                if ((grid(f.getX()) == grid(g.getX())) && (grid(f.getY()) == grid(g.getY()))) {
                    if (toRemoveF.contains(f) == false) {
                        toRemoveF.add(f);
                    }
                    g.respawn(this, player);
                }
            }
            
        }
        slimes.removeAll(toRemoveS);
        toRemoveS = new ArrayList<Slime>();
        fireballs.removeAll(toRemoveF);

        for (Slime s : slimes) {
            if (tileAt(s.getX(), s.getY()) != ' ') {
                toRemoveS.add(s);
            }
            if ((grid(player.getX()) == grid(s.getX())) && (grid(player.getY()) == grid(s.getY()))) {
                this.lives -= 1;
                this.tempLevel -= 1;
            }
        }
        slimes.removeAll(toRemoveS);

        // Update after check if destroyed
        List<brickTile> toRemoveB = new ArrayList<brickTile>();
        for (brickTile b : broken) {
            if (b.change(this) == false) {

                StringBuilder tempString = new StringBuilder(this.layout[b.getY()/20]);
                tempString.setCharAt(b.getX()/20, ' ');
                this.layout[b.getY()/20] = tempString.toString();
                
                toRemoveB.add(b);
            }
        }
        broken.removeAll(toRemoveB);
    }

    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        // If die: Could lower tempLevel by 1.
        background(182, 146, 109);
        // If level finished: Parse base of next level
        // If level not finished: Reparse unbreakable objects everything else have to get from somewhere else.
        if (lives == 0) {
            this.text("Game over", 300, 300);
            this.text("Press any key to restart or press Esc to quit", 300, 350);
        } else {
            if (this.won == 1) {
                this.text("You win", 300, 300);
                this.text("Press any key to restart or press Esc to quit", 300, 350);
            } else if (level < this.levels.size()) {
                if (tempLevel != -1) {
                    collisions();
                }
                if (this.tempLevel != level) {
                    resetMap();
                    this.tempLevel = this.tempLevel + 1;
                }

                int y = 0;
                for (String eachString : this.layout) {
                    int x = 0;
                    for (int i = 0; i < eachString.length(); i++) {
                        if (eachString.charAt(i) == 'X') {
                            this.image(this.stonewall, x, y);
                        } else if (eachString.charAt(i) == 'B') {
                            this.image(this.brickwall, x, y);
                        } else if (eachString.charAt(i) == 'V') {
                            this.image(this.bomb, x, y);
                        }
                        x = x+20;
                    }
                    y = y+20;
                }
                for (brickTile b : broken) {
                    b.draw(this);
                }

                exitTile.draw(this);
                freezeTile.draw(this);
                player.draw(this);
                if (player.shoot(this.fireballCooldown) == false) {
                    player.progressBar(this);
                }
                for (Fireball f : fireballs) {
                    f.draw(this);
                }
                for (Gremlin g : gremlins) {
                    g.draw(this);
                }
                for (Slime s : slimes) {
                    s.draw(this);
                }
                this.text("Lives: ", 20, 690);
                for (int i = 0; i < lives; i++) {
                    int x = (3+i)*20;
                    this.image(wizard2, x, 675);
                }
                this.text("Level " + (level+1) + "/" + (this.levels.size()), 170, 690);
                this.text("Esc: Quit, R: Respawn", 400, 690);
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}