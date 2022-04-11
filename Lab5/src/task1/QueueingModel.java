package task1;

import java.util.Hashtable;
import java.util.concurrent.*;

public class QueueingModel {
    private static final int QUEUE_MAX_SIZE = 512;
    private static final int CHANNELS_MAX_COUNT = 8;

    private final ExecutorService executor = Executors.newFixedThreadPool(CHANNELS_MAX_COUNT);
    private final BlockingQueue<Client> queue = new LinkedBlockingQueue<>();
    private final Hashtable<ServingChannel, Future<?>> channels = new Hashtable<>();

    public QueueingModel(){
        for (int i = 0; i < CHANNELS_MAX_COUNT; i++) {
            channels.put(new SleepServingStrategy(), null);
        }
    }

    public void add(Client client) throws InterruptedException {
        if (queue.size() < QUEUE_MAX_SIZE) {
            queue.put(client);
        } else {
            System.out.println("Queue was full - object was not served.");
        }
    }

    public void serve() throws InterruptedException {
        while (true){
            var client = queue.take();

            while (channels.values().stream().noneMatch(Future::isDone)) { }

            Future<?> future = executor.submit(client);
            future.isDone();
        }
    }
}
