public class SynchronizedObjectCounter implements Counter {
    private int counter = 0;

    @Override
    public void increment() {
        synchronized (this) {
            counter++;
        }
    }

    @Override
    public void decrement() {
        synchronized (this) {
            counter--;
        }
    }

    @Override
    public int getValue() {
        return counter;
    }
}
