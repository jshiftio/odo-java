package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lordofthejars.odo.core.commands.CommandTransformer.transform;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class LinkCommandTest {

    @Mock
    private OdoExecutor odoExecutor;

    @Test
    public void should_execute_link_command() {

        // Given

        final LinkCommand linkCommand = new LinkCommand
            .Builder("backend", odoExecutor)
            .withComponent("frontend")
            .build();

        // When

        final List<String> cliCommand = linkCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("link backend --component frontend"));

    }

}
