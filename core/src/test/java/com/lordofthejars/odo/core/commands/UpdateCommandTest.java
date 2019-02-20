package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import org.junit.jupiter.api.Test;

import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lordofthejars.odo.core.commands.CommandTransformer.transform;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UpdateCommandTest {

    @Mock
    private OdoExecutor odoExecutor;

    @Test
    public void shouldExecuteUpdateProjectCommand() {

        // Given

        final UpdateCommand updateProjectCommand = new UpdateCommand.Builder(odoExecutor)
                .withComponentName("mycomponent")
                .withProject("myproject")
                .build();

        // When

        final List<String> cliCommand = updateProjectCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("update mycomponent --project myproject"));

    }

    @Test
    public void shouldExecuteUpdateAppCommand() {
        // Given

        final UpdateCommand updateAppCommand = new UpdateCommand.Builder(odoExecutor)
                    .withComponentName("mycomponent")
                    .withApp("myapp")
                    .build();

        // When

        final List<String> cliCommand = updateAppCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update mycomponent --app myapp"));
    }

    @Test
    public void shouldExecuteUpdateBinaryCommand() {
        // Given

        final UpdateCommand updateBinaryCommand = new UpdateCommand.Builder(odoExecutor)
                .withComponentName("mycomponent")
                .withBinary("./target/bin")
                .build();

        // When

        final List<String> cliCommand = updateBinaryCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update mycomponent --binary ./target/bin"));
    }

    @Test
    public void shouldExecuteUpdateRefCommand() {
        // Given

        final UpdateCommand updateRefCommand = new UpdateCommand.Builder(odoExecutor)
                .withComponentName("mycomponent")
                .withRef("testrefname")
                .build();

        // When

        final List<String> cliCommand = updateRefCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update mycomponent --ref testrefname"));
    }

    @Test
    public void shouldExecuteUpdateGitCommand() {
        // Given

        final UpdateCommand updateGitCommand = new UpdateCommand.Builder(odoExecutor)
                .withComponentName("mycomponent")
                .withGit("git-url")
                .build();

        // When

        final List<String> cliCommand = updateGitCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update mycomponent --git git-url"));
    }

    @Test
    public void shouldExecuteUpdateLocalCommand() {
        // Given

        final UpdateCommand updateLocalCommand = new UpdateCommand.Builder(odoExecutor)
                .withComponentName("mycomponent")
                .withLocal("./target/dir")
                .build();

        // When

        final List<String> cliCommand = updateLocalCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update mycomponent --local ./target/dir"));
    }

}