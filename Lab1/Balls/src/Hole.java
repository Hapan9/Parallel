import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Hole {
    private static final int XSIZE = 40;
    private static final int YSIZE = 40;
    private int x;
    private int y;

    public Hole(Component canvas, int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.cyan);
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
    }

    public boolean contains(int x, int y) {
        return new Ellipse2D.Double(this.x, this.y, XSIZE, YSIZE).contains(x, y);
    }
}
