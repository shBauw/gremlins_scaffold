package gremlins;

public class Fireball extends Projectile {

    public Fireball(int x, int y, int dir, App app) {
        super(x, y, dir);
        this.sprite = app.fireball;
    }
}
