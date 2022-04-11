package BanksTask;

public class SynchronizedObjectBank implements IBank{
    public static final int NTEST = 10000;
    private final int[] accounts;
    private long ntransacts = 0;
    private Object object = new Object();

    public SynchronizedObjectBank(int n, int initialBalance){
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++)
            accounts[i] = initialBalance;
        ntransacts = 0;
    }

    @Override
    public void transfer(int from, int to, int amount)
            throws InterruptedException{
        synchronized (object){
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0)
                test();
        }
    }

    public void test(){
        int sum = 0;
        for (int i = 0; i < accounts.length; i++)
            sum += accounts[i] ;
        System.out.println("Transactions:" + ntransacts
                + " Sum: " + sum);
    }

    public int size(){
        return accounts.length;
    }
}
