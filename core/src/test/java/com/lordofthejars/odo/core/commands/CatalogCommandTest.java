package com.lordofthejars.odo.core.commands;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CatalogCommandTest {

    @Test
    public void should_list_catalog() {

        // Given

        final CatalogListCommand catalogListCommand = new CatalogListCommand.Builder("components").build();
        final CatalogCommand catalogCommand = new CatalogCommand.Builder(catalogListCommand).build();

        // When

        final List<String> cliCommand = catalogCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder("catalog", "list", "components");
    }

}
