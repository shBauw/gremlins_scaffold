package gremlins;

public class Fireball {
    private int speed = 4;
    private int x;
    private int y;
    private int dir;

    public Fireball(int x, int y, int dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void move() {
        if (dir == 2) {
            this.y -= speed;
        } else if (dir == 0) {
            this.x -= speed;
        } else if (dir == 1) {
            this.x += speed;
        } else {
            this.y += speed;
        }
    }

    public void draw(App app) {
        app.image(app.fireball, this.x, this.y);
    }

}
