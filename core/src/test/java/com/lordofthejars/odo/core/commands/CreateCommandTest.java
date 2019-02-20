package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CreateCommandTest {

    @Mock
    private OdoExecutor odoExecutor;

    @Test
    public void should_execute_create_command() {

        // Given

        final CreateCommand createCommand = new CreateCommand.Builder("nodejs", odoExecutor)
            .withLocal("/path")
            .build();

        // When

        final List<String> cliCommand = createCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder("create", "nodejs", "--local", "/path");
    }

}
