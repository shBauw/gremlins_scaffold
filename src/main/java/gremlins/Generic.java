package gremlins;
import processing.core.PImage;

public abstract class Generic {
    // Define hereditary variables
    protected int x;
    protected int y;
    protected int dir;
    protected PImage sprite;

    /**
     * Initialise object
     * @param x coordinate
     * @param y coordinate
     * @param dir used as either direction or counter
     */
    public Generic(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    /**
     * Getter variables
     * @return initialised integers
     */
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getDir() {
        return this.dir;
    }
    
    /**
     * Basic draw
     * @param app used to draw
     */
    public void draw(App app) {
        app.image(this.sprite, this.x, this.y);
    }
}
