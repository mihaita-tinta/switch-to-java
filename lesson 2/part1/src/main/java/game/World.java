package game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class World {
    private List<Ball> objects = new ArrayList<>();
    private final JFrame container;

    public World(JFrame container) {
        this.container = container;
        IntStream.range(0, 10)
                .mapToObj(i -> Ball.random())
                .forEach(objects :: add);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        Runnable gravityRunnable = () -> {
            System.out.println("gravity - action started");
            objects.parallelStream().forEach(ball -> {
                if(ball.getY() - ball.getHeight() < container.getHeight())
                    ball.setY(ball.getY() + 10);
            });
            container.repaint();
        };
        scheduledExecutorService.scheduleAtFixedRate(gravityRunnable, 1, 500, TimeUnit.MILLISECONDS);
    }

    public void add(Ball ball) {
        objects.add(ball);
    }

    // TODO 3 make this world more dynamic. All objects should react under the gravity

    public List<Ball> getObjects() {
        return objects;
    }
}
