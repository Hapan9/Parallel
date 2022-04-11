package task1;

public class SleepServingStrategy implements ServingStrategy, ServingChannel {

    @Override
    public void serve(Client client) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
