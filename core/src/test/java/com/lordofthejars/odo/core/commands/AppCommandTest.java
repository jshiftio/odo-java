package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.CliExecutor;
import com.lordofthejars.odo.core.commands.output.App;
import com.lordofthejars.odo.core.commands.output.AppList;
import com.lordofthejars.odo.core.commands.output.TerminalOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.lordofthejars.odo.core.commands.CommandTransformer.transform;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppCommandTest {

    @Mock
    private CliExecutor odoExecutor;

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
            .containsExactly(transform("app create myapp"));

    }

    @Test
    public void should_execute_delete_app_command() {

        // Given

        final AppDeleteCommand appDeleteCommand = new AppDeleteCommand.Builder(appCommand, odoExecutor)
                .withAppName("myapp").build();

        // When

        final List<String> cliCommand = appDeleteCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactly(transform("app delete myapp --force"));

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
            .containsExactly(transform("app set myapp --project prj"));
    }

    @Test
    public void should_execute_list_command() {

        // Given

        final AppListCommand appListCommand = new AppListCommand.Builder(appCommand, odoExecutor).build();

        // When

        final List<String> cliCommand = appListCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactly(transform("app list --output json"));
    }

    @Test
    public void should_list_applications() throws IOException {

        // Given

        final List<String>
            appListDescribe = Files.readAllLines(Paths.get("src/test/resources", "app_list.json"));
        final AppListCommand appListCommand = new AppListCommand.Builder(appCommand, odoExecutor).build();

        when(odoExecutor.execute(appListCommand)).thenReturn(appListDescribe);

        // When

        final AppList appList = appListCommand.execute();

        // Then

        assertThat(appList.getItems()).hasSize(2);
        assertThat(appList.getItems().get(0).getTypeMeta().getKind()).isEqualTo("app");
        assertThat(appList.getItems().get(0).getMetadata().getNamespace()).isEqualTo("myproject");
        assertThat(appList.getItems().get(1).getTypeMeta().getKind()).isEqualTo("app");
        assertThat(appList.getItems().get(1).getMetadata().getNamespace()).isEqualTo("myproject");
    }

    @Test
    public void should_execute_describe_command() {

        // Given

        final AppDescribeCommand appDescribeCommand = new AppDescribeCommand.Builder(appCommand, odoExecutor).build();

        // When

        final List<String> cliCommand = appDescribeCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactly(transform("app describe --output json"));
    }

    @Test
    public void should_describe_simple_app() throws IOException {

        // Given

        final List<String>
            appSimpleDescribe = Files.readAllLines(Paths.get("src/test/resources", "app_describe.json"));
        final AppDescribeCommand appDescribeCommand = new AppDescribeCommand.Builder(appCommand, odoExecutor).build();

        when(odoExecutor.execute(appDescribeCommand)).thenReturn(appSimpleDescribe);

        // When

        final TerminalOutput terminalOutput = appDescribeCommand.execute();

        // Then

        assertThat(terminalOutput.isSimple()).isTrue();

        final App app = terminalOutput.as(App.class);
        assertThat(app.getSpec().getComponents()).containsExactly("a", "b");
        assertThat(app.getStatus().isActive()).isTrue();
    }

    @Test
    public void should_describe_list_app() throws IOException {

        // Given

        final List<String>
            appSimpleDescribe = Files.readAllLines(Paths.get("src/test/resources", "app_list.json"));
        final AppDescribeCommand appDescribeCommand = new AppDescribeCommand.Builder(appCommand, odoExecutor).build();

        when(odoExecutor.execute(appDescribeCommand)).thenReturn(appSimpleDescribe);

        // When

        final TerminalOutput terminalOutput = appDescribeCommand.execute();

        // Then

        assertThat(terminalOutput.isSimple()).isFalse();

        final AppList appList = terminalOutput.as(AppList.class);
        assertThat(appList.getItems()).hasSize(2);
    }

}
