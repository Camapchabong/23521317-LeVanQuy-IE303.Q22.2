package shoestore.view;

import shoestore.model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class ProductCard extends JPanel {

    private static final Color COLOR_BORDER_DEFAULT  = new Color(220, 220, 220);
    private static final Color COLOR_BORDER_SELECTED = new Color(80,  80,  80);
    private static final Color COLOR_BORDER_HOVER    = new Color(180, 180, 180);
    private static final int   ARC = 14;
    private static final int   PAD = 10;

    private final Product       product;
    private final BufferedImage image;

    private boolean selected;
    private boolean hovered;

    public ProductCard(Product product, BufferedImage image, boolean selected) {
        this.product  = product;
        this.image    = image;
        this.selected = selected;
        setOpaque(false);
        setPreferredSize(new Dimension(200, 220));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public void setSelected(boolean selected) { this.selected = selected; repaint(); }
    public void setHovered (boolean hovered)  { this.hovered  = hovered;  repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,     RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();

        // Nền + viền
        Color border = selected ? COLOR_BORDER_SELECTED : hovered ? COLOR_BORDER_HOVER : COLOR_BORDER_DEFAULT;
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(1, 1, w - 2, h - 2, ARC, ARC));
        g2.setStroke(new BasicStroke(selected ? 2f : 1f));
        g2.setColor(border);
        g2.draw(new RoundRectangle2D.Float(1, 1, w - 2, h - 2, ARC, ARC));

        // Tên sản phẩm
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.setColor(Color.BLACK);
        g2.drawString(truncate(g2, product.getName(), w - PAD * 2), PAD, 22);

        // Mô tả ngắn
        g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g2.setColor(new Color(140, 140, 140));
        g2.drawString(truncate(g2, product.getDescription(), w - PAD * 2), PAD, 36);

        // Ảnh sản phẩm
        if (image != null) {
            int imgTop = 42, imgH = h - 42 - 40;
            double scale = Math.min((double)(w - PAD * 2) / image.getWidth(),
                                    (double) imgH / image.getHeight());
            int dw = (int)(image.getWidth()  * scale);
            int dh = (int)(image.getHeight() * scale);
            g2.drawImage(image, (w - dw) / 2, imgTop + (imgH - dh) / 2, dw, dh, null);
        }

        // Hàng dưới: brand + giá
        int y = h - 14;
        g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        g2.setColor(new Color(100, 100, 100));
        g2.drawString(product.getBrand(), PAD, y);

        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        g2.setColor(Color.BLACK);
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(product.getPrice(), w - PAD - fm.stringWidth(product.getPrice()), y);

        g2.dispose();
    }

    private String truncate(Graphics2D g2, String text, int maxW) {
        FontMetrics fm = g2.getFontMetrics();
        if (fm.stringWidth(text) <= maxW) return text;
        while (!text.isEmpty() && fm.stringWidth(text + "…") > maxW)
            text = text.substring(0, text.length() - 1);
        return text + "…";
    }
}
