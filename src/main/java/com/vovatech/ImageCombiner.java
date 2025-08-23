package com.vovatech;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class ImageCombiner {

    public static void combineImagesHorizontally(List<BufferedImage> images, Path outputFilePath) {
        if (images == null || images.isEmpty()) {
            throw new RuntimeException("Список изображений пуст");
        }

        int totalWidth = 0;
        int maxHeight = 0;
        for (BufferedImage img : images) {
            totalWidth += img.getWidth();
            maxHeight = Math.max(maxHeight, img.getHeight());
        }

        // ARGB, чтобы не потерять прозрачность (PNG её поддерживает)
        BufferedImage combined = new BufferedImage(totalWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = combined.createGraphics();
        try {
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int x = 0;
            for (BufferedImage img : images) {
                int y = (maxHeight - img.getHeight()) / 2; // центрируем по вертикали
                g2d.drawImage(img, x, y, null);
                x += img.getWidth();
            }
        } finally {
            g2d.dispose();
        }

        try {
            File outputFile = Files.createFile(outputFilePath).toFile();
            log.info("Изображение сохраняется по адресу {}", outputFile.getAbsolutePath());
            ImageIO.write(combined, "png", outputFile);
        } catch (IOException e) {
            log.error("Не удалось сохранить изображение", e);
        }
    }
}
