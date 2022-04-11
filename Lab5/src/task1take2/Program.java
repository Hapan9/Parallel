package task1take2;

import java.util.ArrayList;
import java.util.List;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        QueueStorage<ChannelTask> storage = new QueueStorage<>();
        List<ChannelWorker<ChannelTask>> workers = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            workers.add(new ChannelWorker<>() {
                @Override
                public void setTask(ChannelTask task) {
                }

                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //System.out.println("Processed task");
                }
            });
        }
        ChannelManager<ChannelWorker<ChannelTask>, ChannelTask> manager = new ChannelManager<>(storage, workers);
        Producer producer = new Producer(storage);

        (new Thread(manager::run)).start();
        var t = new Thread(producer::produce);
        var s = new StorageStatistics<>(storage);
        t.start();
        s.start();
        t.join();
        System.out.println(storage.getProcessedCount());
        System.out.println(storage.getDeniesCount());
        System.out.println("Denies possibility: " + (double) storage.getDeniesCount() / (storage.getDeniesCount() + storage.getProcessedCount()));
        System.out.println("Avg queue size: " + s.getAverageQueueSize());
    }
}
