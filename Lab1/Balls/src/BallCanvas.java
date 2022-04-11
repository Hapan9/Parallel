import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class BallCanvas extends JPanel {
    private final ArrayList<Ball> balls = new ArrayList<>();
    private final ArrayList<Hole> holes = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private int _removedBalls = 0;

    public ArrayList<Hole> getHoles() {
        return this.holes;
    }

    public void add(Hole h) {
        this.holes.add(h);
    }

    public void add(Ball b) {
        lock.lock();
        this.balls.add(b);
        lock.unlock();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2 = (Graphics2D) g;
        lock.lock();
        for (Ball b : balls) {
            b.draw(g2);
        }
        lock.unlock();
        for (var hole : holes) {
            hole.draw(g2);
        }
    }

    public void toggleHoles() {
        if (this.holes.size() == 0) {
            this.holes.add(new Hole(this, 0, 0));
            this.holes.add(new Hole(this, getWidth() - 40, getHeight() - 40));
            this.holes.add(new Hole(this, getWidth() - 40, 0));
            this.holes.add(new Hole(this, 0, getHeight() - 40));
        } else {
            this.holes.clear();
        }
        this.repaint();
    }

    public boolean isInsideAnyHole(Ball ball) {
        return holes.stream().anyMatch(hole -> hole.contains(ball.getX(), ball.getY()));
    }

    public void remove(Ball b) {
        lock.lock();
        this._removedBalls++;
        System.out.println(_removedBalls + " balls removed");
        this.balls.remove(b);
        lock.unlock();
    }
}
