package io.jshift.odo.detectors.service;

import io.jshift.odo.detectors.extractor.Extractor;
import io.jshift.odo.detectors.spi.DatabaseConfigurationExtractor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class JpaDatabaseExtractor implements DatabaseConfigurationExtractor {

    public static final String JAVAX_PERSISTENCE_JDBC_USER = "javax.persistence.jdbc.user";
    public static final String JAVAX_PERSISTENCE_JDBC_PASSWORD = "javax.persistence.jdbc.password";
    public static final String JAVAX_PERSISTENCE_JDBC_URL = "javax.persistence.jdbc.url";
    private static Path DEFAULT_LOCATION = Paths.get("src/main/resources/META-INF");

    Path persistence = DEFAULT_LOCATION.resolve("persistence.xml");

    @Override
    public Optional<DatabaseConfiguration> extract() {

        if (Files.exists(persistence)) {
            return getConfiguration();
        }

        return Optional.empty();
    }

    @Override
    public void setExtractor(Extractor extractor) {
    }

    private Optional<DatabaseConfiguration> getConfiguration() {
        String username = "";
        String password = "";
        String databaseUrl = "";

        final XMLInputFactory factory = XMLInputFactory.newInstance();
        try {

            final XMLEventReader persisteceXml =
                factory.createXMLEventReader(Files.newInputStream(persistence));

            while (persisteceXml.hasNext()) {
                final XMLEvent event = persisteceXml.nextEvent();

                if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    final StartElement startElement = event.asStartElement();
                    final String qName = startElement.getName().getLocalPart();

                    if (qName.equalsIgnoreCase("property")) {
                        final Attribute nameAttribute =
                            startElement.getAttributeByName(QName.valueOf("name"));

                        if (nameAttribute.getValue().equalsIgnoreCase(JAVAX_PERSISTENCE_JDBC_USER)) {
                            username = startElement.getAttributeByName(QName.valueOf("value")).getValue();
                        }

                        if (nameAttribute.getValue().equalsIgnoreCase(JAVAX_PERSISTENCE_JDBC_PASSWORD)) {
                            password = startElement.getAttributeByName(QName.valueOf("value")).getValue();
                        }

                        if (nameAttribute.getValue().equalsIgnoreCase(JAVAX_PERSISTENCE_JDBC_URL)) {
                            databaseUrl = startElement.getAttributeByName(QName.valueOf("value")).getValue();
                        }

                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            throw new IllegalArgumentException(e);
        }

        if (username.isEmpty() && password.isEmpty() && databaseUrl.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new DatabaseConfiguration(username, password, JdbcUrlParser.getDatabase(databaseUrl)));
    }
}
