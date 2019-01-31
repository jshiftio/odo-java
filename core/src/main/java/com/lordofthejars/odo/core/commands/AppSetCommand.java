package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class AppSetCommand implements Command {
    private static final String COMMAND_NAME = "set";

    private String appName;

    private static final String PROJECT = "--project";

    private String project;

    private List<String> extraCommands;

    private AppSetCommand(String appName){
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

        if (extraCommands != null) {
            arguments.addAll(extraCommands);
        }

        return arguments;
    }

    public static class Builder {
        private AppSetCommand appCreateCommand;

        public Builder(String appName) {
            this.appCreateCommand = new AppSetCommand(appName);
        }

        public AppSetCommand.Builder withProject(String project) {
            this.appCreateCommand.project = project;
            return this;
        }

        public AppSetCommand.Builder withExtraArguments(List<String> extraArguments) {
            this.appCreateCommand.extraCommands = extraArguments;
            return this;
        }

        public AppSetCommand build() {
            return appCreateCommand;
        }
    }
}
