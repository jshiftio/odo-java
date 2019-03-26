package io.jshift.odo.core.commands;

import java.util.ArrayList;
import java.util.List;
import io.jshift.odo.core.CliExecutor;

public class AppDeleteCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "delete";

    private String appName;

    private static final String PROJECT = "--project";
    private static final String FORCE = "--force";

    private String project;
    private Boolean force = Boolean.TRUE;

    private AppCommand appCommand;
    private GlobalParametersSupport globalParametersSupport;

    private AppDeleteCommand(AppCommand appCommand, CliExecutor odoExecutor){
        super(odoExecutor);
        this.appCommand = appCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(appCommand.getCliCommand());

        arguments.add(COMMAND_NAME);

        // required arg
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

        public Builder(AppCommand appCommand, CliExecutor odoExecutor) {
            this.appDeleteCommand = new AppDeleteCommand(appCommand, odoExecutor);
        }

        public AppDeleteCommand.Builder withProject(String project) {
            this.appDeleteCommand.project = project;
            return this;
        }

        public AppDeleteCommand.Builder withAppName(String appName) {
            this.appDeleteCommand.appName = appName;
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
