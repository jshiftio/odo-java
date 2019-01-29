package com.lordofthejars.odo.core.resolvers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalLocationResolverTest {

    @Test
    public void should_copy_files_from_local_disk() throws IOException {

        // Given
        LocalLocationResolver localLocationResolver = new LocalLocationResolver(Paths.get("src/test/resources/binaries/hello.txt"));

        // When

        final InputStream inputStream = localLocationResolver.loadResource();

        // Then

        final String content = readContent(inputStream);
        assertThat(content).isEqualTo("Hello World");
    }

    @Test
    public void should_set_name_to_local_name() {

        // Given
        LocalLocationResolver localLocationResolver = new LocalLocationResolver(Paths.get("src/test/resources/binaries/hello.txt"));

        // When

        final String name = localLocationResolver.getName();

        // Then

        assertThat(name).isEqualTo("hello.txt");

    }

    private String readContent(InputStream helloResource) throws IOException {
        final String result = new BufferedReader(new InputStreamReader(helloResource))
            .lines().collect(Collectors.joining("\n"));
        helloResource.close();
        return result;
    }

}
