

public class Program {
    static IterationsManager manager = new IterationsManager();

    public static void main(String[] args) {
        int[] workersTests = { 16 };
        for (int i = 0; i < workersTests.length; i++) {
            runWorkersTest(workersTests[i]);
        }

    }

    private static void runWorkersTest(int workersCount){
        System.out.println("Workers test: " + workersCount + " workers");
        int[][] firstMatrix = createMatrix(100, 100);
        int[][] secondMatrix = createMatrix(100, 100);
        TapeStrategy tape = new TapeStrategy(firstMatrix, secondMatrix, workersCount);
        FoxStrategy fox = new FoxStrategy(firstMatrix, secondMatrix, workersCount, 10);

        long start;

        System.out.print("Fox method (100): ");
        start = System.currentTimeMillis();
        manager.iterate(fox);
        System.out.println(System.currentTimeMillis() - start);

        System.out.print("Tape method: ");
        start = System.currentTimeMillis();
        manager.iterate(tape);
        System.out.println(System.currentTimeMillis() - start);

        System.out.println();
        printLastRow(fox.getResult().getMatrix());
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
