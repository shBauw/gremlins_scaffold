package gremlins;
import java.util.*;

public class freezeTile extends Tile {
    // Initialise random generator
    Random gen = new Random();

    /**
     * Initialise freezetile
     * @param x coordinate
     * @param y coordinate
     * @param dir used as a counter
     * @param app used for sprite
     */
    public freezeTile(int x, int y, int dir, App app) {
        super(x, y, dir);
        this.sprite = app.freeze;
        broken();
    }

    /**
     * If broken take a random number to help with generation
     */
    public void broken() {
        dir = gen.nextInt(300);
    }

    /**
     * Modified draw to see when to make it
     * @param app used for drawing
     */
    public void draw(App app) {
        if (dir > 600) {
            app.image(this.sprite, this.x, this.y);
        } else {
            dir += 1;
        }
    }
}
