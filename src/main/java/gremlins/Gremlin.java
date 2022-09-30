package gremlins;

import java.util.*;

public class Gremlin extends Being{
    Random gen = new Random();
    private int tempMove;

    public Gremlin(int x, int y, int dir, App app) {
        super(x, y, dir);
        this.dir = gen.nextInt(4);
        this.speed = 1;
        this.sprite = app.gremlin;
        this.tempMove = 0;
    }

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

    // New direction
    public void direction(App app) {
        if (this.dir % 2 == 0) {
            this.tempMove = this.dir + 1;
        } else {
            this.tempMove = this.dir - 1;
        }
        changer(app);
    }

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

    // Automated movement function
    public void turn(App app) {
        if (move(app) == false) {
            direction(app);
            while (move(app) == false) {
                changer(app);
            }
        }
    }
}
