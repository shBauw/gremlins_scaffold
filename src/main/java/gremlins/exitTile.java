package gremlins;

public class exitTile extends Tile {
    /**
     * Initialising object
     * @param x x-coordinate
     * @param y y-coordinate
     * @param dir dir used as a couter, not used in this case
     * @param app used to initialise sprite
     */
    public exitTile(int x, int y, int dir, App app) {
        super(x, y, dir);
        this.sprite = app.exit;
    }
}
