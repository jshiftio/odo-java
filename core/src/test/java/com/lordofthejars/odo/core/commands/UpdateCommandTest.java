package com.lordofthejars.odo.core.commands;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.lordofthejars.odo.core.commands.CommandTransformer.transform;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateCommandTest {

    @Test
    public void shouldExecuteUpdateCommands() {
        // Given

        UpdateCommand updateCommand = new UpdateCommand.Builder()
                .local().build();

        List<String> cliCommand = updateCommand.getCliCommand();

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("update --local"));


        updateCommand = new UpdateCommand.Builder()
                .local("target/dir").build();

        cliCommand = updateCommand.getCliCommand();

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("update --local target/dir"));


        updateCommand = new UpdateCommand.Builder()
                .component("testcomponent")
                .local("target/dir")
                .build();

        cliCommand = updateCommand.getCliCommand();

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("update testcomponent --local target/dir"));

        updateCommand = new UpdateCommand.Builder()
                .component("testcomponent")
                .app("myapp")
                .build();

        cliCommand = updateCommand.getCliCommand();

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("update testcomponent --app myapp"));

        updateCommand = new UpdateCommand.Builder()
                .component("testcomponent")
                .binary("target/bin")
                .build();

        cliCommand = updateCommand.getCliCommand();

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("update testcomponent --binary target/bin"));

        updateCommand = new UpdateCommand.Builder()
                .component("testcomponent")
                .git("https:/github.com/user/odo-java")
                .build();

        cliCommand = updateCommand.getCliCommand();

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("update testcomponent --git https:/github.com/user/odo-java"));

        updateCommand = new UpdateCommand.Builder()
                .component("testcomponent")
                .project("myproject")
                .build();

        cliCommand = updateCommand.getCliCommand();

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("update testcomponent --project myproject"));

        updateCommand = new UpdateCommand.Builder()
                .component("testcomponent")
                .ref("refName")
                .build();

        cliCommand = updateCommand.getCliCommand();

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("update testcomponent --ref refName"));
    }

    @Test
    public void shouldExecuteUpdateProjectCommand() {

        // Given

        final UpdateProjectCommand updateProjectCommand = new UpdateProjectCommand.Builder()
                .forComponent("mycomponent")
                .project("myproject")
                .build();

        final UpdateCommand updateCommand = new UpdateCommand.Builder(updateProjectCommand).build();

        // When

        final List<String> cliCommand = updateCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("update mycomponent --project myproject"));

    }

    @Test
    public void shouldExecuteUpdateProjectCommandDefaultProject() {

        // Given

        final UpdateProjectCommand updateProjectCommand = new UpdateProjectCommand.Builder()
                .forComponent("mycomponent")
                .build();

        final UpdateCommand updateCommand = new UpdateCommand.Builder(updateProjectCommand).build();

        // When

        final List<String> cliCommand = updateCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("update mycomponent --project"));

    }

    @Test
    public void shouldExecuteUpdateProjectCommandWithoutComponent() {

        // Given

        final UpdateProjectCommand updateProjectCommand = new UpdateProjectCommand.Builder()
                .project("myproject")
                .build();

        final UpdateCommand updateCommand = new UpdateCommand.Builder(updateProjectCommand).build();

        // When

        final List<String> cliCommand = updateCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
                .containsExactlyInAnyOrder(transform("update --project myproject"));
    }

    @Test
    public void shouldExecuteUpdateAppCommand() {
        // Given

        final UpdateAppCommand updateAppCommand = new UpdateAppCommand.Builder()
                    .forComponent("mycomponent")
                    .app("myapp")
                    .build();

        final UpdateCommand updateCommand = new UpdateCommand.Builder(updateAppCommand).build();

        // When

        final List<String> cliCommand = updateCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update mycomponent --app myapp"));
    }

    @Test public void shouldExecuteUpdateAppCommandDefaultApp() {

        // Given

        final UpdateAppCommand updateAppCommand = new UpdateAppCommand.Builder()
                    .forComponent("mycomponent")
                    .build();

        final UpdateCommand updateCommand = new UpdateCommand.Builder(updateAppCommand).build();

        // When

        final List<String> cliCommand = updateCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update mycomponent --app"));
    }

    @Test public void shouldExecuteUpdateAppCommandWithoutComponent() {

        // Given

        final UpdateAppCommand updateAppCommand = new UpdateAppCommand.Builder()
                    .app("myapp")
                    .build();

        final UpdateCommand updateCommand = new UpdateCommand.Builder(updateAppCommand).build();

        // When

        final List<String> cliCommand = updateCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update --app myapp"));
    }

    @Test
    public void shouldExecuteUpdateBinaryCommand() {
        // Given

        final UpdateBinaryCommand updateBinaryCommand = new UpdateBinaryCommand.Builder()
                .forComponent("mycomponent")
                .binary("./target/bin")
                .build();

        final UpdateCommand updateCommand = new UpdateCommand.Builder(updateBinaryCommand).build();

        // When

        final List<String> cliCommand = updateCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update mycomponent --binary ./target/bin"));
    }


    @Test public void shouldExecuteUpdateBinaryCommandWithoutComponent() {

        // Given

        final UpdateBinaryCommand updateAppCommand = new UpdateBinaryCommand.Builder()
                .binary("./target/bin")
                .build();

        final UpdateCommand updateCommand = new UpdateCommand.Builder(updateAppCommand).build();

        // When

        final List<String> cliCommand = updateCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update --binary ./target/bin"));
    }

    @Test
    public void shouldExecuteUpdateRefCommand() {
        // Given

        final UpdateRefCommand updateRefCommand = new UpdateRefCommand.Builder()
                .forComponent("mycomponent")
                .ref("testrefname")
                .build();

        final UpdateCommand updateCommand = new UpdateCommand.Builder(updateRefCommand).build();

        // When

        final List<String> cliCommand = updateCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update mycomponent --ref testrefname"));
    }


    @Test public void shouldExecuteUpdateRefCommandWithoutComponent() {

        // Given

        final UpdateRefCommand updateRefCommand = new UpdateRefCommand.Builder()
                .ref("testrefname")
                .build();

        final UpdateCommand updateCommand = new UpdateCommand.Builder(updateRefCommand).build();

        // When

        final List<String> cliCommand = updateCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update --ref testrefname"));
    }

    @Test
    public void shouldExecuteUpdateGitCommand() {
        // Given

        final UpdateGitCommand updateGitCommand = new UpdateGitCommand.Builder()
                .forComponent("mycomponent")
                .git("git-url")
                .build();

        final UpdateCommand updateCommand = new UpdateCommand.Builder(updateGitCommand).build();

        // When

        final List<String> cliCommand = updateCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update mycomponent --git git-url"));
    }


    @Test public void shouldExecuteUpdateGitCommandWithoutComponent() {

        // Given

        final UpdateGitCommand updateGitCommand = new UpdateGitCommand.Builder()
                .git("git-url")
                .build();

        final UpdateCommand updateCommand = new UpdateCommand.Builder(updateGitCommand).build();

        // When

        final List<String> cliCommand = updateCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update --git git-url"));
    }

    @Test
    public void shouldExecuteUpdateLocalCommand() {
        // Given

        final UpdateLocalCommand updateLocalCommand = new UpdateLocalCommand.Builder()
                .forComponent("mycomponent")
                .directory("./target/dir")
                .build();

        final UpdateCommand updateCommand = new UpdateCommand.Builder(updateLocalCommand).build();

        // When

        final List<String> cliCommand = updateCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update mycomponent --local ./target/dir"));
    }

    @Test public void shouldExecuteUpdateLocalCommandDefaultApp() {

        // Given

        final UpdateLocalCommand updateLocalCommand = new UpdateLocalCommand.Builder()
                .forComponent("mycomponent")
                .build();

        final UpdateCommand updateCommand = new UpdateCommand.Builder(updateLocalCommand).build();

        // When

        final List<String> cliCommand = updateCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update mycomponent --local"));
    }

    @Test public void shouldExecuteUpdateLocalCommandWithoutComponent() {
        // Given

        final UpdateLocalCommand updateLocalCommand = new UpdateLocalCommand.Builder()
                .directory("./target/dir")
                .build();

        final UpdateCommand updateCommand = new UpdateCommand.Builder(updateLocalCommand).build();

        // When

        final List<String> cliCommand = updateCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("update --local ./target/dir"));
    }
}