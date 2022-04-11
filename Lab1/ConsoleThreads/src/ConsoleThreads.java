import java.util.concurrent.atomic.AtomicInteger;

public class ConsoleThreads {
    public static void main(String[] args) throws InterruptedException {
        counter();
    }

    private static void counter() throws InterruptedException {
        var counter = new AtomicIntCounter();
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
        System.out.println("Current thread: " + counter.getValue());
    }

    private void characters() {
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
