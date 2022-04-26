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
                        Thread.sleep(160);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
    }
}
