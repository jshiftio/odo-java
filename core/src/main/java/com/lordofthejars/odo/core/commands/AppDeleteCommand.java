package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import java.util.ArrayList;
import java.util.List;

public class AppDeleteCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "delete";

    private String appName;

    private static final String PROJECT = "--project";
    private static final String FORCE = "--force";

    private String project;
    private Boolean force = Boolean.TRUE;

    private AppCommand appCommand;
    private GlobalParametersSupport globalParametersSupport;

    private AppDeleteCommand(AppCommand appCommand, String appName, OdoExecutor odoExecutor){
        super(odoExecutor);
        this.appName = appName;
        this.appCommand = appCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(appCommand.getCliCommand());

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

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<AppDeleteCommand.Builder> {
        private AppDeleteCommand appDeleteCommand;

        public Builder(AppCommand appCommand, String appName, OdoExecutor odoExecutor) {
            this.appDeleteCommand = new AppDeleteCommand(appCommand, appName, odoExecutor);
        }

        public AppDeleteCommand.Builder withProject(String project) {
            this.appDeleteCommand.project = project;
            return this;
        }

        public AppDeleteCommand.Builder withForce(boolean force) {
            this.appDeleteCommand.force = force;
            return this;
        }

        public AppDeleteCommand build() {
            appDeleteCommand.globalParametersSupport = buildGlobalParameters();
            return appDeleteCommand;
        }
    }

}
