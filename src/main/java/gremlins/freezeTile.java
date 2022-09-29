package gremlins;
import java.util.*;

public class freezeTile extends Generic{
    public int broken;
    Random gen = new Random();

    public freezeTile(int x, int y, int dir, App app) {
        super(x, y, dir);
        this.sprite = app.freeze;
        broken();
    }

    public boolean onTile(Player player, App app) {
        if ((app.grid(player.getX()) == app.grid(this.x)) && (app.grid(player.getY()) == app.grid(this.y))) {
            broken();
            return true;
        } else {
            return false;
        }
    }

    public void broken() {
        broken = gen.nextInt(300);
    }

    public void draw(App app) {
        if (broken > 600) {
            app.image(this.sprite, this.x, this.y);
        } else {
            broken += 1;
        }
    }
}
