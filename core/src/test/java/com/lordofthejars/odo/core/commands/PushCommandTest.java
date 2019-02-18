package com.lordofthejars.odo.core.commands;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PushCommandTest {

    @Test
    public void should_execute_push_command() {

        // Given

        final PushCommand pushCommand = new PushCommand.Builder().withApp("application").build();

        // When

        final List<String> cliCommand = pushCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder("push", "--app", "application");
    }

    @Test
    public void should_execute_psh_with_global_parameters() {

        // Given

        PushCommand pushCommand = new PushCommand.Builder()
            .withApp("application")
            .withV(4)
            .build();

        // When

        final List<String> cliCommand = pushCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder("push", "--app", "application", "--v", "4");
    }

}
