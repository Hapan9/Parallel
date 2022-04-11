public class BallThread extends Thread {
    private final Ball b;
    private final BallCanvas canvas;

    public BallThread(Ball ball, BallCanvas canvas) {
        b = ball;
        this.canvas = canvas;
    }

    @Override
    public void run() {
        try {
            for (; ;) {
                if (canvas.isInsideAnyHole(b)) {
                    canvas.remove(b);
                    canvas.repaint();
                    return;
                }
                b.move();
                Thread.sleep(4);
            }
        } catch (InterruptedException ex) {
            System.out.println("Thread " + Thread.currentThread().getName() + " interrupted");
        }
    }
}
