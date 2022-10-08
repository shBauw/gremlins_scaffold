package gremlins;

public abstract class Projectile extends Generic {
    // Define constant speed of projectile
    protected int speed = 4;

    /**
     * Initialise projective
     * @param x coordinate
     * @param y coordinate
     * @param dir direction of movement
     */
    public Projectile(int x, int y, int dir) {
        super(x,y,dir);
    }

    /**
     * Move in decided direction each turn
     */
    public void move() {
        if (dir == 2) {
            this.y -= speed;
        } else if (dir == 0) {
            this.x -= speed;
        } else if (dir == 1) {
            this.x += speed;
        } else if (dir == 3) {
            this.y += speed;
        }
    }
}
