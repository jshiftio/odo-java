package com.lordofthejars.odo.detectors.extractor;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class MavenExtractor implements Extractor {

    private XMLEventReader xmlEventReader;

    private String packaging = "";
    private String artifactId = "";
    private List<Dependency> dependencies = new LinkedList<>();

    private boolean artifactIdFlag;
    private boolean groupIdFlag;
    private boolean packagingFlag;
    private boolean dependencyManagementFlag;
    private boolean dependenciesFlag;
    private boolean dependencyFlag;

    public MavenExtractor(Path pom) {
        readPom(pom);
    }

    private void readPom(Path pom) {

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(Files.newInputStream(pom));

            String artifact = "", group = "";

            while (xmlEventReader.hasNext()){
                XMLEvent event = xmlEventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement element = (StartElement)event;
                    switch (trim(element.getName().toString())) {
                        case "artifactId": artifactIdFlag = true; break;
                        case "groupId" : groupIdFlag = true; break;
                        case "packaging": packagingFlag = true; break;
                        case "dependencyManagement": dependencyManagementFlag = true; break;
                        case "dependencies": dependenciesFlag = true; break;
                        case "dependency": dependencyFlag = true; break;
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
                    if (dependencyManagementFlag) {
                        if (dependenciesFlag && dependencyFlag) {
                            if (artifactIdFlag) { artifact = event.asCharacters().getData(); }
                            if (groupIdFlag) { group = event.asCharacters().getData(); }
                            if (artifact.length() > 0 && group.length() > 0) {
                                dependencies.add(new MavenDependency(artifact, group));
                                artifact = ""; group = "";
                            }
                        }
                    }
                }

                if (event.isEndElement()) {
                    EndElement element = (EndElement)event;
                    switch (trim(element.getName().toString())) {
                        case "artifactId": artifactIdFlag = false; break;
                        case "groupId" : groupIdFlag = false; break;
                        case "packaging": packagingFlag = false; break;
                        case "dependencyManagement": dependencyManagementFlag = false; break;
                        case "dependencies": dependenciesFlag = false; break;
                        case "dependency": dependencyFlag = false; break;
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("IOException occured :" + e.getMessage());
            e.printStackTrace();
        } catch (XMLStreamException e) {
            System.err.println("XMLStreamException :" + e.getMessage());
            e.printStackTrace();
        }

    }

    private String trim (String s) {
        String prefix = "{http://maven.apache.org/POM/4.0.0}";
        if (s.startsWith(prefix)) {
            return s.replace(prefix, "");
        }
        return null;
    }

    @Override
    public String extractTypeOfProject() {
        return packaging;
    }

    @Override
    public List<Dependency> extractDependencies() {
        return dependencies;
    }

    @Override
    public String extractArtifactId() {
        return artifactId;
    }

    public class MavenDependency implements Dependency {
        private String artifactId;
        private String groupId;

        private MavenDependency(String artifact, String group) {
            artifactId = artifact;
            groupId = group;
        }

        public String depName() {
            return artifactId;
        }

        public String depGroupName() {
            return groupId;
        }
    }
}