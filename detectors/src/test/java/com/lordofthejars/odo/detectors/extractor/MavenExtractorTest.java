package com.lordofthejars.odo.detectors.extractor;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MavenExtractorTest {
    @Test
    public void shouldReadPomWithoutFail() {
        Extractor extractor = new MavenExtractor(Paths.get("src/test/resources","test-pom.xml"));
        assertEquals("fabric8-maven-sample-spring-boot", extractor.extractArtifactId());
        assertEquals("jar", extractor.extractTypeOfProject());

        List<String[]> depList = new ArrayList<String[]>(3) {{
            add(new String[]{"spring-boot-starter-web", "org.springframework.boot"});
            add(new String[]{"org.springframework.boot", "org.springframework.boot"});
            add(new String[]{"jolokia-core", "org.jolokia"});
        }};

        Iterator<Dependency> depIterator = extractor.extractDependencies().iterator();
        Iterator<String[]> testIterator = depList.iterator();

        while (testIterator.hasNext() && depIterator.hasNext()) {
            String[] testPair = testIterator.next();
            Dependency d = depIterator.next();
            assertEquals(testPair[0], d.depName());
            assertEquals(testPair[1], d.depGroupName());
        }
    }
}
