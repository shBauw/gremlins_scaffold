package gremlins;

public class exitTile extends Generic {
    public exitTile(int x, int y, int dir, App app) {
        super(x, y, dir);
        this.sprite = app.exit;
    }

    public boolean onTile(Player player, App app) {
        if ((app.grid(player.getX()) == app.grid(this.x)) && (app.grid(player.getY()) == app.grid(this.y))) {
            return true;
        } else {
            return false;
        }
    }
}
