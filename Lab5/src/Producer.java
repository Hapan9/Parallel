import java.util.Random;

public class Producer {
    private final QueueStorage<ChannelTask> storage;
    private final Random random = new Random();

    public Producer(QueueStorage<ChannelTask> storage) {
        this.storage = storage;
    }

    public void produce() {
        for (; ;){
            storage.addTask(new ChannelTask() { });
            try {
                Thread.sleep((long)(Math.abs(random.nextGaussian()) * 10));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
