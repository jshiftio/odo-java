package com.lordofthejars.odo.core.commands;

import java.util.List;
import org.junit.jupiter.api.Test;

import static com.lordofthejars.odo.core.commands.CommandTransformer.transform;
import static org.assertj.core.api.Assertions.assertThat;

public class AppCommandTest {

    @Test
    public void should_execute_create_app_command() {

        // Given

        final AppCreateCommand appCreateCommand = new AppCreateCommand.Builder()
            .withAppName("myapp")
            .build();

        final AppCommand appCommand = new AppCommand.Builder(appCreateCommand).build();

        // When

        final List<String> cliCommand = appCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("app create myapp"));

    }

    @Test
    public void should_execute_delete_app_command() {

        // Given

        final AppDeleteCommand appDeleteCommand = new AppDeleteCommand.Builder("myapp").build();

        final AppCommand appCommand = new AppCommand.Builder(appDeleteCommand).build();

        // When

        final List<String> cliCommand = appCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("app delete myapp --force"));

    }

    @Test
    public void should_execute_set_app_command() {

        // Given

        final AppSetCommand appSetCommand = new AppSetCommand.Builder("myapp")
            .withProject("prj")
            .build();

        final AppCommand appCommand = new AppCommand.Builder(appSetCommand).build();

        // When

        final List<String> cliCommand = appCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("app set myapp --project prj"));
    }

}
