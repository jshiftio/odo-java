package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.CliExecutor;
import com.lordofthejars.odo.core.commands.output.Component;
import com.lordofthejars.odo.core.commands.output.ComponentList;
import com.lordofthejars.odo.core.commands.output.TerminalOutput;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lordofthejars.odo.core.commands.CommandTransformer.transform;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ComponentCommandTest {

    @Mock
    private CliExecutor odoExecutor;

    private ComponentCommand componentCommand = new ComponentCommand.Builder().build();

    @Test
    public void should_execute_create_command() {

        // Given

        final ComponentCreateCommand componentCreateCommand = new ComponentCreateCommand.Builder(componentCommand, "nodejs", odoExecutor)
            .withLocal("/path")
            .build();

        // When

        final List<String> cliCommand = componentCreateCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder("component", "create", "nodejs", "--local", "/path");
    }

    @Test
    public void should_execute_link_command() {

        // Given

        final ComponentLinkCommand componentLinkCommand = new ComponentLinkCommand
            .Builder(componentCommand, "backend", odoExecutor)
            .withComponent("frontend")
            .build();

        // When

        final List<String> cliCommand = componentLinkCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("component link backend --component frontend"));

    }

    @Test
    public void should_execute_push_command() {

        // Given

        final ComponentPushCommand
            componentPushCommand = new ComponentPushCommand.Builder(componentCommand, odoExecutor).withApp("application").build();

        // When

        final List<String> cliCommand = componentPushCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder("component", "push", "--app", "application");
    }

    @Test
    public void should_execute_push_with_global_parameters() {

        // Given

        ComponentPushCommand componentPushCommand = new ComponentPushCommand.Builder(componentCommand, odoExecutor)
            .withApp("application")
            .withV(4)
            .build();

        // When

        final List<String> cliCommand = componentPushCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder("component", "push", "--app", "application", "--v", "4");
    }

    @Test
    public void should_create_unlink_command() {

        // Given

        final ComponentUnlinkCommand componentUnlinkCommand = new ComponentUnlinkCommand.Builder(componentCommand, "backend", odoExecutor).build();

        // When

        final List<String> cliCommand = componentUnlinkCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("component unlink backend"));
    }

    @Test
    public void shouldExecuteUpdateProjectCommand() {

        // Given

        final ComponentUpdateCommand updateProjectCommand = new ComponentUpdateCommand.Builder(componentCommand, odoExecutor)
            .withComponentName("mycomponent")
            .withProject("myproject")
            .build();

        // When

        final List<String> cliCommand = updateProjectCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("component update mycomponent --project myproject"));

    }

    @Test
    public void shouldExecuteUpdateAppCommand() {
        // Given

        final ComponentUpdateCommand updateAppCommand = new ComponentUpdateCommand.Builder(componentCommand, odoExecutor)
            .withComponentName("mycomponent")
            .withApp("myapp")
            .build();

        // When

        final List<String> cliCommand = updateAppCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("component update mycomponent --app myapp"));
    }

    @Test
    public void shouldExecuteUpdateBinaryCommand() {
        // Given

        final ComponentUpdateCommand updateBinaryCommand = new ComponentUpdateCommand.Builder(componentCommand, odoExecutor)
            .withComponentName("mycomponent")
            .withBinary("./target/bin")
            .build();

        // When

        final List<String> cliCommand = updateBinaryCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("component update mycomponent --binary ./target/bin"));
    }

    @Test
    public void shouldExecuteUpdateRefCommand() {
        // Given

        final ComponentUpdateCommand updateRefCommand = new ComponentUpdateCommand.Builder(componentCommand, odoExecutor)
            .withComponentName("mycomponent")
            .withRef("testrefname")
            .build();

        // When

        final List<String> cliCommand = updateRefCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("component update mycomponent --ref testrefname"));
    }

    @Test
    public void shouldExecuteUpdateGitCommand() {
        // Given

        final ComponentUpdateCommand updateGitCommand = new ComponentUpdateCommand.Builder(componentCommand, odoExecutor)
            .withComponentName("mycomponent")
            .withGit("git-url")
            .build();

        // When

        final List<String> cliCommand = updateGitCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("component update mycomponent --git git-url"));
    }

    @Test
    public void shouldExecuteUpdateLocalCommand() {
        // Given

        final ComponentUpdateCommand updateLocalCommand = new ComponentUpdateCommand.Builder(componentCommand, odoExecutor)
            .withComponentName("mycomponent")
            .withLocal("./target/dir")
            .build();

        // When

        final List<String> cliCommand = updateLocalCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("component update mycomponent --local ./target/dir"));
    }

    @Test
    public void should_execute_describe_component_command() {

        // Given

        final ComponentDescribeCommand componentDescribeCommand = new ComponentDescribeCommand.Builder(componentCommand, odoExecutor).build();

        // When

        final List<String> cliCommand = componentDescribeCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactly("component", "describe", "--output", "json");

    }

    @Test
    public void should_describe_simple_component() throws IOException {

        // Given
        final List<String> simpleDescribe = Files.readAllLines(Paths.get("src/test/resources", "component_describe.json"));
        final ComponentDescribeCommand componentDescribeCommand = new ComponentDescribeCommand.Builder(componentCommand, odoExecutor).build();

        when(odoExecutor.execute(componentDescribeCommand)).thenReturn(simpleDescribe);

        // When

        final TerminalOutput terminalOutput = componentDescribeCommand.execute();

        // Then

        assertThat(terminalOutput.isSimple()).isTrue();

        final Component component = terminalOutput.as(Component.class);

        assertThat(component.getTypeMeta().getKind()).isEqualToIgnoringCase("Component");
        assertThat(component.getMetadata().getName()).isEqualToIgnoringCase("nodejs");

        assertThat(component.getStatus().isActive()).isTrue();
        assertThat(component.getSpec().getType()).isEqualTo("nodejs");
        assertThat(component.getSpec().getUrl()).containsExactlyInAnyOrder("route");
        assertThat(component.getSpec().getSource()).isEqualTo("file:///private/var/folders/xd/_pglfgy97snfpv5pfd74qjtr0000gn/T/odo-test4230570567731660381/nodejs-ex");
    }

    @Test
    public void should_describe_multiple_component() throws IOException {

        // Given
        final List<String> listDescribe = Files.readAllLines(Paths.get("src/test/resources", "component_list.json"));
        final ComponentDescribeCommand componentDescribeCommand = new ComponentDescribeCommand.Builder(componentCommand, odoExecutor).build();

        when(odoExecutor.execute(componentDescribeCommand)).thenReturn(listDescribe);

        // When

        final TerminalOutput terminalOutput = componentDescribeCommand.execute();

        // Then

        assertThat(terminalOutput.isList()).isTrue();

        final ComponentList componentList = terminalOutput.as(ComponentList.class);
        assertThat(componentList.getItems()).hasSize(1);

        final Component component = componentList.getItems().get(0);

        assertThat(component.getStatus().isActive()).isTrue();
        assertThat(component.getSpec().getType()).isEqualTo("nodejs");
        assertThat(component.getSpec().getUrl()).containsExactlyInAnyOrder("route");
        assertThat(component.getSpec().getSource()).isEqualTo("file:///private/var/folders/xd/_pglfgy97snfpv5pfd74qjtr0000gn/T/odo-test4230570567731660381/nodejs-ex");
    }

    @Test
    public void should_execute_component_list_command() {

        // Given

        final ComponentListCommand componentListCommand = new ComponentListCommand.Builder(componentCommand, odoExecutor).build();

        // When

        final List<String> cliCommand = componentListCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactly("component", "list", "--output", "json");

    }

    @Test
    public void should_list_components() throws IOException {

        // Given

        final List<String> listDescribe = Files.readAllLines(Paths.get("src/test/resources", "component_multiple_list.json"));
        final ComponentListCommand componentListCommand = new ComponentListCommand.Builder(componentCommand, odoExecutor).build();

        when(odoExecutor.execute(componentListCommand)).thenReturn(listDescribe);

        // When

        final ComponentList componentList = componentListCommand.execute();

        // Then

        assertThat(componentList.getTypeMeta().getKind()).isEqualToIgnoringCase("List");
        assertThat(componentList.getMetadata().getName()).isNullOrEmpty();
        assertThat(componentList.getItems()).hasSize(2);
    }


}
