package com.vovatech;

import org.apache.commons.io.FileUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class Boostrap {
    public static void main(String[] args) throws Exception {
        Path logsDir = Path.of("logs");
        String logsFileName = "app.log";
        Path pathToLogFile = logsDir.resolve(logsFileName);

        FileUtils.deleteDirectory(logsDir.toFile());
        Files.createDirectory(logsDir);
        Files.createFile(pathToLogFile);

        System.setProperty("org.slf4j.simpleLogger.logFile", pathToLogFile.toString());
        System.setProperty("org.slf4j.simpleLogger.showDateTime", "true");
        System.setProperty("org.slf4j.simpleLogger.dateTimeFormat", "yyyy-MM-dd HH:mm:ss.SSS");
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info");

        Main.main(args);
    }
}
