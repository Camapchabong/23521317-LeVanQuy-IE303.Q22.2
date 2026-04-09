import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener {

    int WIDTH, HEIGHT;
    Image bgImg;
    Bird bird;
    ArrayList<Pipe> pipes;
    int score = 0;
    boolean gameOver = false;
    boolean started = false;

    Timer gameLoop;
    Timer pipeSpawner;
    Random rand = new Random();

    Font scoreFont = new Font("Arial", Font.BOLD, 48);
    Font msgFont   = new Font("Arial", Font.BOLD, 28);

    public GamePanel(int w, int h) {
        WIDTH = w; HEIGHT = h;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        bgImg = new ImageIcon("img/flappybirdbg.png").getImage();
        reset();
    }

    // ── Reset về trạng thái ban đầu ──────────────────────────────────
    void reset() {
        bird  = new Bird(80, HEIGHT / 2);
        pipes = new ArrayList<>();
        score = 0;
        gameOver = false;
        started  = false;
    }

    // ── Bắt đầu vòng lặp game ────────────────────────────────────────
    public void startGame() {
        // Game loop: 60 FPS
        gameLoop = new Timer(1000 / 60, e -> {
            if (started && !gameOver) {
                update();
            }
            repaint();
        });
        gameLoop.start();

        // Sinh pipe mỗi 1.8 giây
        pipeSpawner = new Timer(1800, e -> {
            if (started && !gameOver) spawnPipe();
        });
        pipeSpawner.start();
    }

    // ── Sinh pipe mới ────────────────────────────────────────────────
    void spawnPipe() {
        int gapY = 150 + rand.nextInt(HEIGHT - 300);
        pipes.add(new Pipe(WIDTH + 10, gapY));
    }

    // ── Cập nhật logic ───────────────────────────────────────────────
    void update() {
        bird.update();

        // Giới hạn bird không bay ra ngoài màn hình
        if (bird.y < 0) { bird.y = 0; bird.velY = 0; }
        if (bird.y + bird.height >= HEIGHT) {
            bird.y = HEIGHT - bird.height;
            gameOver = true;
        }

        // Cập nhật pipe
        for (Pipe p : pipes) {
            p.update();

            // Tính điểm khi bird vượt qua pipe
            if (!p.scored && p.x + Pipe.WIDTH < bird.x) {
                score++;
                p.scored = true;
            }

            // Va chạm
            if (bird.getBounds().intersects(p.getTopBounds()) ||
                bird.getBounds().intersects(p.getBotBounds())) {
                gameOver = true;
            }
        }

        // Xóa pipe đã ra khỏi màn hình
        pipes.removeIf(Pipe::isOffScreen);
    }

    // ── Vẽ giao diện ─────────────────────────────────────────────────
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.drawImage(bgImg, 0, 0, WIDTH, HEIGHT, null);

        // Pipes
        for (Pipe p : pipes) p.draw(g);

        // Bird
        bird.draw(g);

        // Score
        g.setColor(Color.WHITE);
        g.setFont(scoreFont);
        String scoreStr = String.valueOf(score);
        int sw = g.getFontMetrics().stringWidth(scoreStr);
        g.drawString(scoreStr, (WIDTH - sw) / 2, 70);

        // Màn hình chờ
        if (!started) {
            g.setColor(new Color(0, 0, 0, 140));
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.WHITE);
            g.setFont(msgFont);
            drawCentered(g, "Nhấn SPACE để bắt đầu", HEIGHT / 2);
        }

        // Game Over
        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.WHITE);
            g.setFont(scoreFont);
            drawCentered(g, "Game Over", HEIGHT / 2 - 50);
            g.setFont(msgFont);
            drawCentered(g, "Điểm: " + score, HEIGHT / 2 + 10);
            drawCentered(g, "Nhấn SPACE để chơi lại", HEIGHT / 2 + 60);
        }
    }

    void drawCentered(Graphics g, String text, int y) {
        int sw = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (WIDTH - sw) / 2, y);
    }

    // ── Xử lý phím ───────────────────────────────────────────────────
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_ENTER) {
            if (gameOver) {
                reset();          // Restart
            } else if (!started) {
                started = true;   // Bắt đầu game
            } else {
                bird.jump();      // Nhảy
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}