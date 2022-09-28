package gremlins;

public abstract class Projectile extends Generic {

    protected int speed = 4;

    public Projectile(int x, int y, int dir) {
        super(x,y,dir);
    }

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
