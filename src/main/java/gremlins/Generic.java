package gremlins;
import processing.core.PImage;

public abstract class Generic {
    protected int x;
    protected int y;
    protected int dir;
    protected PImage sprite;

    public Generic(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getDir() {
        return this.dir;
    }
    
    public void draw(App app) {
        app.image(this.sprite, this.x, this.y);
    }
}
