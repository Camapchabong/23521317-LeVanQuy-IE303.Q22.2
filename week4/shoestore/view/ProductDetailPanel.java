package shoestore.view;

import shoestore.model.Product;
import shoestore.utils.FadeAnimator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ProductDetailPanel extends JPanel {

    private final FadeAnimator fadeAnimator = new FadeAnimator();

    private BufferedImage currentImage;
    private BufferedImage fadingImage;
    private float         fadeAlpha = 1f;

    private final ImageCanvas imageCanvas;
    private final JLabel      lblName;
    private final JLabel      lblPrice;
    private final JLabel      lblBrand;
    private final JLabel      lblDesc;

    public ProductDetailPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(280, 0));
        setBorder(new EmptyBorder(10, 0, 10, 20));

        imageCanvas = new ImageCanvas();
        imageCanvas.setPreferredSize(new Dimension(260, 200));
        imageCanvas.setMaximumSize(new Dimension(260, 200));
        imageCanvas.setAlignmentX(LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(220, 220, 220));
        sep.setAlignmentX(LEFT_ALIGNMENT);

        lblName  = makeLabel("", new Font("SansSerif", Font.BOLD,  18), Color.BLACK);
        lblPrice = makeLabel("", new Font("SansSerif", Font.BOLD,  16), Color.BLACK);
        lblBrand = makeLabel("", new Font("SansSerif", Font.PLAIN, 13), new Color(80, 80, 80));
        lblDesc  = new JLabel();
        lblDesc.setAlignmentX(LEFT_ALIGNMENT);

        add(imageCanvas);
        add(Box.createVerticalStrut(12));
        add(sep);
        add(Box.createVerticalStrut(10));
        add(lblName);
        add(Box.createVerticalStrut(4));
        add(lblPrice);
        add(Box.createVerticalStrut(4));
        add(lblBrand);
        add(Box.createVerticalStrut(6));
        add(lblDesc);
    }

    public void showProduct(Product product, BufferedImage image, boolean animate) {
        lblName .setText(product.getName());
        lblPrice.setText(product.getPrice());
        lblBrand.setText(product.getBrand());
        lblDesc .setText("<html><body style='width:230px;color:#777;font-size:11px'>"
                + product.getDescription() + "</body></html>");

        if (animate && image != null) {
            fadingImage = image;
            fadeAlpha   = 0f;
            fadeAnimator.start(new FadeAnimator.Callback() {
                @Override public void onFrame(float alpha) {
                    fadeAlpha = alpha;
                    imageCanvas.repaint();
                }
                @Override public void onFinish() {
                    currentImage = fadingImage;
                    fadingImage  = null;
                    fadeAlpha    = 1f;
                    imageCanvas.repaint();
                }
            });
        } else {
            fadeAnimator.stop();
            currentImage = image;
            fadingImage  = null;
            fadeAlpha    = 1f;
            imageCanvas.repaint();
        }
    }

    private JLabel makeLabel(String text, Font font, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(font);
        l.setForeground(color);
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    // Canvas vẽ ảnh với hiệu ứng alpha
    private class ImageCanvas extends JPanel {
        ImageCanvas() { setOpaque(false); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

            BufferedImage img = (fadingImage != null) ? fadingImage : currentImage;
            if (img != null) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAlpha));
                int w = getWidth(), h = getHeight();
                double scale = Math.min((double) w / img.getWidth(), (double) h / img.getHeight());
                int dw = (int)(img.getWidth()  * scale);
                int dh = (int)(img.getHeight() * scale);
                g2.drawImage(img, (w - dw) / 2, (h - dh) / 2, dw, dh, null);
            }
            g2.dispose();
        }
    }
}
