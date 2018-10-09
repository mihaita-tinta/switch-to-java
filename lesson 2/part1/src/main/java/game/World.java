package game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
public class World {
    private List<Ball> objects = new ArrayList<>();
    private final JFrame container;

    public World(JFrame container) {
        this.container = container;
        // TODO 2 add some objects into this world
    }

    // TODO 3 make this world more dynamic. All objects should react under the gravity

    public List<Ball> getObjects() {
        return objects;
    }
}
