package gremlins;

import java.lang.Math;

public abstract class Being extends Generic {
    protected int lastShot;
    protected int speed;
    protected int stopMove;

    public Being(int x, int y, int dir) {
        super(x, y, dir);
        this.lastShot = 9999;
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

    public void stopMove() {
        this.stopMove = 1;
    }

    public void stopMovement() {
        if ((this.x % 20 != 0) || (this.y % 20 != 0)) {
            if (this.dir == 0) {
                left();
            } else if (this.dir == 1) {
                right();
            } else if (this.dir == 2) {
                up();
            } else if (this.dir == 3) {
                down();
            }
        }
    }

    public void shot() {
        this.lastShot = 0;
    }

    public boolean shoot(float cooldown) {
        if (Math.round(cooldown*60) <= this.lastShot) {
            return true;
        }
        return false;
    }

    public boolean move(App app) {
        if (this.stopMove == 1) {
            stopMovement();
            return false;
        } else if (this.dir == 2) {
            if (app.tileAt(this.x, this.y-speed) == ' ') {
                up();
            } else {
                return false;
            }
        } else if (this.dir == 0) {
            if (app.tileAt(this.x-speed, this.y) == ' ') {
                left();
            } else {
                return false;
            }
        } else if (this.dir == 1) {
            if (app.tileAt(this.x+20, this.y) == ' ') {
                right();
            } else {
                return false;
            }
        } else if (this.dir == 3) {
            if (app.tileAt(this.x, this.y+20) == ' ') {
                down();
            } else {
                return false;
            }
        }
        return true;
    }

    public void draw(App app) {
        this.lastShot += 1;
        app.image(this.sprite, this.x, this.y);
    }
}
