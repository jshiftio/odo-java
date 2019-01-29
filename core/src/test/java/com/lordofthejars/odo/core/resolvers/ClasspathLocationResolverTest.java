package com.lordofthejars.odo.core.resolvers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClasspathLocationResolverTest {

    @Test
    public void should_load_resource_from_classpath() throws IOException {

        // Given

        final ClasspathLocationResolver classpathLocationResolver = new ClasspathLocationResolver("hello.txt");

        // When

        final InputStream helloResource = classpathLocationResolver.loadResource();

        // Then

        assertThat(helloResource)
            .isNotNull();

        final String result = readContent(helloResource);
        assertThat(result).isEqualTo("Hello World");

    }

    private String readContent(InputStream helloResource) throws IOException {
        final String result = new BufferedReader(new InputStreamReader(helloResource))
            .lines().collect(Collectors.joining("\n"));
        helloResource.close();
        return result;
    }
}
