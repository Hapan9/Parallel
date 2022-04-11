public class SimpleCounter implements Counter {
    private int counter = 0;

    public void increment() {
        counter++;
    }

    public void decrement() {
        counter--;
    }

    public int getValue() {
        return counter;
    }
}
