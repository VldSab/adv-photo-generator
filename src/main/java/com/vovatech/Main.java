package com.vovatech;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Real main class is Boostrap.
 * Wrapper for logging needed.
 */
@Slf4j
public class Main {
    private static final Path WORK_DIR = Path.of(System.getProperty("user.dir")).toAbsolutePath().normalize();
    private static final String FILE_TO_PARSE_NAME = "text.txt";
    private static final Path FILE_TO_PARSE_PATH = WORK_DIR.resolve(FILE_TO_PARSE_NAME);
    private static final String IMAGE_NAME = "result.png";
    private static final Path SAVE_IMAGE_PATH = WORK_DIR.resolve(IMAGE_NAME);

    @SneakyThrows
    public static void main(String[] args) {
        prepareFileSystem();
        String textToParse = FileReader.readFile(FILE_TO_PARSE_PATH);
        log.info("Файл считан {}", textToParse);
        List<String> imageIds = ImageIdsParser.parseIds(textToParse);
        log.info("Получены ids {}", imageIds);
        List<BufferedImage> images = new ArrayList<>();
        for (String imageId : imageIds) {
            images.add(ImageFetcher.getImage(imageId));
        }
        ImageCombiner.combineImagesHorizontally(images, SAVE_IMAGE_PATH);
        log.info("Изображение сохранено");
    }

    @SneakyThrows
    private static void prepareFileSystem() {
        Files.deleteIfExists(SAVE_IMAGE_PATH);
    }
}