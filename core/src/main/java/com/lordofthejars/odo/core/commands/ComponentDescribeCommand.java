package com.lordofthejars.odo.core.commands;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.lordofthejars.odo.core.OdoExecutor;
import com.lordofthejars.odo.core.commands.output.Component;
import com.lordofthejars.odo.core.commands.output.ComponentList;
import com.lordofthejars.odo.core.commands.output.TerminalOutput;
import java.util.ArrayList;
import java.util.List;

public class ComponentDescribeCommand extends AbstractRunnableCommand<TerminalOutput> {

    private static final String COMMAND_NAME = "describe";

    private String componentName;

    private static final String APP = "--app";
    private static final String PROJECT = "--project";
    private static final String OUTPUT = "--output";
    private static final String DEFAULT_FORMAT = "json";

    private String app;
    private String project;

    private ComponentCommand componentCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ComponentDescribeCommand(ComponentCommand componentCommand, OdoExecutor odoExecutor){
        super(odoExecutor, ComponentDescribeCommand::parse);
        this.componentCommand = componentCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(componentCommand.getCliCommand());

        arguments.add(COMMAND_NAME);

        if (componentName != null) {
            arguments.add(componentName);
        }

        if (app != null) {
            arguments.add(APP);
            arguments.add(app);
        }

        if (project != null) {
            arguments.add(PROJECT);
            arguments.add(project);
        }

        arguments.add(OUTPUT);
        arguments.add(DEFAULT_FORMAT);

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

        public Builder(ComponentCommand componentCommand, OdoExecutor odoExecutor) {
            this.componentDescribeCommand = new ComponentDescribeCommand(componentCommand, odoExecutor);
        }

        public ComponentDescribeCommand.Builder withComponentName(String componentName) {
            this.componentDescribeCommand.componentName = componentName;
            return this;
        }

        public ComponentDescribeCommand.Builder withApp(String app) {
            this.componentDescribeCommand.app = app;
            return this;
        }

        public ComponentDescribeCommand.Builder withProject(String project) {
            this.componentDescribeCommand.project = project;
            return this;
        }

        public ComponentDescribeCommand build() {
            componentDescribeCommand.globalParametersSupport = buildGlobalParameters();
            return componentDescribeCommand;
        }

    }
}
