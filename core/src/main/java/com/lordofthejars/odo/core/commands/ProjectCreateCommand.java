package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;

import java.util.ArrayList;
import java.util.List;

public class ProjectCreateCommand implements Command {
    private static final String COMMAND_NAME = "create";

    private String projectName;
    private GlobalParametersSupport globalParametersSupport;

    private ProjectCreateCommand(){
    }

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

    public static class Builder extends GlobalParametersSupport.Builder<ProjectCreateCommand.Builder> {
        private ProjectCreateCommand projectCreateCommand;

        public Builder() {
            this.projectCreateCommand = new ProjectCreateCommand();
        }

        public ProjectCreateCommand.Builder withName(String projectName) {
            this.projectCreateCommand.projectName = projectName;
            return this;
        }

        public ProjectCreateCommand build() {
            this.projectCreateCommand.globalParametersSupport = buildGlobalParameters();
            return projectCreateCommand;
        }
    }
}
