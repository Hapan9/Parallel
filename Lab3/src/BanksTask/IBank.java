package BanksTask;

public interface IBank {
    void transfer(int fromAccount, int toAccount, int amount) throws InterruptedException;
    int size();
}
