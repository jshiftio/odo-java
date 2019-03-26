package io.jshift.odo.core.commands;

import java.util.ArrayList;
import java.util.List;
import io.jshift.odo.core.CliExecutor;

public class ProjectCreateCommand extends AbstractRunnableCommand<Void> {
    private static final String COMMAND_NAME = "create";

    private String projectName;
    private ProjectCommand projectCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ProjectCreateCommand(ProjectCommand projectCommand, String projectName, CliExecutor odoExecutor){
        super(odoExecutor);
        this.projectCommand = projectCommand;
        this.projectName = projectName;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(projectCommand.getCliCommand());

        arguments.add(COMMAND_NAME);
        arguments.add(projectName);

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<ProjectCreateCommand.Builder> {
        private ProjectCreateCommand projectCreateCommand;

        public Builder(ProjectCommand projectCommand, String projectName, CliExecutor odoExecutor) {
            this.projectCreateCommand = new ProjectCreateCommand(projectCommand, projectName, odoExecutor);
        }

        public ProjectCreateCommand build() {
            this.projectCreateCommand.globalParametersSupport = buildGlobalParameters();
            return projectCreateCommand;
        }
    }
}
