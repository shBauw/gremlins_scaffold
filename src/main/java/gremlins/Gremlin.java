package gremlins;

import java.util.*;

public class Gremlin {
    private int x;
    private int y;
    private int movement;
    private int shooting;
    private List<Slime> slimes = new ArrayList<Slime>();
    Random gen = new Random();

    public Gremlin(int x, int y) {
        this.x = x;
        this.y = y;
        this.shooting = 0;
    }

    // New direction
    public void direction() {
        this.movement = gen.nextInt(4);
    }
    // Automated movement function
    public void move(App app) {
        Boolean flag = true;
        while (flag == true) {
            if (this.movement == 0) {
                if (app.tileAt(this.x, this.y-1) == ' ') {
                    this.y -= 1;
                    flag = false;
                } else {
                    flag = true;
                }
            } else if (this.movement == 1) {
                if (app.tileAt(this.x-1, this.y) == ' ') {
                    this.x -= 1;
                    flag = false;
                } else {
                    flag = true;
                }
            } else if (this.movement == 2) {
                if (app.tileAt(this.x+20, this.y) == ' ') {
                    this.x += 1;
                    flag = false;
                } else {
                    flag = true;
                }
            } else if (this.movement == 3) {
                if (app.tileAt(this.x, this.y+20) == ' ') {
                    this.y += 1;
                    flag = false;
                } else {
                    flag = true;
                }
            }
            if (flag == true) {
                direction();
            }
        }
    }

    public void shoot(int cooldown) {
        if (this.shooting == 0) {
            this.shooting = 1;
            slimes.add(new Slime(this.x, this.y, this.movement));
            try {
                Thread.sleep(cooldown);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            this.shooting = 0;
        }
    }

    public void draw(App app) {
        for (Slime s: slimes) {
            s.move();
            s.draw(app);
        }
        app.image(app.gremlin, this.x, this.y);
    }
}
