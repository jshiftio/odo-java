package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.CliExecutor;
import java.util.ArrayList;
import java.util.List;

public class ComponentUnlinkCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "unlink";

    private String name;

    private static final String APP = "--app";
    private static final String COMPONENT = "--component";
    private static final String PORT = "--port";
    private static final String PROJECT = "--project";

    private String app;
    private String component;
    private String port;
    private String project;

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

        arguments.addAll(componentCommand.getCliCommand());

        arguments.add(COMMAND_NAME);
        arguments.add(name);

        if (component != null) {
            arguments.add(COMPONENT);
            arguments.add(component);
        }

        if (app != null) {
            arguments.add(APP);
            arguments.add(app);
        }

        if (port != null) {
            arguments.add(PORT);
            arguments.add(port);
        }

        if (project != null) {
            arguments.add(PROJECT);
            arguments.add(project);
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<ComponentUnlinkCommand.Builder> {

        private ComponentUnlinkCommand componentUnlinkCommand;

        public Builder(ComponentCommand componentCommand, String name, CliExecutor odoExecutor) {
            componentUnlinkCommand = new ComponentUnlinkCommand(componentCommand, name, odoExecutor);
        }

        public ComponentUnlinkCommand.Builder withComponent(String component) {
            componentUnlinkCommand.component = component;
            return this;
        }

        public ComponentUnlinkCommand.Builder withApp(String app) {
            componentUnlinkCommand.app = app;
            return this;
        }

        public ComponentUnlinkCommand.Builder withPort(String port) {
            componentUnlinkCommand.port = port;
            return this;
        }

        public ComponentUnlinkCommand.Builder withProject(String project) {
            componentUnlinkCommand.project = project;
            return this;
        }

        public ComponentUnlinkCommand build() {
            this.componentUnlinkCommand.globalParametersSupport = buildGlobalParameters();
            return componentUnlinkCommand;
        }

    }
}
