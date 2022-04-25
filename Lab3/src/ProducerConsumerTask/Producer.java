package ProducerConsumerTask;
import java.util.Random;

public class Producer implements Runnable {
    private Drop drop;
    private int[] importantInfo;

    public Producer(Drop drop, int[] importantInfo) {
        this.drop = drop;
        this.importantInfo = importantInfo;
    }

    public void run() {
        Random random = new Random();

        for (int i = 0;
             i < importantInfo.length;
             i++) {
            drop.put(String.valueOf(importantInfo[i]));
            try {
                Thread.sleep(random.nextInt(5));
            } catch (InterruptedException e) {}
        }
        drop.put("DONE");
    }
}