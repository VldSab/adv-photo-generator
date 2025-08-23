package com.vovatech;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FileReader {
    public static String readFile(Path filePath) {
        try {
            return Files.readString(filePath);
        } catch (Exception e) {
            log.error("Не удалось прочитать файл %s".formatted(filePath), e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
