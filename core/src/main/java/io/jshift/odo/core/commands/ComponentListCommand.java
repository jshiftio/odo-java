package io.jshift.odo.core.commands;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import io.jshift.odo.core.CliExecutor;
import java.util.ArrayList;
import java.util.List;
import io.jshift.odo.core.commands.output.ComponentList;

public class ComponentListCommand extends AbstractRunnableCommand<ComponentList> {

    private static final String COMMAND_NAME = "list";

    private static final String OUTPUT = "--output";
    private static final String DEFAULT_FORMAT = "json";
    private static final String PATH = "--path";

    private String path;
    private ComponentCommand componentCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ComponentListCommand(ComponentCommand componentCommand, CliExecutor odoExecutor) {
        super(odoExecutor, ComponentListCommand::parse);
        this.componentCommand = componentCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.add(componentCommand.getCommandName());

        arguments.add(COMMAND_NAME);

        arguments.add(OUTPUT);
        arguments.add(DEFAULT_FORMAT);

        arguments.addAll(componentCommand.getArguments());

        if (path != null) {
            arguments.add(PATH);
            arguments.add(path);
        }

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
        private ComponentCommand componentCommand;

        public Builder(ComponentCommand componentCommand, CliExecutor odoExecutor) {
            this.componentListCommand = new ComponentListCommand(componentCommand, odoExecutor);
            this.componentCommand = componentCommand;
        }

        public ComponentListCommand.Builder withApp(String app) {
            this.componentCommand.app = app;
            return this;
        }

        public ComponentListCommand.Builder withProject(String project) {
            this.componentCommand.project = project;
            return this;
        }

        public ComponentListCommand.Builder withShort() {
            this.componentCommand.q = Boolean.TRUE;
            return this;
        }

        public ComponentListCommand.Builder withPath(String path) {
            this.componentListCommand.path = path;
            return this;
        }

        public ComponentListCommand.Builder withContext(String context) {
            this.componentCommand.context = context;
            return this;
        }

        public ComponentListCommand build() {
            componentListCommand.globalParametersSupport = buildGlobalParameters();
            return componentListCommand;
        }

    }

}
