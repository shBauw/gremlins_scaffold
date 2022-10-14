package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class playerTest {

    App app;
    String[] args = {"hi", "hello"};

    @BeforeEach
    public void setup() {
        app = new App();
        app.main(args);
    }

    @Test
    void playerOnTile() {
        app.player = new Player(20,20,2,app);
        app.player.setMovement(0,app);
        app.player.setMovement(1,app);
        app.player.setMovement(2,app);
        app.player.setMovement(3,app);
        app.player.setMovement(4,app);
        app.player.down();
        app.player.up();
        app.player.right();
        app.player.stopMove();
        app.player.move(app);
        app.player.left();
        app.player.move(app);
        app.player.shot();
    }

    @Test
    void playerOffTile() {
        app.player = new Player(10,10,2,app);
        app.player.setMovement(1, app);
        app.player = new Player(20,10,2,app);
        app.player.setMovement(1, app);
        app.player = new Player(10,20,2,app);
        app.player.setMovement(1, app);
        assertEquals(10, app.player.getX());
        assertEquals(20, app.player.getY());
        assertEquals(2, app.player.getDir());
    }
}
