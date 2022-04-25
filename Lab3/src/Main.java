import BanksTask.BankTaskStarter;
import Journal.JournalTaskStarter;
import ProducerConsumerTask.ProducerConsumerTaskStarter;

public class Main {
    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;
    public static final int ARRAY_SIZE = 500;

    public static void main(String[] args) {
        //ProducerConsumerTaskStarter.startTask(ARRAY_SIZE);
        JournalTaskStarter.startTask();
        //BankTaskStarter.startTask(NACCOUNTS, INITIAL_BALANCE);
    }
}


