package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.CliExecutor;
import java.util.ArrayList;
import java.util.List;

public class ComponentLinkCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "link";

    private String name;

    private static final String APP = "--app";
    private static final String COMPONENT = "--component";
    private static final String PORT = "--port";
    private static final String PROJECT = "--project";
    private static final String WAIT = "--wait";

    private String app;
    private String component;
    private String port;
    private String project;
    private Boolean wait;

    private ComponentCommand componentCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ComponentLinkCommand(ComponentCommand componentCommand, String name, CliExecutor odoExecutor) {
        super(odoExecutor);
        this.name =name;
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

        if (wait != null && wait.booleanValue()) {
            arguments.add(WAIT);
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<ComponentLinkCommand.Builder> {

        private ComponentLinkCommand componentLinkCommand;

        public Builder(ComponentCommand componentCommand, String name, CliExecutor odoExecutor) {
            componentLinkCommand = new ComponentLinkCommand(componentCommand, name, odoExecutor);
        }

        public ComponentLinkCommand.Builder withComponent(String component) {
            componentLinkCommand.component = component;
            return this;
        }

        public ComponentLinkCommand.Builder withApp(String app) {
            componentLinkCommand.app = app;
            return this;
        }

        public ComponentLinkCommand.Builder withPort(String port) {
            componentLinkCommand.port = port;
            return this;
        }

        public ComponentLinkCommand.Builder withProject(String project) {
            componentLinkCommand.project = project;
            return this;
        }

        public ComponentLinkCommand.Builder withWait() {
            componentLinkCommand.wait = Boolean.TRUE;
            return this;
        }

        public ComponentLinkCommand build() {
            this.componentLinkCommand.globalParametersSupport = buildGlobalParameters();
            return componentLinkCommand;
        }

    }

}
