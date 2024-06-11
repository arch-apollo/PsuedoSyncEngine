package com.apollo.pseudoSyncEngine.service;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    public void copyFile(String sourcePath, String destinationPath) throws IOException {
        Path source = Path.of(sourcePath);
        Path destination = Path.of(destinationPath);
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }
}

