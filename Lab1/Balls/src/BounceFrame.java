import javax.swing.*;
import java.awt.*;

public class BounceFrame extends JFrame {
    private BallCanvas canvas;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce program");
        this.canvas = new BallCanvas();
        var content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        var buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        var buttonStart = new JButton("Start");
        var buttonAddHoles = new JButton("Add holes");
        var buttonStop = new JButton("Stop");
        var buttonJoin = new JButton("Join test");
        var buttonRed = new JButton("Red ball");
        var buttonBlue = new JButton("Blue ball");
        buttonRed.addActionListener(e -> {
            var b = new Ball(canvas, Color.red);
            canvas.add(b);
            var ballThread = new BallThread(b, canvas);
            ballThread.setPriority(Thread.MAX_PRIORITY);
            ballThread.start();
        });
        buttonBlue.addActionListener(e -> {
            int x = 0;
            int y = 30;
            for (int i = 0; i < 1500; i++) {
                var b = new Ball(canvas, Color.blue, x, y);
                canvas.add(b);
                var ballThread = new BallThread(b, canvas);
                ballThread.setPriority(Thread.MIN_PRIORITY);
                ballThread.start();
            }

            var r = new Ball(canvas, Color.red, x, y);
            canvas.add(r);
            var redBallThread = new BallThread(r, canvas);
            redBallThread.setPriority(Thread.MAX_PRIORITY);
            redBallThread.start();
        });
        buttonAddHoles.addActionListener(e -> {
            canvas.toggleHoles();
        });
        buttonStart.addActionListener(e -> {
            var b = new Ball(canvas);
            canvas.add(b);
            var thread = new BallThread(b, canvas);
            thread.start();
            System.out.println("Thread name = " + thread.getName());

        });

        buttonStop.addActionListener(e -> {
            System.exit(0);
        });
        buttonJoin.addActionListener(e -> {
            var b = new Ball(canvas);
            canvas.add(b);
            var c = new Ball(canvas);
            canvas.add(c);
            var thread1 = new BallThread(b, canvas);
            var thread2 = new BallThread(c, canvas);

            new Thread(() -> {
                thread1.start();
                try {
                    thread1.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                thread2.start();
                try {
                    thread2.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();
        });
        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonStop);
        buttonPanel.add(buttonAddHoles);
        buttonPanel.add(buttonJoin);
        buttonPanel.add(buttonRed);
        buttonPanel.add(buttonBlue);
        content.add(buttonPanel, BorderLayout.SOUTH);
    }
}
