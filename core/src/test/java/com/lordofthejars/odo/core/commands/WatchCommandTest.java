package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.CliExecutor;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lordofthejars.odo.core.commands.CommandTransformer.transform;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class WatchCommandTest {

    @Mock
    private CliExecutor odoExecutor;

    @Test
    public void should_exeucte_watch_command() {

        // Given

        final WatchCommand watchCommand = new WatchCommand.Builder(odoExecutor)
            .withComponentName("frontend")
            .build();

        // When

        final List<String> cliCommand = watchCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactly(transform("watch frontend"));
    }

}
