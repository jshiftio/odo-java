package io.jshift.odo.core.commands;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import io.jshift.odo.core.CliExecutor;
import java.util.ArrayList;
import java.util.List;
import io.jshift.odo.core.commands.output.Component;
import io.jshift.odo.core.commands.output.ComponentList;
import io.jshift.odo.core.commands.output.TerminalOutput;

public class ComponentDescribeCommand extends AbstractRunnableCommand<TerminalOutput> {

    private static final String COMMAND_NAME = "describe";

    private String componentName;

    private static final String OUTPUT = "--output";
    private static final String DEFAULT_FORMAT = "json";

    private ComponentCommand componentCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ComponentDescribeCommand(ComponentCommand componentCommand, CliExecutor odoExecutor){
        super(odoExecutor, ComponentDescribeCommand::parse);
        this.componentCommand = componentCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.add(componentCommand.getCommandName());

        arguments.add(COMMAND_NAME);


        if (componentName != null) {
            arguments.add(componentName);
        }

        arguments.add(OUTPUT);
        arguments.add(DEFAULT_FORMAT);
        arguments.addAll(componentCommand.getArguments());

        if (this.globalParametersSupport != null) {
            arguments.addAll(this.globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    protected static TerminalOutput parse(List<String> consoleOutput) {
        final String output = String.join(" ", consoleOutput.toArray(new String[consoleOutput.size()]));
        final JsonValue outputJson = Json.parse(output);

        final JsonObject componentJson = outputJson.asObject();

        final JsonValue items = componentJson.get("items");

        if (items != null) {
            return ComponentList.from(componentJson);
        } else {
            return Component.from(componentJson);
        }
    }

    public static class Builder extends GlobalParametersSupport.Builder<ComponentDescribeCommand.Builder> {
        private ComponentDescribeCommand componentDescribeCommand;
        private ComponentCommand componentCommand;

        public Builder(ComponentCommand componentCommand, CliExecutor odoExecutor) {
            this.componentDescribeCommand = new ComponentDescribeCommand(componentCommand, odoExecutor);
            this.componentCommand = componentCommand;
        }

        public ComponentDescribeCommand.Builder withComponentName(String componentName) {
            this.componentDescribeCommand.componentName = componentName;
            return this;
        }

        public ComponentDescribeCommand.Builder withApp(String app) {
            this.componentCommand.app = app;
            return this;
        }

        public ComponentDescribeCommand.Builder withProject(String project) {
            this.componentCommand.project = project;
            return this;
        }

        public ComponentDescribeCommand.Builder withShort() {
            this.componentCommand.q = Boolean.TRUE;
            return this;
        }

        public ComponentDescribeCommand.Builder withContext(String context) {
            this.componentCommand.context = context;
            return this;
        }

        public ComponentDescribeCommand build() {
            componentDescribeCommand.globalParametersSupport = buildGlobalParameters();
            return componentDescribeCommand;
        }

    }
}
