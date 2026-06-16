package shoestore.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    private final ProductDetailPanel detailPanel;
    private final ProductGridPanel   gridPanel;

    public MainFrame() {
        super("Adidas Shoe Store");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1180, 640);
        setMinimumSize(new Dimension(900, 500));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        detailPanel = new ProductDetailPanel();
        gridPanel   = new ProductGridPanel();

        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(buildTopBar(),  BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
    }

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        bar.setBackground(Color.WHITE);
        bar.setBorder(new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
        for (Color c : new Color[]{new Color(255, 195, 0), new Color(255, 140, 0), new Color(220, 50, 50)}) {
            JLabel dot = new JLabel("●");
            dot.setForeground(c);
            dot.setFont(new Font("Dialog", Font.PLAIN, 14));
            bar.add(dot);
        }
        return bar;
    }

    private JPanel buildContent() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(20, 20, 20, 20));
        content.add(detailPanel,                  BorderLayout.WEST);
        content.add(gridPanel.wrapInScrollPane(), BorderLayout.CENTER);
        return content;
    }

    public ProductDetailPanel getDetailPanel() { return detailPanel; }
    public ProductGridPanel   getGridPanel()   { return gridPanel; }
}
