package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class AppDeleteCommand implements Command {

    private static final String COMMAND_NAME = "delete";

    private String appName;

    private static final String PROJECT = "--project";
    private static final String FORCE = "--force";

    private String project;
    private Boolean force = Boolean.TRUE;

    private GlobalParametersSupport globalParametersSupport;

    private List<String> extraCommands;

    private AppDeleteCommand(String appName){
        this.appName = appName;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);
        arguments.add(appName);

        if (project != null) {
            arguments.add(PROJECT);
            arguments.add(project);
        }

        if (force != null && force.booleanValue()) {
            arguments.add(FORCE);
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        if (extraCommands != null) {
            arguments.addAll(extraCommands);
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<AppDeleteCommand.Builder> {
        private AppDeleteCommand appDeleteCommand;

        public Builder(String appName) {
            this.appDeleteCommand = new AppDeleteCommand(appName);
        }


        public AppDeleteCommand.Builder withProject(String project) {
            this.appDeleteCommand.project = project;
            return this;
        }

        public AppDeleteCommand.Builder withForce(boolean force) {
            this.appDeleteCommand.force = force;
            return this;
        }

        public AppDeleteCommand.Builder withExtraArguments(List<String> extraArguments) {
            this.appDeleteCommand.extraCommands = extraArguments;
            return this;
        }

        public AppDeleteCommand build() {
            appDeleteCommand.globalParametersSupport = buildGlobalParameters();
            return appDeleteCommand;
        }
    }

}
