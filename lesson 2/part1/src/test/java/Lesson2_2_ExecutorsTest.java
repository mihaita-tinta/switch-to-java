import org.junit.Test;

import java.util.concurrent.*;

public class Lesson2_2_ExecutorsTest {


    @Test
    public void testNewCachedThreadPool() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(3);
        Runnable task = () -> {
            System.out.println("testNewCachedThreadPool - thread: " + Thread.currentThread().getName());
            latch.countDown();

        };

        executor.submit(task);
        executor.submit(task);
        executor.submit(task);
        latch.await(1, TimeUnit.SECONDS);


    }

    @Test
    public void testFixedThreadPool() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        CountDownLatch latch = new CountDownLatch(3);
        Runnable task = () -> {
            System.out.println("testFixedThreadPool - thread: " + Thread.currentThread().getName());
            latch.countDown();

        };

        executor.submit(task);
        executor.submit(task);
        executor.submit(task);
        latch.await(1, TimeUnit.SECONDS);

    }

    @Test
    public void testScheduled() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

        CountDownLatch latch = new CountDownLatch(3);
        Runnable task = () -> {
            System.out.println("testScheduled - thread: " + Thread.currentThread().getName());
            latch.countDown();

        };

        executor.scheduleAtFixedRate(task, 0, 100, TimeUnit.MILLISECONDS);
        latch.await(1, TimeUnit.SECONDS);

    }
}
