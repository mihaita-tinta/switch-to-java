import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class Lesson2_1_CompletableFutureTest {

    @Test
    public void test_completableFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<String> promise = new CompletableFuture<>();

        Executors.newCachedThreadPool()
                .submit(() -> {
            Thread.sleep(500);
            promise.complete("Hello");
            return null;
        });

        assertEquals("Hello", promise.get());
    }

    @Test
    public void test_supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test - long running task, thread: " + Thread.currentThread().getName());
            return "test";
        });


        String result = future.get();// wait for the long running task to complete
        assertEquals("test", result);
    }
    @Test
    public void test_handle() {
        String finalResult = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test_supplyAsync_error - this thing will blow up, thread: " + Thread.currentThread().getName());
            throw new IllegalStateException("Blow up");
        })
        .handle((result, error) -> {
            if (error != null) {
                System.out.println("test_handle - we knew this won't work, thread: " + Thread.currentThread().getName());
            }
            return "default-result";
        })
        .thenApply(result -> {
            System.out.println("test_handle - result: " + result);
            return "combined-" + result;
        })
        .join();

        assertEquals("combined-default-result", finalResult);
    }

    @Test
    public void test_supplyAsync_exceptionally() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test_supplyAsync_error - this thing will blow up, thread: " + Thread.currentThread().getName());
            throw new IllegalStateException("Blow up");
        })
        .thenApply(result -> {
            Assert.fail();
            return result;
        })
        .exceptionally(throwable -> {
            System.out.println("test_supplyAsync_error - exceptionally: " + throwable + ", thread: " + Thread.currentThread().getName());
            return null;
        });
    }

    @Test
    public void test_compose() throws ExecutionException, InterruptedException {


        String result = CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("test_compose 0 - long running task, thread: " + Thread.currentThread().getName());
                    return "test";
                })
                .thenCompose((firstResult) -> {
                    return CompletableFuture.supplyAsync(() -> {
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("test_compose 1 - long running task, thread: " + Thread.currentThread().getName());
                        return firstResult + "_java";
                    });
                })
        .get();

        assertEquals("test_java", result);
    }

    @Test
    public void test_allOf_no_result() {


        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
                        try {
                            Thread.sleep(2000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("test_compose 0 - long running task, thread: " + Thread.currentThread().getName());
                        return "test";
                    });

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("test_compose 1 - long running task, thread: " + Thread.currentThread().getName());
                        return "java";
                    });

        CompletableFuture all = CompletableFuture.allOf(task1, task2); // wait for all tasks to complete
        assertNull(all.join());
    }

    @Test
    public void test_allOf_with_result() {

        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("test_compose 0 - long running task, thread: " + Thread.currentThread().getName());
            return "test";
        });

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("test_compose 1 - long running task, thread: " + Thread.currentThread().getName());
            return "java";
        });

        String result = Stream.of(task1, task2)
                .map(CompletableFuture::join)
                .collect(Collectors.joining("_"));
        assertEquals("test_java", result);
    }
}
