package io.jshift.odo.detectors.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcUrlParserTest {

    @Test
    public void should_parse_mysql_url() {

        // Given

        String mySqlUrl = "jdbc:mysql://localhost:1111/db?user=user&password=password";

        // When

        final String database = JdbcUrlParser.getDatabase(mySqlUrl);

        // Then

        assertThat(database).isEqualTo("db");

    }

    @Test
    public void should_parse_postgresql_url() {

        // Given

        String postgreSqlUrl = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";

        // When

        final String database = JdbcUrlParser.getDatabase(postgreSqlUrl);

        // Then

        assertThat(database).isEqualTo("test");
    }

}
