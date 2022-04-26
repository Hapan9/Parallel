package word.counter;

import example.Document;

import java.io.File;
import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        var file = new File("Lab4/src/common/words/Lord of the Rings.txt");
        var document = Document.fromFile(file);
        var counter = new WordLengthCounter();

        System.out.println("Single thread");
        var time = System.currentTimeMillis();
        var result = counter.countWordsLengthInSingleThread(document);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
        for (var entry : result.wordsLengths.entrySet()) {
            System.out.println("Length " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Math expected: " + result.mathExpected);
        System.out.println("Disperse: " + result.disperse);
        System.out.println("Deviation: " + result.deviation);

        System.out.println();

        System.out.println("ForkJoin");
        time = System.currentTimeMillis();
        result = counter.countWordsLengthInRealParallel(document);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
        for (var entry : result.wordsLengths.entrySet()) {
            System.out.println("Length " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Math expected: " + result.mathExpected);
        System.out.println("Disperse: " + result.disperse);
        System.out.println("Deviation: " + result.deviation);
    }
}
