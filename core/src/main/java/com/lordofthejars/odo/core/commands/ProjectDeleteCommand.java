package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.CliExecutor;
import java.util.ArrayList;
import java.util.List;

public class ProjectDeleteCommand extends AbstractRunnableCommand<Void> {
    private static final String COMMAND_NAME = "delete";
    private static final String FORCE = "--force";

    private String projectName;
    private Boolean force = Boolean.TRUE;

    private ProjectCommand projectCommand;

    private ProjectDeleteCommand(ProjectCommand projectCommand, String projectName, CliExecutor odoExecutor) {
        super(odoExecutor);
        this.projectName = projectName;
        this.projectCommand = projectCommand;
    }

    private GlobalParametersSupport globalParametersSupport;

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(projectCommand.getCliCommand());

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

        public Builder(ProjectCommand projectCommand, String projectName, CliExecutor odoExeuctor) {
            this.projectDeleteCommand = new ProjectDeleteCommand(projectCommand, projectName, odoExeuctor);
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
