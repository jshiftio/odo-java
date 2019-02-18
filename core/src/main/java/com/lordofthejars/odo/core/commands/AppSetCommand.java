package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class AppSetCommand implements Command {
    private static final String COMMAND_NAME = "set";

    private String appName;

    private static final String PROJECT = "--project";

    private String project;

    private GlobalParametersSupport globalParametersSupport;
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

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        if (extraCommands != null) {
            arguments.addAll(extraCommands);
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<AppSetCommand.Builder> {
        private AppSetCommand appSetCommand;

        public Builder(String appName) {
            this.appSetCommand = new AppSetCommand(appName);
        }

        public AppSetCommand.Builder withProject(String project) {
            this.appSetCommand.project = project;
            return this;
        }

        public AppSetCommand.Builder withExtraArguments(List<String> extraArguments) {
            this.appSetCommand.extraCommands = extraArguments;
            return this;
        }

        public AppSetCommand build() {
            appSetCommand.globalParametersSupport = buildGlobalParameters();
            return appSetCommand;
        }
    }
}
