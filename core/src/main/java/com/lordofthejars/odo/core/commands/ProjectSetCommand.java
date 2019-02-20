package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import java.util.ArrayList;
import java.util.List;

public class ProjectSetCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "set";
    private static final String SHORT = "--short";

    private String projectName;
    private Boolean shortDesc;

    private ProjectCommand projectCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ProjectSetCommand(ProjectCommand projectCommand, String projectName, OdoExecutor odoExecutor) {
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

        if (shortDesc != null && shortDesc.booleanValue()) {
            arguments.add(SHORT);
            arguments.add(Boolean.toString(shortDesc));
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<ProjectSetCommand.Builder> {
        private ProjectSetCommand projectSetCommand;

        public Builder(ProjectCommand projectCommand, String projectName, OdoExecutor odoExecutor) {
            projectSetCommand = new ProjectSetCommand(projectCommand, projectName, odoExecutor);
        }

        public Builder withShort(Boolean shortDesc) {
            projectSetCommand.shortDesc = shortDesc;
            return this;
        }

        public ProjectSetCommand build() {
            projectSetCommand.globalParametersSupport = buildGlobalParameters();
            return projectSetCommand;
        }
    }

}
