package io.jshift.odo.core.commands;

import java.util.ArrayList;
import java.util.List;
import io.jshift.odo.core.CliExecutor;

public class AppSetCommand extends AbstractRunnableCommand<Void> {
    private static final String COMMAND_NAME = "set";

    private String appName;

    private static final String PROJECT = "--project";

    private String project;

    private AppCommand appCommand;
    private GlobalParametersSupport globalParametersSupport;

    private AppSetCommand(AppCommand appCommand, String appName, CliExecutor odoExecutor){
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

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<AppSetCommand.Builder> {
        private AppSetCommand appSetCommand;

        public Builder(AppCommand appCommand, String appName, CliExecutor odoExecutor) {
            this.appSetCommand = new AppSetCommand(appCommand, appName, odoExecutor);
        }

        public AppSetCommand.Builder withProject(String project) {
            this.appSetCommand.project = project;
            return this;
        }
        public AppSetCommand build() {
            appSetCommand.globalParametersSupport = buildGlobalParameters();
            return appSetCommand;
        }
    }
}
