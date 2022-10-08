package gremlins;

public class Slime extends Projectile{
    /**
     * Initialise slime projectile
     * @param x coordinate
     * @param y coordinate
     * @param dir direction of movement
     * @param app to select sprite
     */
    public Slime(int x, int y, int dir, App app) {
        super(x, y, dir);
        this.sprite = app.slime;
    }
}
