package io.jshift.odo.core.commands;

import java.util.ArrayList;
import java.util.List;
import io.jshift.odo.core.CliExecutor;

public class ComponentDeleteCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "delete";

    private String componentName;

    private static final String FORCE = "--force";

    private Boolean force = Boolean.TRUE;

    private ComponentCommand componentCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ComponentDeleteCommand(ComponentCommand componentCommand, String componentName, CliExecutor odoExecutor){
        super(odoExecutor);
        this.componentName = componentName;
        this.componentCommand = componentCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.add(componentCommand.getCommandName());

        arguments.add(COMMAND_NAME);
        arguments.add(componentName);

        arguments.addAll(componentCommand.getArguments());

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
        private ComponentCommand componentCommand;

        public Builder(ComponentCommand componentCommand, String componentName, CliExecutor odoExecutor) {
            this.urComponentDeleteCommand = new ComponentDeleteCommand(componentCommand, componentName, odoExecutor);
            this.componentCommand = componentCommand;
        }

        public ComponentDeleteCommand.Builder withApp(String app) {
            this.componentCommand.app = app;
            return this;
        }

        public ComponentDeleteCommand.Builder withProject(String project) {
            this.componentCommand.project = project;
            return this;
        }

        public ComponentDeleteCommand.Builder withShort() {
            this.componentCommand.q = Boolean.TRUE;
            return this;
        }

        public ComponentDeleteCommand.Builder withContext(String context) {
            this.componentCommand.context = context;
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
