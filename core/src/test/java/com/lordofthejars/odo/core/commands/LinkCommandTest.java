package com.lordofthejars.odo.core.commands;

import java.util.List;
import org.junit.jupiter.api.Test;

import static com.lordofthejars.odo.core.commands.CommandTransformer.transform;
import static org.assertj.core.api.Assertions.assertThat;

public class LinkCommandTest {

    @Test
    public void should_execute_link_command() {

        // Given

        final LinkCommand linkCommand = new LinkCommand
            .Builder("backend")
            .withComponent("frontend")
            .build();

        // When

        final List<String> cliCommand = linkCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("link backend --component frontend"));

    }

}
