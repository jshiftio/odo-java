package com.lordofthejars.odo.core;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FileManagerTest {

    @Test
    public void should_copy_file_from_inputstream_to_temp_file() throws IOException {

        // Given
        final InputStream resourceAsStream =
            FileManagerTest.class.getClassLoader().getResourceAsStream("binaries/hello.txt");
        final FileManager fileManager = new FileManager("hello.txt", resourceAsStream);

        // When
        final Path copyToTemp = fileManager.copyToTemp();

        // Then
        assertThat(copyToTemp)
            .exists()
            .hasContent("Hello World");
    }

}
