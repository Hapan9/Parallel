import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FoxStrategy implements MultiplicationStrategy {
    private final int threadsCount;
    private FoxWorker[][] workers;

    private final int[][] first;
    private final int[][] result;

    private int blockRank = 250;
    private final int horizontalBlocksCount;
    private final int verticalBlocksCount;

    public FoxStrategy(int[][] first, int[][] second, int threadsCount, int blockRank) {
        this.blockRank = blockRank;
        this.first = first;
        this.threadsCount = threadsCount;
        this.result = new int[first.length][second[0].length];
        horizontalBlocksCount = first[0].length / blockRank;
        verticalBlocksCount = first.length / blockRank;
        workers = new FoxWorker[verticalBlocksCount][horizontalBlocksCount];
        for (int i = 0; i < workers.length; i++) {
            for (int j = 0; j < workers[0].length; j++) {
                workers[i][j] = new FoxWorker(i, j, blockRank, first, second, result);
            }
        }
    }

    @Override
    public int getIterationsCount() {
        return first[0].length / blockRank;
    }

    @Override
    public void nextIteration() {
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        for (var array : workers) {
            for (FoxWorker worker : array){
                executor.execute(worker);
            }
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Result getResult() {
        return new Result(result);
    }
}
