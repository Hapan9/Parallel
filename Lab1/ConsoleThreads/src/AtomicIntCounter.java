import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntCounter implements Counter {
    private final AtomicInteger integer = new AtomicInteger(0);
    @Override
    public void increment() {
        integer.incrementAndGet();
    }

    @Override
    public void decrement() {
        integer.decrementAndGet();
    }

    @Override
    public int getValue() {
        return integer.get();
    }
}
