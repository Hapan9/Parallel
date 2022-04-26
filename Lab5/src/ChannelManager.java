import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

public class ChannelManager<Worker extends ChannelWorker<Task>, Task extends ChannelTask> {
    private final ExecutorService executor = Executors.newFixedThreadPool(8);

    private final QueueStorage<Task> storage;
    private final ConcurrentHashMap<ChannelWorker<Task>, Future<?>> workers = new ConcurrentHashMap<>();

    public ChannelManager(QueueStorage<Task> storage, List<ChannelWorker<Task>> workers) {
        this.storage = storage;
        for (var worker : workers) {
            this.workers.put(worker, CompletableFuture.completedFuture(null));
        }
    }

    public void run() {
        while (true){
            Task task = null;
            try {
                task = storage.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Optional<Map.Entry<ChannelWorker<Task>, Future<?>>> optional;
            do {
                optional = workers.entrySet().stream().filter(x -> x.getValue().isDone()).findFirst();
            } while (optional.isEmpty());

            var worker = optional.get().getKey();

            worker.setTask(task);
            var future = executor.submit(worker);
            workers.put(worker, future);
        }
    }
}
