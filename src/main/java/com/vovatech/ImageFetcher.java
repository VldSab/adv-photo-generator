package com.vovatech;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ImageFetcher {

    private static final String IMAGE_URL_TEMPLATE = "https://t.me/i/emoji/%s.webp";

    public static BufferedImage getImage(String imageId) throws InterruptedException {
        int fetchRetries = 3;
        URL imageUrl = getImageUrl(imageId);
        Exception savedException = null;
        while (fetchRetries > 0) {
            fetchRetries--;
            try {
                return ImageIO.read(imageUrl);
            } catch (Exception e) {
                log.error("Не удалось загрузить изображение", e);
                savedException = e;
                TimeUnit.SECONDS.sleep(1L);
            }
        }
        throw new RuntimeException(savedException);
    }

    private static URL getImageUrl(String imageId) {
        String imageAddress = IMAGE_URL_TEMPLATE.formatted(imageId);
        try {
            return URI.create(imageAddress).toURL();
        } catch (Exception e) {
            log.error("Не удалось получить URL из строки %s".formatted(imageAddress), e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
