package gremlins;

public class brickTile extends Generic{
    /**
     * Initialising object
     * @param x x-coordinate
     * @param y y-coordinate
     * @param dir dir used as a couter
     */
    public brickTile(int x, int y, int dir) {
        super(x, y, dir);
    }

    /**
     * Changing sprite during destruction
     * @param app used to get new sprites
     * @return boolean, whether change is ongoing or not
     */
    public boolean change(App app) {
        this.dir += 1;
        if (this.dir < 5) {
            this.sprite = app.brickwall0;
        } else if (this.dir < 9) {
            this.sprite = app.brickwall1;
        } else if (this.dir < 13) {
            this.sprite = app.brickwall2;
        } else if (this.dir < 17) {
            this.sprite = app.brickwall3;
        } else {
            return false;
        }
        return true;
    }
}
