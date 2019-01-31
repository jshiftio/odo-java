package com.lordofthejars.odo.core.commands;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateCommandTest {

    @Test
    public void should_execute_create_command() {

        // Given

        final CreateCommand createCommand = new CreateCommand.Builder("nodejs")
            .withLocal("/path")
            .build();

        // When

        final List<String> cliCommand = createCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder("create", "nodejs", "--local", "/path");
    }

}
