package gremlins;

public class Player extends Being{
    /**
     * Initialise player object
     * @param x coordinate
     * @param y coordinate
     * @param dir direction for sprite image
     * @param app for selecting initial sprite
     */
    public Player(int x, int y, int dir, App app) {
        super(x, y, dir);
        this.speed = 2;
        this.sprite = app.wizard2;
    }

    /**
     * Change sprite based on direction of movement
     * @param dir direction of movement
     * @param app sprite to use
     */
    public void setMovement(int dir, App app) {
        if ((this.x % 20 == 0) && (this.y % 20 == 0)) {
            this.dir = dir;
            if (dir == 0) {
                this.sprite = app.wizard0;
            } else if (dir == 1) {
                this.sprite = app.wizard1;
            } else if (dir == 2) {
                this.sprite = app.wizard2;
            } else if (dir == 3) {
                this.sprite = app.wizard3;
            }
            this.stopMove = 0;
        }
    }
    
    /**
     * Create and update progressbar if fireball on cooldown
     * @param app used for drawing
     */
    public void progressBar(App app) {
        app.rect(600,678,100,12);
        float len = 96*((float) this.lastShot/(app.fireballCooldown*60));
        app.fill(0);
        app.rect(602,680,len,8);
        app.fill(255);
    }
}
