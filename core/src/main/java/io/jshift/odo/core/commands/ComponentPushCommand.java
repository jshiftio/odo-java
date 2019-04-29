package io.jshift.odo.core.commands;

import java.util.ArrayList;
import java.util.List;
import io.jshift.odo.core.CliExecutor;

public class ComponentPushCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "push";

    private String componentName;

    private static final String LOCAL = "--local";
    private static final String CONFIG = "--config";
    private static final String SOURCE = "--source";

    private String local;
    private Boolean config;
    private Boolean source;

    private ComponentCommand componentCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ComponentPushCommand(ComponentCommand componentCommand, CliExecutor odoExecutor){
        super(odoExecutor);
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

        arguments.addAll(componentCommand.getArguments());
        if (local != null) {
            arguments.add(LOCAL);
            arguments.add(local);
        }

        if (config != null && config) {
            arguments.add(CONFIG);
        }

        if (source != null && source) {
            arguments.add(SOURCE);
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<ComponentPushCommand.Builder> {
        private ComponentPushCommand componentPushCommand;
        private ComponentCommand componentCommand;

        public Builder(ComponentCommand componentCommand, CliExecutor odoExecutor) {
            this.componentPushCommand = new ComponentPushCommand(componentCommand, odoExecutor);
            this.componentCommand = componentCommand;
        }

        public ComponentPushCommand.Builder withComponentName(String componentName) {
            this.componentPushCommand.componentName = componentName;
            return this;
        }

        public ComponentPushCommand.Builder withApp(String app) {
            this.componentCommand.app = app;
            return this;
        }

        public ComponentPushCommand.Builder withLocal(String local) {
            this.componentPushCommand.local = local;
            return this;
        }

        public ComponentPushCommand.Builder withConfig() {
            this.componentPushCommand.config = Boolean.TRUE;
            return this;
        }

        public ComponentPushCommand.Builder withSource() {
            this.componentPushCommand.source = Boolean.TRUE;
            return this;
        }

        public ComponentPushCommand.Builder withProject(String project) {
            this.componentCommand.project = project;
            return this;
        }

        public ComponentPushCommand.Builder withShort() {
            this.componentCommand.q = Boolean.TRUE;
            return this;
        }

        public ComponentPushCommand.Builder withContext(String context) {
            this.componentCommand.context = context;
            return this;
        }



        public ComponentPushCommand build() {
            this.componentPushCommand.globalParametersSupport = buildGlobalParameters();
            return componentPushCommand;
        }
    }
}
