package gremlins;

import java.lang.Math;

public abstract class Being extends Generic {
    // Define hereditary variables
    protected int lastShot;
    protected int speed;
    protected int stopMove;

    /**
     * Initialise objects
     * @param x coordinate
     * @param y coordinate
     * @param dir used for direction
     */
    public Being(int x, int y, int dir) {
        super(x, y, dir);
        this.lastShot = 9999;
        this.stopMove = 0;
    }

    /**
     * Movement up
     */
    public void up() {
        this.y -= speed;
    }
    /**
     * Movement left
     */
    public void left() {
        this.x -= speed;
    }
    /**
     * Movement right
     */
    public void right() {
        this.x += speed;
    }
    /**
     * Movement down
     */
    public void down() {
        this.y += speed;
    }

    /**
     * Start stopping movement
     */
    public void stopMove() {
        this.stopMove = 1;
    }
    /**
     * Cleanly stopping movement
     */
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

    /**
     * Resetting cooldown when shot
     */
    public void shot() {
        this.lastShot = 0;
    }
    /**
     * Checking if possible to shoot
     * @param cooldown cooldown from config
     * @return boolean whether can shoot or not
     */
    public boolean shoot(float cooldown) {
        if (Math.round(cooldown*60) <= this.lastShot) {
            return true;
        }
        return false;
    }

    /**
     * Movement if open space based on map
     * @param app used to find the tile to move onto
     * @return true if moved, false if not
     */
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

    /**
     * Modified drawing to increment last shot as well
     * @param app used for drawing
     */
    public void draw(App app) {
        this.lastShot += 1;
        app.image(this.sprite, this.x, this.y);
    }
}
