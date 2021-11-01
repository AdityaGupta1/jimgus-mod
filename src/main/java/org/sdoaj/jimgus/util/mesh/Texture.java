package org.sdoaj.jimgus.util.mesh;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Texture {
    private final Color[][] pixels;
    private final int width;
    private final int height;

    public Texture(String name) throws IOException {
        String path = MeshFileHelper.getFilePath(name);
        BufferedImage image = ImageIO.read(Texture.class.getClassLoader().getResourceAsStream(path));
        width = image.getWidth();
        height = image.getHeight();
        pixels = new Color[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels[x][y] = new Color(image.getRGB(x, height - 1 - y));
            }
        }
    }

    public Color getColor(float x, float y) {
        return pixels[Math.round(x * width)][Math.round(y * height)];
    }
}
