package BanksTask;

public class BankTaskStarter {
    public static void startTask(int naCCounts, int initial_balance){
        //var b = new Bank(naCCounts, initial_balance);
        //var b = new SynchronizedBank(naCCounts, initial_balance);
        //var b = new SynchronizedObjectBank(naCCounts, initial_balance);
        var b = new ReentrantLockBank(naCCounts, initial_balance);
        int i;
        for (i = 0; i < naCCounts; i++){
            var t = new TransferThread(b, i,
                    initial_balance);
            t.setPriority(Thread.NORM_PRIORITY + i % 2);
            t.start ();
        }
    }
}
