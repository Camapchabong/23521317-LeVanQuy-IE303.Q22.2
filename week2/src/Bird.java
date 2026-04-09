import java.awt.*;
import javax.swing.ImageIcon;

public class Bird {
    int x, y, width = 50, height = 35;
    double velY = 0;
    final double GRAVITY = 0.5;
    final double JUMP = -8;
    Image img;

    public Bird(int startX, int startY) {
        x = startX;
        y = startY;
        img = new ImageIcon("img/flappybird.png").getImage();
    }

    public void update() {
        velY += GRAVITY;
        y += (int) velY;
    }

    public void jump() {
        velY = JUMP;
    }

    public Rectangle getBounds() {
        return new Rectangle(x + 4, y + 4, width - 8, height - 8); // hitbox nhỏ hơn
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }
}