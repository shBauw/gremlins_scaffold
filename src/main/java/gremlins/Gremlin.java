package gremlins;

import java.util.*;

public class Gremlin extends Being{
    Random gen = new Random();

    public Gremlin(int x, int y, int dir, App app) {
        super(x, y, dir);
        this.dir = gen.nextInt(4);
        this.speed = 1;
        this.sprite = app.gremlin;
    }

    public void changer(App app, int tempMove){
        int checker = 0;
        if (app.tileAt(this.x, this.y-speed) != ' ') {
            checker += 1;
        } else if (app.tileAt(this.x-speed, this.y) != ' ') {
            checker += 1;
        } else if (app.tileAt(this.x+20, this.y) != ' ') {
            checker += 1;
        } else if (app.tileAt(this.x, this.y+20) != ' ') {
            checker += 1;
        }

        if (checker == 3) {
            this.dir = tempMove;
        } else {
            this.dir = gen.nextInt(4);
            while (tempMove == this.dir) {
                this.dir = gen.nextInt(4);
            }
        }  
    }

    // New direction
    public void direction(App app) {
        int tempMove = 0;
        if (this.dir % 2 == 0) {
            tempMove = this.dir + 1;
        } else {
            tempMove = this.dir - 1;
        }

        changer(app, tempMove); 
    }

    public void respawn(App app) {

    }

    // Automated movement function
    public void turn(App app) {
        while (move(app) == false) {
            direction(app);
        }
    }
}
