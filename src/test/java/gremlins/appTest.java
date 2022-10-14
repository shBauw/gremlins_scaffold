package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class appTest {
    App app;
    String[] args = {"hi", "hello"};

    @BeforeEach
    public void setup() {
        app = new App();
        app.main(args);
    }

    @Test
    public void make() {
        assertNotNull(app);
    }

    @Test
    public void window() {

    }
}
