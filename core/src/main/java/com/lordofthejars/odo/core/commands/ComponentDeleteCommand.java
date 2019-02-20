package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import java.util.ArrayList;
import java.util.List;

public class ComponentDeleteCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "delete";

    private String componentName;

    private static final String APP = "--app";
    private static final String PROJECT = "--project";
    private static final String FORCE = "--force";

    private String app;
    private String project;
    private Boolean force = Boolean.TRUE;

    private ComponentCommand componentCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ComponentDeleteCommand(ComponentCommand componentCommand, String componentName, OdoExecutor odoExecutor){
        super(odoExecutor);
        this.componentName = componentName;
        this.componentCommand = componentCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(componentCommand.getCliCommand());

        arguments.add(COMMAND_NAME);
        arguments.add(componentName);

        if (app != null) {
            arguments.add(APP);
            arguments.add(app);
        }

        if (project != null) {
            arguments.add(PROJECT);
            arguments.add(project);
        }

        if (force != null && force.booleanValue()) {
            arguments.add(FORCE);
        }

        if (this.globalParametersSupport != null) {
            arguments.addAll(this.globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<ComponentDeleteCommand.Builder> {
        private ComponentDeleteCommand urComponentDeleteCommand;

        public Builder(ComponentCommand componentCommand, String componentName, OdoExecutor odoExecutor) {
            this.urComponentDeleteCommand = new ComponentDeleteCommand(componentCommand, componentName, odoExecutor);
        }

        public ComponentDeleteCommand.Builder withApp(String app) {
            this.urComponentDeleteCommand.app = app;
            return this;
        }

        public ComponentDeleteCommand.Builder withProject(String project) {
            this.urComponentDeleteCommand.project = project;
            return this;
        }

        public ComponentDeleteCommand.Builder withForce(boolean force) {
            this.urComponentDeleteCommand.force = force;
            return this;
        }

        public ComponentDeleteCommand build() {
            urComponentDeleteCommand.globalParametersSupport = buildGlobalParameters();
            return urComponentDeleteCommand;
        }

    }
}
