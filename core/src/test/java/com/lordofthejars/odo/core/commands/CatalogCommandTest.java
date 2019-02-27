package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.CliExecutor;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CatalogCommandTest {

    @Mock
    private CliExecutor odoExecutor;

    private CatalogCommand catalogCommand = new CatalogCommand.Builder().build();

    @Test
    public void should_list_catalog() {

        // Given

        final CatalogListCommand catalogListCommand = new CatalogListCommand.Builder(catalogCommand, "components", odoExecutor).build();

        // When

        final List<String> cliCommand = catalogListCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder("catalog", "list", "components");
    }

}
