package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PushCommandTest {

    @Mock
    private OdoExecutor odoExecutor;

    @Test
    public void should_execute_push_command() {

        // Given

        final PushCommand pushCommand = new PushCommand.Builder(odoExecutor).withApp("application").build();

        // When

        final List<String> cliCommand = pushCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder("push", "--app", "application");
    }

    @Test
    public void should_execute_psh_with_global_parameters() {

        // Given

        PushCommand pushCommand = new PushCommand.Builder(odoExecutor)
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
