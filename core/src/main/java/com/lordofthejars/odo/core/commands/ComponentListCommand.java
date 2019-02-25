package com.lordofthejars.odo.core.commands;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.lordofthejars.odo.core.OdoExecutor;
import com.lordofthejars.odo.core.commands.output.ComponentList;
import java.util.ArrayList;
import java.util.List;

public class ComponentListCommand extends AbstractRunnableCommand<ComponentList> {

    private static final String COMMAND_NAME = "list";

    private static final String APP = "--app";
    private static final String PROJECT = "--project";
    private static final String OUTPUT = "--output";
    private static final String DEFAULT_FORMAT = "json";

    private String app;
    private String project;

    private ComponentCommand componentCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ComponentListCommand(ComponentCommand componentCommand, OdoExecutor odoExecutor) {
        super(odoExecutor, ComponentListCommand::parse);
        this.componentCommand = componentCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(componentCommand.getCliCommand());

        arguments.add(COMMAND_NAME);

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

    protected static ComponentList parse(List<String> consoleOutput) {
        final String output = String.join(" ", consoleOutput.toArray(new String[consoleOutput.size()]));
        final JsonValue outputJson = Json.parse(output);

        final JsonObject componentJson = outputJson.asObject();
        return ComponentList.from(componentJson);
    }

    public static class Builder extends GlobalParametersSupport.Builder<ComponentListCommand.Builder> {
        private ComponentListCommand componentListCommand;

        public Builder(ComponentCommand componentCommand, OdoExecutor odoExecutor) {
            this.componentListCommand = new ComponentListCommand(componentCommand, odoExecutor);
        }

        public ComponentListCommand.Builder withApp(String app) {
            this.componentListCommand.app = app;
            return this;
        }

        public ComponentListCommand.Builder withProject(String project) {
            this.componentListCommand.project = project;
            return this;
        }

        public ComponentListCommand build() {
            componentListCommand.globalParametersSupport = buildGlobalParameters();
            return componentListCommand;
        }

    }

}
