package shoestore.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {

    private final Map<String, BufferedImage> cache = new HashMap<>();

    public BufferedImage load(String filepath) {
        if (cache.containsKey(filepath)) {
            return cache.get(filepath);
        }
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filepath));
        } catch (IOException e) {
            System.err.println("Không tải được ảnh: " + filepath);
        }
        cache.put(filepath, img);
        return img;
    }
}