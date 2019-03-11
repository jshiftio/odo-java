package com.lordofthejars.odo.detectors.extractor;

import com.lordofthejars.odo.detectors.util.Packaging;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class MavenExtractor implements Extractor {

    private String packaging = "";
    private String artifactId = "";
    private Set<Dependency> dependencies = new HashSet<>();

    private boolean artifactIdFlag;
    private boolean groupIdFlag;
    private boolean packagingFlag;
    private boolean dependencyFlag;

    private Path workingDirectory;

    public MavenExtractor(Path pom) {
        readPom(pom);
        this.workingDirectory = pom.getParent();
    }

    private void readPom(Path pom) {

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(Files.newInputStream(pom));


            String artifact = "", group = "";

            while (xmlEventReader.hasNext()) {
                XMLEvent event = xmlEventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement element = (StartElement) event;
                    switch (trim(element.getName().toString())) {
                        case "artifactId":
                            artifactIdFlag = true;
                            break;
                        case "groupId":
                            groupIdFlag = true;
                            break;
                        case "packaging":
                            packagingFlag = true;
                            break;
                        case "dependency":
                            dependencyFlag = true;
                            break;
                    }
                }

                if (event.isCharacters() && !event.asCharacters().isIgnorableWhiteSpace()) {
                    if (artifactIdFlag) {
                        if (artifactId.length() == 0) {
                            artifactId = event.asCharacters().getData();
                        }
                    }
                    if (packagingFlag) {
                        if (packaging.length() == 0) {
                            packaging = event.asCharacters().getData();
                        }
                    }
                    if (dependencyFlag) {
                        if (artifactIdFlag) {
                            artifact = event.asCharacters().getData();
                        }
                        if (groupIdFlag) {
                            group = event.asCharacters().getData();
                        }
                        if (artifact.length() > 0 && group.length() > 0) {
                            dependencies.add(new Dependency(group, artifact));
                            artifact = "";
                            group = "";
                        }
                    }
                }

                if (event.isEndElement()) {
                    EndElement element = (EndElement) event;
                    switch (trim(element.getName().toString())) {
                        case "artifactId":
                            artifactIdFlag = false;
                            break;
                        case "groupId":
                            groupIdFlag = false;
                            break;
                        case "packaging":
                            packagingFlag = false;
                            break;
                        case "dependency":
                            dependencyFlag = false;
                            break;
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } catch (XMLStreamException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String trim(String s) {
        String prefix = "{http://maven.apache.org/POM/4.0.0}";
        if (s.startsWith(prefix)) {
            return s.replace(prefix, "");
        }
        return null;
    }

    @Override
    public Packaging extractTypeOfProject() {
        return Packaging.valueOf(packaging.toUpperCase());
    }

    @Override
    public Set<Dependency> extractDependencies() {
        return dependencies;
    }

    @Override
    public Path workingDirectory() {
        return workingDirectory;
    }

    @Override
    public String extractArtifactId() {
        return artifactId;
    }
}