package task1take2;

import java.util.ArrayList;

public class StorageStatistics<Task extends ChannelTask> extends Thread {
    private final QueueStorage<Task> storage;
    private ArrayList<Integer> measures = new ArrayList<>();

    public StorageStatistics(QueueStorage<Task> storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true){
            measures.add(storage.getCurrentQueueSize());
            System.out.println("===============");
            System.out.println("Avg queue size: " + getAverageQueueSize());
            System.out.println(storage.getProcessedCount());
            System.out.println(storage.getDeniesCount());
            System.out.println("Denies possibility: " + (double) storage.getDeniesCount() / (storage.getDeniesCount() + storage.getProcessedCount()));
            System.out.println("===============");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getAverageQueueSize(){
        int sum = 0;

        for (Integer measure : measures) {
            sum += measure;
        }

        return sum / measures.size();
    }
}
