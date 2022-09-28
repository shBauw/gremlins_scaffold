package gremlins;

public class Slime extends Projectile{

    public Slime(int x, int y, int dir, App app) {
        super(x, y, dir);
        this.sprite = app.slime;
    }
}
