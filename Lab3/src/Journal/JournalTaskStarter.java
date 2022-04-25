package Journal;

import java.util.LinkedList;

public class JournalTaskStarter {
    public static void startTask(){
        Journal journal = new Journal();
        int nWeeks = 3;

        var threads = new LinkedList<Thread>();

        threads.add(new Thread(new Teacher("L1", nWeeks, journal)));
        threads.add(new Thread(new Teacher("As1", nWeeks, journal)));
        threads.add(new Thread(new Teacher("As2", nWeeks, journal)));
        threads.add(new Thread(new Teacher("As3", nWeeks, journal)));

        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        journal.show();
    }
}
