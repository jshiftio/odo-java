package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;

import java.util.ArrayList;
import java.util.List;

public class ProjectDeleteCommand implements Command {
    private static final String COMMAND_NAME = "delete";
    private static final String FORCE = "--force";

    private String projectName;
    private Boolean force = Boolean.TRUE;

    private GlobalParametersSupport globalParametersSupport;

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.add(COMMAND_NAME);
        arguments.add(projectName);

        if (force != null && force.booleanValue()) {
            arguments.add(FORCE);
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<ProjectDeleteCommand.Builder> {
        private ProjectDeleteCommand projectDeleteCommand;

        public Builder() {
            this.projectDeleteCommand = new ProjectDeleteCommand();
        }

        public ProjectDeleteCommand.Builder name(String projectName) {
            this.projectDeleteCommand.projectName = projectName;
            return this;
        }

        public ProjectDeleteCommand.Builder withForce(boolean force) {
            this.projectDeleteCommand.force = force;
            return this;
        }

        public ProjectDeleteCommand build() {
            this.projectDeleteCommand.globalParametersSupport = buildGlobalParameters();
            return projectDeleteCommand;
        }
    }
}
