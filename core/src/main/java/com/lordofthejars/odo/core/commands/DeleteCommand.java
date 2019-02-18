package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class DeleteCommand implements Command {

    private static final String COMMAND_NAME = "delete";

    private String componentName;

    private static final String APP = "--app";
    private static final String PROJECT = "--project";
    private static final String FORCE = "--force";

    private String app;
    private String project;
    private Boolean force = Boolean.TRUE;

    private GlobalParametersSupport globalParametersSupport;
    private List<String> extraCommands;

    private DeleteCommand(String componentName){
        this.componentName = componentName;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
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

        if (extraCommands != null) {
            arguments.addAll(extraCommands);
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<DeleteCommand.Builder> {
        private DeleteCommand urDeleteCommand;

        public Builder(String componentName) {
            this.urDeleteCommand = new DeleteCommand(componentName);
        }

        public DeleteCommand.Builder withApp(String app) {
            this.urDeleteCommand.app = app;
            return this;
        }

        public DeleteCommand.Builder withProject(String project) {
            this.urDeleteCommand.project = project;
            return this;
        }

        public DeleteCommand.Builder withForce(boolean force) {
            this.urDeleteCommand.force = force;
            return this;
        }

        public DeleteCommand.Builder withExtraArguments(List<String> extraArguments) {
            this.urDeleteCommand.extraCommands = extraArguments;
            return this;
        }

        public DeleteCommand build() {
            urDeleteCommand.globalParametersSupport = buildGlobalParameters();
            return urDeleteCommand;
        }

    }
}
