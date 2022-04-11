package task1take2;

import java.util.Random;

public class Producer {
    private final QueueStorage<ChannelTask> storage;
    private final Random random = new Random();

    public Producer(QueueStorage<ChannelTask> storage) {
        this.storage = storage;
    }

    public void produce() {
        for (int i = 0; i < 1000; i++){
            System.out.println("Producing task " + i);
            storage.addTask(new ChannelTask() { });
            try {
                Thread.sleep((long)(Math.abs(random.nextGaussian()) * 5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
