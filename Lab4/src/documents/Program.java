package documents;

import example.Folder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Program {
    public static void main(String[] args) throws IOException {
        var folder = Folder.fromDirectory(new File("Lab4/src/documents"));
        var browser = new FilesBrowser();

        Set<String> searchedWords = ConcurrentHashMap.newKeySet();
        searchedWords.addAll(Arrays.asList("code","programming"));

        var result = browser.findFilesByTheme(folder, searchedWords);

        for (String file : result){
            System.out.println(file);
        }
    }
}
