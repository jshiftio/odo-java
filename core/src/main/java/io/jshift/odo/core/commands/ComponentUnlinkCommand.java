package io.jshift.odo.core.commands;

import java.util.ArrayList;
import java.util.List;
import io.jshift.odo.core.CliExecutor;

public class ComponentUnlinkCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "unlink";

    private String name;

    private static final String COMPONENT = "--component";
    private static final String PORT = "--port";

    private String component;
    private String port;

    private ComponentCommand componentCommand;
    private GlobalParametersSupport globalParametersSupport;

    protected ComponentUnlinkCommand(ComponentCommand componentCommand, String name, CliExecutor odoExecutor) {
        super(odoExecutor);
        this.name = name;
        this.componentCommand = componentCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.add(componentCommand.getCommandName());

        arguments.add(COMMAND_NAME);
        arguments.add(name);


        if (component != null) {
            arguments.add(COMPONENT);
            arguments.add(component);
        }

        arguments.addAll(componentCommand.getArguments());
        if (port != null) {
            arguments.add(PORT);
            arguments.add(port);
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<ComponentUnlinkCommand.Builder> {

        private ComponentUnlinkCommand componentUnlinkCommand;
        private ComponentCommand componentCommand;

        public Builder(ComponentCommand componentCommand, String name, CliExecutor odoExecutor) {
            componentUnlinkCommand = new ComponentUnlinkCommand(componentCommand, name, odoExecutor);
            this.componentCommand = componentCommand;
        }

        public ComponentUnlinkCommand.Builder withComponent(String component) {
            componentUnlinkCommand.component = component;
            return this;
        }

        public ComponentUnlinkCommand.Builder withApp(String app) {
            componentCommand.app = app;
            return this;
        }

        public ComponentUnlinkCommand.Builder withPort(String port) {
            componentUnlinkCommand.port = port;
            return this;
        }

        public ComponentUnlinkCommand.Builder withProject(String project) {
            componentCommand.project = project;
            return this;
        }

        public ComponentUnlinkCommand.Builder withShort() {
            this.componentCommand.q = Boolean.TRUE;
            return this;
        }

        public ComponentUnlinkCommand.Builder withContext(String context) {
            this.componentCommand.context = context;
            return this;
        }

        public ComponentUnlinkCommand build() {
            this.componentUnlinkCommand.globalParametersSupport = buildGlobalParameters();
            return componentUnlinkCommand;
        }

    }
}
