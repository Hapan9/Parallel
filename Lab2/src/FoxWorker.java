public class FoxWorker implements Runnable {
    private final int blockRank;
    private final int[][] first;
    private final int[][] second;
    private final int[][] result;

    private int firstBlockRow;
    private int firstBlockColumn;
    private int secondBlockRow;
    private int secondBlockColumn;

    private int blocksCount;

    public FoxWorker(int i, int j, int blockRank, int[][] first, int[][] second, int[][] result) {
        firstBlockRow = i;
        firstBlockColumn = i;
        secondBlockRow = i;
        secondBlockColumn = j;
        this.blockRank = blockRank;
        this.first = first;
        this.second = second;
        this.result = result;
        blocksCount = first.length / blockRank;
    }

    @Override
    public void run(){
        int[][] firstBlock = getBlock(first, firstBlockRow, firstBlockColumn);
        int[][] secondBlock = getBlock(second, secondBlockRow, secondBlockColumn);

        int[][] resultBlock = InlineStrategy.multiply(firstBlock, secondBlock).getMatrix();
        applyBlockToResult(resultBlock);

        firstBlockColumn = (firstBlockColumn + 1) % blocksCount;
        secondBlockRow = (secondBlockRow + 1) % blocksCount;
    }

    private int[][] getBlock(int[][] matrix, int blockRow, int blockColumn){
        int[][] block = new int[blockRank][blockRank];
        for (int i = 0; i < blockRank; i++) {
            for (int j = 0; j < blockRank; j++) {
                block[i][j] = matrix[blockRow * blockRank + i][blockColumn * blockRank + j];
            }
        }
        return block;
    }

    private void applyBlockToResult(int[][] block){
        for (int i = 0; i < blockRank; i++) {
            for (int j = 0; j < blockRank; j++) {
                result[firstBlockRow * blockRank + i][secondBlockColumn * blockRank + j] += block[i][j];
            }
        }
    }

    private static synchronized void print(int[][] matrix){
        System.out.println();
        for(int i =0; i < matrix.length; i ++){
            for (int j = 0; j < matrix[0].length; j++){
            }
            System.out.println();
        }
    }
}
