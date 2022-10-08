package gremlins;

import java.util.*;

public class Gremlin extends Being{
    // Create random generator
    Random gen = new Random();
    // Define temporary movement variable
    private int tempMove;

    /**
     * Initialise gremlin
     * @param x coordinate
     * @param y coordinate
     * @param dir direction
     * @param app for sprite
     */
    public Gremlin(int x, int y, int dir, App app) {
        super(x, y, dir);
        this.dir = gen.nextInt(4);
        this.speed = 1;
        this.sprite = app.gremlin;
        this.tempMove = 0;
    }

    /**
     * Change direction
     * @param app used to find the tiles surround gremlin
     */
    public void changer(App app){
        int checker = 0;
        if (app.tileAt(this.x, this.y-speed) != ' ') {
            checker += 1;
        } 
        if (app.tileAt(this.x-speed, this.y) != ' ') {
            checker += 1;
        } 
        if (app.tileAt(this.x+20, this.y) != ' ') {
            checker += 1;
        } 
        if (app.tileAt(this.x, this.y+20) != ' ') {
            checker += 1;
        }


        if (checker == 3) {
            this.dir = this.tempMove;
        } else {
            this.dir = gen.nextInt(4);
            while (this.tempMove == this.dir) {
                this.dir = gen.nextInt(4);
            }
        }  
    }

    /**
     * Set a new temp direction
     * @param app used to call changing direction
     */
    public void direction(App app) {
        if (this.dir % 2 == 0) {
            this.tempMove = this.dir + 1;
        } else {
            this.tempMove = this.dir - 1;
        }
        changer(app);
    }
    
    /**
     * Respawn gremlin away from player
     * @param app plug into new temp direction and changing direction
     * @param player to find player coordinates
     */
    public void respawn(App app, Player player) {
        this.x = gen.nextInt(35)*20;
        this.y = gen.nextInt(32)*20;
        direction(app);
        double distance = Math.sqrt((this.y - player.getY()) * (this.y - player.getY()) + (this.x - player.getX()) * (this.x - player.getX()));
        while ((app.tileAt(this.x, this.y) != ' ') || (distance < 10)) {
            this.x = gen.nextInt(35)*20;
            this.y = gen.nextInt(32)*20;
            changer(app);
        }
    }

    /**
     * Complete turn through moving
     * @param app used for plugging into other functions
     */
    public void turn(App app) {
        if (move(app) == false) {
            direction(app);
            while (move(app) == false) {
                changer(app);
            }
        }
    }
}
