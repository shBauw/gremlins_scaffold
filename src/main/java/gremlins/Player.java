package gremlins;

import java.util.*;

public class Player {
    private int x;
    private int y;
    private int movement;
    private int speed = 2;

    private int shooting;
    private int stopMove;

    public List<Fireball> fireballs = new ArrayList<Fireball>();

    // Can return these 3 to use when drawing
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.movement = 2;
        this.shooting = 0;
        this.stopMove = 0;
    }

    public void up() {
        this.y -= speed;
    }
    public void left() {
        this.x -= speed;
    }
    public void right() {
        this.x += speed;
    }
    public void down() {
        this.y += speed;
    }

    public void move(App app) {
        if (this.stopMove == 1) {
            stopMovement();
        } else if (this.movement == 2) {
            if (app.tileAt(this.x, this.y-2) == ' ') {
                up();
            }
        } else if (this.movement == 0) {
            if (app.tileAt(this.x-2, this.y) == ' ') {
                left();
            }
        } else if (this.movement == 1) {
            if (app.tileAt(this.x+20, this.y) == ' ') {
                right();
            }
        } else if (this.movement == 3) {
            if (app.tileAt(this.x, this.y+20) == ' ') {
                down();
            }
        }
    }


    public void shoot(int cooldown) {
        if (this.shooting == 0) {
            this.shooting = 1;
            fireballs.add(new Fireball(this.x, this.y, this.movement));
            try {
                Thread.sleep(cooldown);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            this.shooting = 0;
        }
    }

    public void setMovement(int dir) {
        if ((this.x % 20 == 0) && (this.y % 20 == 0)) {
            this.movement = dir;
            this.stopMove = 0;
        }
    }


    public void stopMove() {
        this.stopMove = 1;
    }

    public void stopMovement() {
        if ((this.x % 20 != 0) || (this.y % 20 != 0)) {
            if (this.movement == 0) {
                left();
            } else if (this.movement == 1) {
                right();
            } else if (this.movement == 2) {
                up();
            } else {
                down();
            }
        }
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    public void draw(App app) {
        for (Fireball f: fireballs) {
            f.move();
            f.draw(app);
        }
        app.image(app.wizard2, this.x, this.y);
    }
}
