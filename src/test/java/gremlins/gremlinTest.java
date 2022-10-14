package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class gremlinTest {
    App app;
    Gremlin g;
    String[] args = {"test"};

    @BeforeEach
    public void setup() {
        app = new App();
        app.main(args);
    }

    @Test
    public void create() {
        g = new Gremlin(20, 20, 1, app);
        assertNotNull(g);
    }
}
