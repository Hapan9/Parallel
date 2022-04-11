package ProducerConsumerTask;

public class ProducerConsumerTaskStarter {
    public static void startTask(int arraySize){
        int[] importantInfo = Utils.generateArray(arraySize);
        var string = "";
        for( var i : importantInfo){
            string += " " + i;
        }
        System.out.println(string);
        Drop drop = new Drop();
        (new Thread(new Producer(drop, importantInfo))).start();
        (new Thread(new Consumer(drop))).start();
    }
}
