import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class Lesson2_0_ThreadTest {


    @Test
    public void testCreatingThreads() {
//        TODO more info at: http://jcip.net/listings.html
        MyFirstThreadExtendingThread thread = new MyFirstThreadExtendingThread();
        thread.start();

        Runnable runnable = new MyFirstThread();
        runnable.run();

        Runnable lambdaRunnable = () -> {
            System.out.println("LambdaRunnable#run - start: " + Thread.currentThread().getName());
        };
        lambdaRunnable.run();
    }

    class MyFirstThreadExtendingThread extends Thread {

        @Override
        public void run() {
            System.out.println("MyFirstThreadExtendingThread#run - start: " + Thread.currentThread().getName());
        }
    }

    class MyFirstThread implements Runnable {

        @Override
        public void run() {
            System.out.println("MyFirstThread#run - start: " + Thread.currentThread().getName());
        }
    }

    @Test
    public void testSharedResource() throws InterruptedException {
        SharedResource obj = new SharedResource();
        Runnable r1 = () -> {
            IntStream.range(0, 1000)
                    .forEach(i -> {
                        obj.inc();
                        System.out.println("r1 - increment i:" + obj.i + ", thread: " + Thread.currentThread().getName());
                    });
            System.out.println("r1 - finished: " + obj.i);
        };
        Runnable r2 = () -> {
            IntStream.range(0, 1000)
                    .forEach(i -> {
                        obj.inc();
                        System.out.println("r2 - increment i:" + obj.i + ", thread: " + Thread.currentThread().getName());
                    });
            System.out.println("r2 - finished: " + obj.i);
        };
        new Thread(r1)
                .start();
        new Thread(r2)
                .start();
        Thread.sleep(1000L); // main tread needs to wait for the child threads

        assertEquals(2000, obj.i); // sometimes this statement fails because i++ means i = i + 1 and it is not an atomic operation
    }

    static class SharedResource {
        int i  = 0;

        public void inc() {
            i++;
        }
    }

    @Test
    public void testObjectLock() throws InterruptedException {
        Dog dog = new Dog();
        new Thread(() -> {
            try {
                dog.eat();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                System.out.println("dog - look for food . . .");
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("dog - found food . . .");
            dog.foundFood();
        })
        .start();
        Thread.sleep(5000L);
    }

    static class Dog {
        private boolean isSeeingFood = false;

        public void eat() throws InterruptedException {
            synchronized(this) {
                while(!isSeeingFood){
                    System.out.println("eat - looking for food . . .");
                    wait();
                }
            }
            isSeeingFood = false;
            System.out.println("eat - that was good!");
        }

        public void foundFood(){
            System.out.println("foundFood - is that a bone?");
            synchronized(this) {
                this.isSeeingFood = true;
                System.out.println("foundFood - I should it that!");
                notifyAll();
                System.out.println("foundFood - notified other threads!");
            }
        }

    }
    @Test
    public void testSharedResource_withLock() throws InterruptedException {
        SharedResourceWithLock obj = new SharedResourceWithLock();
        Runnable r1 = () -> {
            IntStream.range(0, 100)
                    .forEach(i -> {
                        obj.inc();
                        System.out.println("r1 - increment i:" + obj.i + ", thread: " + Thread.currentThread().getName());
                    });
            System.out.println("r1 - finished: " + obj.i);
        };
        Runnable r2 = () -> {
            IntStream.range(0, 100)
                    .forEach(i -> {
                        obj.inc();
                        System.out.println("r2 - increment i:" + obj.i + ", thread: " + Thread.currentThread().getName());
                    });
            System.out.println("r2 - finished: " + obj.i);
        };
        new Thread(r1)
                .start();
        new Thread(r2)
                .start();
        Thread.sleep(1000L); // main tread needs to wait for the child threads

        assertEquals(200, obj.i); // sometimes this statement fails because i++ means i = i + 1 and it is not an atomic operation
    }

    static class SharedResourceWithLock {
        private Object lock = new Object();
        int i  = 0;
        public void inc() {
           synchronized (lock) {
               i++;
           }
        }
    }


    @Test
    public void testSynchronizedSharedResource() throws InterruptedException {
        SynchronizedSharedResource obj = new SynchronizedSharedResource();
        Runnable r1 = () -> {
            IntStream.range(0, 100)
                    .forEach(i -> {
                        obj.inc();
                        System.out.println("r1 - increment i:" + obj.i + ", thread: " + Thread.currentThread().getName());
                    });
            System.out.println("r1 - finished: " + obj.i);
        };

        Runnable r2 = () -> {
            IntStream.range(0, 100)
                    .forEach(i -> {
                        obj.inc();
                        System.out.println("r2 - increment i:" + obj.i + ", thread: " + Thread.currentThread().getName());
                    });
            System.out.println("r2 - finished: " + obj.i);
        };
        new Thread(r1)
                .start();
        new Thread(r2)
                .start();
        Thread.sleep(1000L); // main tread needs to wait for the child threads

        assertEquals(200, obj.i); // sometimes this statement fails because i++ means i = i + 1 and it is not an atomic operation
    }

    static class SynchronizedSharedResource {
        int i  = 0;
        public synchronized void inc() {
            i++;
        }
    }

    @Test
    public void test_starvation_onlyFirstThreadGetsSomeWork() throws InterruptedException {
        StarvingDog rex = new StarvingDog();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                rex.eat();
                System.out.println("StarvingDog#eat - end: " + Thread.currentThread().getName());
            }).start();
        }
        Thread.sleep(5000L);
    }


    static class StarvingDog {
        public synchronized  void eat() { // TODO uncomment this to see all threads working, but we have no synchronization
            while(true) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("StarvingDog#eat, thread: " + Thread.currentThread().getName());
            }
        }
    }

    @Test
    public void test_livelock() throws InterruptedException {
        final Owner owner = new Owner();
        final Rex rex = new Rex();

        new Thread(() -> owner.giveFood(rex))
                .start();

        new Thread(() -> rex.bark(owner))
                .start();
        Thread.sleep(5000L);
    }

    class Owner {
        private boolean hasFood = false;

        public void giveFood(Rex dog) {
            while (!dog.isHasBarked()) {

                System.out.println("Owner: waiting for dog to bark");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            System.out.println("Owner: throw bone");

            this.hasFood = true;
        }

        public boolean isHasFood() {
            return hasFood;
        }
    }

    class Rex {
        private boolean hasBarked = false;

        public void bark(Owner owner) {

            while (!owner.isHasFood()) {

                System.out.println("Rex: waiting for owner to provide some food");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            System.out.println("Rex: bark!");

            this.hasBarked = true;
        }

        public boolean isHasBarked() {
            return hasBarked;
        }
    }

    @Test
    public void test_notAllThreadsCanFooAndBar() throws InterruptedException {
        BadDesignLock business = new BadDesignLock();

        for (int i = 0; i < 10; i++) {
            new Thread(business :: foo).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(business :: bar).start();
        }
        Thread.sleep(5000L);
        // FIXME how can you fix this?
    }

    class BadDesignLock {

        private Object lock1 = new Object();
        private Object lock2 = new Object();

        public void foo() {
            synchronized (lock1) {
                System.out.println("foo - got lock 1, thread: " + Thread.currentThread().getName());
                synchronized (lock2) {
                    System.out.println("foo - got lock 2, thread: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("foo - end, thread: " + Thread.currentThread().getName());
        }

        public void bar() {
            synchronized (lock2) {
                System.out.println("bar - got lock 2, thread: " + Thread.currentThread().getName());
                synchronized (lock1) {
                    System.out.println("bar - got lock 1, thread: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("bar - end, thread: " + Thread.currentThread().getName());
        }
    }
}
