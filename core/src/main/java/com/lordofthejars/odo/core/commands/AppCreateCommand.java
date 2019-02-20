package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import java.util.ArrayList;
import java.util.List;

public class AppCreateCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "create";

    private String appName;

    private static final String PROJECT = "--project";

    private String project;

    private AppCommand appCommand;
    private GlobalParametersSupport globalParametersSupport;

    private AppCreateCommand(AppCommand appCommand, OdoExecutor odoExecutor){
        super(odoExecutor);
        this.appCommand = appCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(appCommand.getCliCommand());
        arguments.add(COMMAND_NAME);

        if (appName != null) {
            arguments.add(appName);
        }

        if (project != null) {
            arguments.add(PROJECT);
            arguments.add(project);
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<AppCreateCommand.Builder> {
        private AppCreateCommand appCreateCommand;

        public Builder(AppCommand appCommand, OdoExecutor odoExecutor) {
            this.appCreateCommand = new AppCreateCommand(appCommand, odoExecutor);
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
            appCreateCommand.globalParametersSupport = buildGlobalParameters();
            return appCreateCommand;
        }
    }
}
