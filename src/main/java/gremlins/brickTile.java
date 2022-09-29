package gremlins;

public class brickTile extends Generic{
    public brickTile(int x, int y, int dir) {
        super(x, y, dir);
    }

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

    public void draw(App app) {
        if (change(app) == true) {
            app.image(this.sprite, this.x, this.y);
        }
    }
}
