package BanksTask;

public class TransferThread extends Thread {
    private IBank bank;
    private int fromAccount;
    private int maxAmount;
    private static final int REPS = 1000;

    public TransferThread(IBank b, int from, int max){
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }

    @Override
    public void run(){
        try{
            while (!interrupted()){
                for (int i = 0; i < REPS; i++){
                    int toAccount = (int)(bank.size()*Math.random());
                    int amount =
                            (int)(maxAmount * Math.random()/REPS);
                    bank.transfer(fromAccount, toAccount, amount);
                    Thread.sleep(1);
                }
            }
        } catch(InterruptedException e) {}
    }

}
