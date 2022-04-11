import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCounter implements Counter {
    private int counter = 0;
    private final Lock lock = new ReentrantLock();

    @Override
    public void increment() {
        lock.lock();
        counter++;
        lock.unlock();
    }

    @Override
    public void decrement() {
        lock.lock();
        counter--;
        lock.unlock();
    }

    @Override
    public int getValue() {
        return counter;
    }
}
