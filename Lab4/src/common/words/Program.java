package common.words;

import example.Document;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Program {
    public static void main(String[] args) throws IOException {

        var file = new File("Lab4/src/common/words/Lord of the Rings.txt");
        var lotr = Document.fromFile(file);

        var file2 = new File("Lab4/src/common/words/The-Hobbit.txt");
        var theHobbit = Document.fromFile(file2);

        SameWordsFinder finder = new SameWordsFinder();

        Set<String> result = finder.findSameWords(lotr, theHobbit);

        System.out.println(result.size());
        for (var item : result) {
            System.out.println(item);
        }
    }
}
