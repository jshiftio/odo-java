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
public class UnlinkCommandTest {

    @Mock
    OdoExecutor odoExecutor;

    @Test
    public void should_create_unlink_command() {

        // Given

        final UnlinkCommand unlinkCommand = new UnlinkCommand.Builder("backend", odoExecutor).build();

        // When

        final List<String> cliCommand = unlinkCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("unlink backend"));
    }

}
