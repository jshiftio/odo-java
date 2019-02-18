package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;

import java.util.ArrayList;
import java.util.List;

public class UpdateProjectCommand implements Command {
    private static final String COMMAND_FLAG = "--project";

    private String component;
    private String projectName;

    private GlobalParametersSupport globalParametersSupport;

    private UpdateProjectCommand(){
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        if (component!= null) {
            arguments.add(component);
        }

        arguments.add(COMMAND_FLAG);

        if (projectName != null) {
            arguments.add(projectName);
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<UpdateProjectCommand.Builder> {
        private UpdateProjectCommand updateProjectCommand;

        public Builder() {
            this.updateProjectCommand = new UpdateProjectCommand();
        }

        public UpdateProjectCommand.Builder forComponent(String component) {
            this.updateProjectCommand.component = component;
            return this;
        }

        public UpdateProjectCommand.Builder project(String projectName) {
            this.updateProjectCommand.projectName = projectName;
            return this;
        }

        public UpdateProjectCommand build() {
            updateProjectCommand.globalParametersSupport = buildGlobalParameters();
            return updateProjectCommand;
        }
    }
}