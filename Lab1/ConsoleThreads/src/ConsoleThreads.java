import java.util.concurrent.atomic.AtomicInteger;

public class ConsoleThreads {
    public static void main(String[] args) throws InterruptedException {
        counter(new SimpleCounter());
        //counter(new LockCounter());
        //counter(new SynchronizedCounter());
        //counter(new SynchronizedObjectCounter());
        //counter(new SimpleCounter());
        //counter(new AtomicIntCounter());
        characters();
    }

    private static void counter(Counter counter) throws InterruptedException {
        var t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        });
        var t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.decrement();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Current value: " + counter.getValue());
    }

    private static void characters() {
        var sync = new Object();
        AtomicInteger state = new AtomicInteger();
        var firstThread = new Thread(() -> {
            synchronized (sync) {
                while (true) {
                    if (state.get() == 0) {
                        System.out.print("-");
                        state.set(1);
                        sync.notify();
                    } else {
                        try {
                            sync.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        var secondThread = new Thread(() -> {
            synchronized (sync) {
                while (true) {
                    if (state.get() == 1) {
                        System.out.print("|");
                        state.set(0);
                        sync.notify();
                    } else {
                        try {
                            sync.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        firstThread.start();
        secondThread.start();
    }
}
