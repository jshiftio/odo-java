package io.jshift.odo.core.commands;

import io.jshift.odo.core.CliExecutor;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ConfigCommandTest {

    @Mock
    private CliExecutor odoExecutor;

    private ConfigCommand configCommand = new ConfigCommand.Builder().build();

    @Test
    public void shoud_execute_config_view_command() {

        // Given

        final ConfigViewCommand configViewCommand = new ConfigViewCommand.Builder(configCommand, odoExecutor)
                                                    .withContext("ctx")
                                                    .build();

        // When

        final List<String> cliCommand = configViewCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactly("config", "view", "--context", "ctx");

    }

    @Test
    public void should_execute_config_set_command() {

        // Given

        final ConfigSetCommand configSetCommand = new ConfigSetCommand.Builder(configCommand, "CPU", "1", odoExecutor).build();

        // When

        final List<String> cliCommand = configSetCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactly("config", "set", "CPU", "1");
    }

    @Test
    public void should_execute_config_unsert_command() {

        // Given

        final ConfigUnsetCommand configUnsetCommand = new ConfigUnsetCommand.Builder(configCommand, "CPU", odoExecutor).build();

        // When

        final List<String> cliCommand = configUnsetCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactly("config", "unset", "CPU");
    }

}
