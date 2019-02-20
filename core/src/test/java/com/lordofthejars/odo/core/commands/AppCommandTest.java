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
public class AppCommandTest {

    @Mock
    private OdoExecutor odoExecutor;

    private AppCommand appCommand = new AppCommand.Builder().build();

    @Test
    public void should_execute_create_app_command() {

        // Given

        final AppCreateCommand appCreateCommand = new AppCreateCommand.Builder(appCommand, odoExecutor)
            .withAppName("myapp")
            .build();

        // When

        final List<String> cliCommand = appCreateCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("app create myapp"));

    }

    @Test
    public void should_execute_delete_app_command() {

        // Given

        final AppDeleteCommand appDeleteCommand = new AppDeleteCommand.Builder(appCommand, "myapp", odoExecutor).build();

        // When

        final List<String> cliCommand = appDeleteCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("app delete myapp --force"));

    }

    @Test
    public void should_execute_set_app_command() {

        // Given

        final AppSetCommand appSetCommand = new AppSetCommand.Builder(appCommand, "myapp", odoExecutor)
            .withProject("prj")
            .build();

        // When

        final List<String> cliCommand = appSetCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("app set myapp --project prj"));
    }

}
