package ProducerConsumerTask;

import java.util.Random;

public class Utils {
    public static int[] generateArray(int arraySize){
        int[] resultArray = new int[arraySize];
        Random random = new Random();

        for (int i = 0; i < resultArray.length; i++)
            resultArray[i] = random.nextInt(100);

        return resultArray;
    }
}
