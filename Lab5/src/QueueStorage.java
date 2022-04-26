import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueStorage<Task extends ChannelTask> {
    private static final int MAX_QUEUE_SIZE = 500;
    private final BlockingQueue<Task> queue = new LinkedBlockingQueue<>();

    private int deniesCount;
    private int processedCount;

    public synchronized void addTask(Task task){
        if (queue.size() > MAX_QUEUE_SIZE){
            deniesCount++;
            return;
        }

        queue.add(task);
        processedCount++;

        notify();
    }

    public synchronized Task take() throws InterruptedException {
        while (queue.isEmpty()){
            wait();
        }
        return queue.take();
    }

    public int getDeniesCount() {
        return deniesCount;
    }

    public int getProcessedCount() {
        return processedCount;
    }

    public int getCurrentQueueSize(){
        return queue.size();
    }
}
