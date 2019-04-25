package io.jshift.odo.core.commands;

import java.util.ArrayList;
import java.util.List;
import io.jshift.odo.core.CliExecutor;

public class ComponentLinkCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "link";

    private String name;

    private static final String COMPONENT = "--component";
    private static final String PORT = "--port";
    private static final String WAIT = "--wait";
    private static final String WAIT_FOR_TARGET = "--wait-for-target";

    private String component;
    private String port;
    private Boolean wait;
    private Boolean waitForTarget;

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

        if (wait != null && wait.booleanValue()) {
            arguments.add(WAIT);
        }

        if (waitForTarget != null && waitForTarget.booleanValue()) {
            arguments.add(WAIT_FOR_TARGET);
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<ComponentLinkCommand.Builder> {

        private ComponentLinkCommand componentLinkCommand;
        private ComponentCommand componentCommand;

        public Builder(ComponentCommand componentCommand, String name, CliExecutor odoExecutor) {
            componentLinkCommand = new ComponentLinkCommand(componentCommand, name, odoExecutor);
            this.componentCommand = componentCommand;
        }

        public ComponentLinkCommand.Builder withComponent(String component) {
            componentLinkCommand.component = component;
            return this;
        }

        public ComponentLinkCommand.Builder withApp(String app) {
            componentCommand.app = app;
            return this;
        }

        public ComponentLinkCommand.Builder withPort(String port) {
            componentLinkCommand.port = port;
            return this;
        }

        public ComponentLinkCommand.Builder withProject(String project) {
            componentCommand.project = project;
            return this;
        }

        public ComponentLinkCommand.Builder withShort() {
            this.componentCommand.q = Boolean.TRUE;
            return this;
        }

        public ComponentLinkCommand.Builder withContext(String context) {
            this.componentCommand.context = context;
            return this;
        }

        public ComponentLinkCommand.Builder withWait() {
            componentLinkCommand.wait = Boolean.TRUE;
            return this;
        }

        public ComponentLinkCommand.Builder withWaitForTarget() {
            componentLinkCommand.waitForTarget = Boolean.TRUE;
            return this;
        }

        public ComponentLinkCommand build() {
            this.componentLinkCommand.globalParametersSupport = buildGlobalParameters();
            return componentLinkCommand;
        }

    }

}
