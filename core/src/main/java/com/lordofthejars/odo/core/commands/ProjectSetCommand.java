package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;

import java.util.ArrayList;
import java.util.List;

public class ProjectSetCommand implements Command {
    private static final String COMMAND_NAME = "set";

    private String projectName;

    private GlobalParametersSupport globalParametersSupport;

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.add(COMMAND_NAME);
        arguments.add(projectName);

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<ProjectSetCommand.Builder> {
        private ProjectSetCommand projectSetCommand;

        public Builder() {
            projectSetCommand = new ProjectSetCommand();
        }

        public ProjectSetCommand.Builder name(String projectName) {
            this.projectSetCommand.projectName = projectName;
            return this;
        }

        public ProjectSetCommand build() {
            projectSetCommand.globalParametersSupport = buildGlobalParameters();
            return projectSetCommand;
        }
    }

}
