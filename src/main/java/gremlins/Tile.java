package gremlins;

public class Tile extends Generic {
    /**
     * Initialising object
     * @param x x-coordinate
     * @param y y-coordinate
     * @param dir dir used as a couter, not used in this case
     */
    public Tile(int x, int y, int dir) {
        super(x, y, dir);
    }

    /**
     * Checking whether on tile
     * @param player used to get positions
     * @param app used to use grid functio 
     * @return boolean whether on tile
     */
    public boolean onTile(Player player, App app) {
        if ((app.grid(player.getX()) == app.grid(this.x)) && (app.grid(player.getY()) == app.grid(this.y))) {
            return true;
        } else {
            return false;
        }
    }
}
