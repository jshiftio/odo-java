package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.CliExecutor;
import org.junit.jupiter.api.Test;

import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lordofthejars.odo.core.commands.CommandTransformer.transform;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ProjectCommandTest {

    @Mock
    private CliExecutor odoExecutor;

    private ProjectCommand projectCommand = new ProjectCommand.Builder().build();

    @Test
    public void shouldExecuteCreateProjectCommand() {

        // Given

        final ProjectCreateCommand projectCreateCommand = new ProjectCreateCommand.Builder(projectCommand, "myproject", odoExecutor)
                .build();

        // When

        final List<String> cliCommand = projectCreateCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("project create myproject"));

    }

    @Test
    public void shouldExectureDeleteProjectCommand() {
        // Given

        final ProjectDeleteCommand projectDeleteCommand = new ProjectDeleteCommand.Builder(projectCommand, "myproject", odoExecutor)
                .build();

        // When

        final List<String> cliCommand = projectDeleteCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("project delete myproject --force"));
    }


    @Test
    public void shouldExecuteSetProjectCommand() {
        // Given

        final ProjectSetCommand projectSetCommand = new ProjectSetCommand.Builder(projectCommand, "myproject", odoExecutor)
                .build();

        // When

        final List<String> cliCommand = projectSetCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("project set myproject"));
    }

}