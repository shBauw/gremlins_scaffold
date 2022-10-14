package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class createTests {
    App app;
    exitTile e;
    Fireball f;
    Slime s;
    String[] args = {"hi", "hello"};

    @BeforeEach
    public void setup() {
        app = new App();
        app.main(args);
    }

    @Test
    public void create() {
        e = new exitTile(10, 10, 0, app);
        f = new Fireball(10, 10, 1, app);
        s = new Slime(10, 10, 1, app);

        f.move();
        f = new Fireball(10, 10, 2, app);
        f.move();
        f = new Fireball(10, 10, 0, app);
        f.move();
        f = new Fireball(10, 10, 3, app);
        f.move();
        f = new Fireball(10, 10, 4, app);
        f.move();
        
        assertNotNull(e);
        assertNotNull(f);
        assertNotNull(s);
    }
}
