package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class AppCreateCommand implements Command {

    private static final String COMMAND_NAME = "create";

    private String appName;

    private static final String PROJECT = "--project";

    private String project;

    private List<String> extraCommands;

    private AppCreateCommand(){
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        if (appName != null) {
            arguments.add(appName);
        }

        if (project != null) {
            arguments.add(PROJECT);
            arguments.add(project);
        }

        return arguments;
    }

    public static class Builder {
        private AppCreateCommand appCreateCommand;

        public Builder() {
            this.appCreateCommand = new AppCreateCommand();
        }

        public AppCreateCommand.Builder withAppName(String app) {
            this.appCreateCommand.appName = app;
            return this;
        }

        public AppCreateCommand.Builder withProject(String project) {
            this.appCreateCommand.project = project;
            return this;
        }

        public AppCreateCommand build() {
            return appCreateCommand;
        }
    }
}
