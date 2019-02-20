package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import java.util.ArrayList;
import java.util.List;

public class ComponentPushCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "push";

    private String componentName;

    private static final String APP = "--app";
    private static final String LOCAL = "--local";
    private static final String PROJECT = "--project";

    private String app;
    private String local;
    private String project;

    private ComponentCommand componentCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ComponentPushCommand(ComponentCommand componentCommand, OdoExecutor odoExecutor){
        super(odoExecutor);
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

        if (local != null) {
            arguments.add(LOCAL);
            arguments.add(local);
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

    public static class Builder extends GlobalParametersSupport.Builder<ComponentPushCommand.Builder> {
        private ComponentPushCommand componentPushCommand;

        public Builder(ComponentCommand componentCommand, OdoExecutor odoExecutor) {
            this.componentPushCommand = new ComponentPushCommand(componentCommand, odoExecutor);
        }

        public ComponentPushCommand.Builder withComponentName(String componentName) {
            this.componentPushCommand.componentName = componentName;
            return this;
        }

        public ComponentPushCommand.Builder withApp(String app) {
            this.componentPushCommand.app = app;
            return this;
        }

        public ComponentPushCommand.Builder withLocal(String local) {
            this.componentPushCommand.local = local;
            return this;
        }

        public ComponentPushCommand.Builder withProject(String project) {
            this.componentPushCommand.project = project;
            return this;
        }

        public ComponentPushCommand build() {
            this.componentPushCommand.globalParametersSupport = buildGlobalParameters();
            return componentPushCommand;
        }
    }
}
