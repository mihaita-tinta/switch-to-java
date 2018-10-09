package game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class World {
    private List<Ball> objects = new ArrayList<>();
    private final JFrame container;

    public World(JFrame container) {
        this.container = container;
        IntStream.range(0, 10)
                .forEach(i -> objects.add(Ball.random()));

        Executors.newScheduledThreadPool(2)
                .scheduleAtFixedRate(() -> clock(), 1, 200,  TimeUnit.MILLISECONDS);
    }

    public void addBall(int x, int y) {
        objects.add(Ball.random(x, y));
    }
    public void clock() {
        System.out.println("clock - tick tack");
        objects.stream().forEach(Ball::goDown);
        container.repaint();

    }

    public List<Ball> getObjects() {
        return objects;
    }
}
