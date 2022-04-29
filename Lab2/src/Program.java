public class Program {

    public static void main(String[] args) {
        //int[] workersTests = {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024};
        int[] sizeTests = { 500, 1000, 1500};

        // for (int i = 0; i < workersTests.length; i++) {
        //    runWorkersTest(workersTests[i]);
        // }

        for (int i = 0; i < sizeTests.length; i++) {
            runSizeTest(sizeTests[i]);
        }
    }

    private static void runWorkersTest(int workersCount){
        System.out.println("Workers test: " + workersCount + " workers");
        int[][] firstMatrix = createMatrix(500, 500);
        int[][] secondMatrix = createMatrix(500, 500);
        TapeStrategy tape = new TapeStrategy(firstMatrix, secondMatrix, workersCount);
        FoxStrategy fox = new FoxStrategy(firstMatrix, secondMatrix, workersCount, 10);

        long start;

        System.out.print("Fox method (500): ");
        start = System.currentTimeMillis();
        IterationsManager.iterate(fox);
        System.out.println(System.currentTimeMillis() - start);

        System.out.print("Tape method: ");
        start = System.currentTimeMillis();
        IterationsManager.iterate(tape);
        System.out.println(System.currentTimeMillis() - start);

        System.out.println();
        //printLastRow(tape.getResult().getMatrix());
    }

    private static void runSizeTest(int matrixRank){
        System.out.println("Size test: " + matrixRank + " matrix rank");
        int[][] firstMatrix = createMatrix(matrixRank, matrixRank);
        int[][] secondMatrix = createMatrix(matrixRank, matrixRank);
        TapeStrategy tape = new TapeStrategy(firstMatrix, secondMatrix, 4);
        FoxStrategy fox = new FoxStrategy(firstMatrix, secondMatrix,4, 10);

        long start;

        System.out.print("Fox method: ");
        start = System.currentTimeMillis();
        IterationsManager.iterate(fox);
        System.out.println(System.currentTimeMillis() - start);

        System.out.print("Tape method: ");
        start = System.currentTimeMillis();
        IterationsManager.iterate(tape);
        System.out.println(System.currentTimeMillis() - start);

        System.out.println();
    }

    private static int[][] createMatrix(int rows, int columns){
        int[][] matrix = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = 1;
            }
        }
        return matrix;
    }

    private static void printLastRow(int[][] matrix){
        var lastIndex = matrix.length - 1;
        for (int j = 0; j < matrix[0].length; j++)
            System.out.print(matrix[lastIndex][j] + " ");
        System.out.println();
    }
}
