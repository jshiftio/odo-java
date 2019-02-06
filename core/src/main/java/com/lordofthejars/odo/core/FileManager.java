package com.lordofthejars.odo.core;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileManager {

    private InputStream content;
    private String name;

    FileManager(String name, InputStream content) {
        this.content = content;
        this.name = name;
    }

    Path copyToTemp() throws IOException {
        final Path odoTempDirectory = Files.createTempDirectory("odo");

        final Path output = odoTempDirectory.resolve(this.name);
        try(final InputStream stream = this.content) {
            Files.copy(stream, output, StandardCopyOption.REPLACE_EXISTING);
        }
        return output;
    }


}
