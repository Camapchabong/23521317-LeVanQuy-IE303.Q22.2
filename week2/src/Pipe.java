import java.awt.*;
import javax.swing.ImageIcon;

public class Pipe {
    static final int WIDTH = 80;
    static final int HEIGHT = 500;
    static final int GAP = 160;

    int x;
    int gapY;        // tâm khoảng hở
    boolean scored;
    Image topImg, botImg;

    public Pipe(int x, int gapY) {
        this.x = x;
        this.gapY = gapY;
        this.scored = false;
        topImg = new ImageIcon("img/toppipe.png").getImage();
        botImg = new ImageIcon("img/bottompipe.png").getImage();
    }

    public void update() {
        x -= 3;
    }

    public boolean isOffScreen() {
        return x + WIDTH < 0;
    }

    public Rectangle getTopBounds() {
        return new Rectangle(x, gapY - GAP / 2 - HEIGHT, WIDTH, HEIGHT);
    }

    public Rectangle getBotBounds() {
        return new Rectangle(x, gapY + GAP / 2, WIDTH, HEIGHT);
    }

    public void draw(Graphics g) {
        g.drawImage(topImg, x, gapY - GAP / 2 - HEIGHT, WIDTH, HEIGHT, null);
        g.drawImage(botImg, x, gapY + GAP / 2, WIDTH, HEIGHT, null);
    }
}