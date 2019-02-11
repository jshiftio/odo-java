package com.lordofthejars.odo.core.commands;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.lordofthejars.odo.core.commands.CommandTransformer.transform;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectCommandTest {

    @Test
    public void shouldExecuteCreateProjectCommand() {

        // Given

        final ProjectCreateCommand projectCreateCommand = new ProjectCreateCommand.Builder()
                .withName("myproject")
                .build();

        final ProjectCommand projectCommand = new ProjectCommand.Builder(projectCreateCommand).build();

        // When

        final List<String> cliCommand = projectCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("project create myproject"));

    }

    @Test
    public void shouldExecuteCreateProjectCommandGeneric() {
        // Given
        final ProjectCommand projectCommand = new ProjectCommand.Builder()
                .create()
                .name("myproject")
                .build();
        // When

        final List<String> cliCommand = projectCommand.getCliCommand();
        // Then
        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("project create myproject"));
    }

    @Test
    public void shouldExectureDeleteProjectCommand() {
        // Given

        final ProjectDeleteCommand projectDeleteCommand = new ProjectDeleteCommand.Builder()
                .name("myproject")
                .build();

        final ProjectCommand projectCommand = new ProjectCommand.Builder(projectDeleteCommand).build();

        // When

        final List<String> cliCommand = projectCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("project delete myproject --force"));
    }

    @Test
    public void shouldExecuteDeleteProjectCommandGeneric() {
        // Given
        final ProjectCommand projectCommand = new ProjectCommand.Builder()
                .delete()
                .name("myproject")
                .withForce()
                .build();
        // When

        final List<String> cliCommand = projectCommand.getCliCommand();
        // Then
        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("project delete myproject --force"));
    }

    @Test
    public void shouldExecuteSetProjectCommand() {
        // Given

        final ProjectSetCommand projectSetCommand = new ProjectSetCommand.Builder()
                .name("myproject")
                .build();

        final ProjectCommand projectCommand = new ProjectCommand.Builder(projectSetCommand).build();

        // When

        final List<String> cliCommand = projectCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("project set myproject"));
    }

    @Test
    public void shouldExecuteSetProjectCommandGeneric() {
        // Given
        final ProjectCommand projectCommand = new ProjectCommand.Builder()
                .set()
                .name("myproject")
                .build();
        // When

        final List<String> cliCommand = projectCommand.getCliCommand();
        // Then
        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("project set myproject"));
    }

}