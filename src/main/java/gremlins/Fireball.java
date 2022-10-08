package gremlins;

public class Fireball extends Projectile {
    /**
     * Used to initialise fireball
     * @param x coordinate
     * @param y coordinate
     * @param dir of movement
     * @param app to select sprite
     */
    public Fireball(int x, int y, int dir, App app) {
        super(x, y, dir);
        this.sprite = app.fireball;
    }
}
