public class Printer implements Runnable {
    private final Object printLock;
    private volatile int state = 0;
    private final int printFlag;
    private final int nextPrintFlag;

    private final char printChar;

    public Printer(Object printLock, int printFlag, int nextPrintFlag, char printChar) {
        this.printLock = printLock;
        this.printFlag = printFlag;
        this.nextPrintFlag = nextPrintFlag;
        this.printChar = printChar;
    }

    @Override
    public void run() {
        synchronized (printLock) {
//            while (true) {
//                while (state != printFlag) {
//                    try {
//                        printLock.wait();
//                    } catch (InterruptedException e) {
//                    }
//                }
//            }
            System.out.printf(String.valueOf(printChar));
            state = nextPrintFlag;
            printLock.notify();
        }
    }
}
