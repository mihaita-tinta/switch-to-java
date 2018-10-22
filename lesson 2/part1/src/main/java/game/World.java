package game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class World {
    private List<Ball> objects = new ArrayList<>();
    private final JFrame container;

    public World(JFrame container) {
        this.container = container;
        // TODO 2 add some objects into this world
        IntStream.range(0, 10).mapToObj(i -> Ball.random())
                              .forEach(objects ::add);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        Runnable gravityRunnable = () -> {
            System.out.println("gravity - action started");
            objects.parallelStream().forEach(ball -> {
                if (ball.getY() + ball.getHeight() < container.getHeight() - 30) {
                    ball.setY(ball.getY() + 5);
                }

            });
            container.repaint();
        };
        scheduledExecutorService.scheduleAtFixedRate(gravityRunnable,1, 50, TimeUnit.MILLISECONDS);
    }

    public void add(Ball b) {
        this.objects.add(b);
    }

    // TODO 3 make this world more dynamic. All objects should react under the gravity

    public List<Ball> getObjects() {
        return objects;
    }
}
