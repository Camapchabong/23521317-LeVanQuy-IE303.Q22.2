package shoestore.view;

import shoestore.model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ProductGridPanel extends JPanel {

    public interface SelectionListener {
        void onProductSelected(int index);
    }

    private final List<ProductCard>       cards     = new ArrayList<>();
    private final List<SelectionListener> listeners = new ArrayList<>();
    private int selectedIndex = 0;

    public ProductGridPanel() {
        setLayout(new GridLayout(0, 4, 12, 12));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(4, 4, 4, 4));
    }

    public void populate(List<Product> products, List<BufferedImage> images) {
        removeAll();
        cards.clear();

        for (int i = 0; i < products.size(); i++) {
            final int idx  = i;
            ProductCard card = new ProductCard(products.get(i), images.get(i), i == 0);

            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectCard(idx);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (idx != selectedIndex) card.setHovered(true);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    card.setHovered(false);
                }
            });

            cards.add(card);
            add(card);
        }
        revalidate();
        repaint();
    }

    private void selectCard(int index) {
        cards.get(selectedIndex).setSelected(false);
        selectedIndex = index;
        cards.get(selectedIndex).setSelected(true);
        listeners.forEach(l -> l.onProductSelected(index));
    }

    public void addSelectionListener(SelectionListener l) {
        listeners.add(l);
    }

    public JScrollPane wrapInScrollPane() {
        JScrollPane scroll = new JScrollPane(this);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }
}
