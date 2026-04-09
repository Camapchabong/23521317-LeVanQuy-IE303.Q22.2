import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        int WIDTH = 360, HEIGHT = 640;

        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);  
        frame.setLocationRelativeTo(null); 

        GamePanel gamePanel = new GamePanel(WIDTH, HEIGHT);
        frame.add(gamePanel);
        frame.pack();                     
        frame.setVisible(true);
        gamePanel.startGame();
    }
}