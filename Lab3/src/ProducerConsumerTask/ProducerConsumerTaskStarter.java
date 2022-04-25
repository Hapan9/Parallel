package ProducerConsumerTask;

import java.util.Random;

public class ProducerConsumerTaskStarter {
    public static void startTask(int arraySize){
        int[] importantInfo = generateArray(arraySize);
        var string = "";
        for( var i : importantInfo){
            string += " " + i;
        }
        System.out.println(string);
        Drop drop = new Drop();
        (new Thread(new Producer(drop, importantInfo))).start();
        (new Thread(new Consumer(drop))).start();
    }

    private static int[] generateArray(int arraySize){
        int[] resultArray = new int[arraySize];
        Random random = new Random();

        for (int i = 0; i < resultArray.length; i++)
            resultArray[i] = random.nextInt(100);

        return resultArray;
    }
}
