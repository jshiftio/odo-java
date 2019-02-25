package com.lordofthejars.odo.core.commands;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.lordofthejars.odo.core.OdoExecutor;
import com.lordofthejars.odo.core.commands.output.AppList;
import java.util.ArrayList;
import java.util.List;

public class AppListCommand extends AbstractRunnableCommand<AppList> {

    private static final String COMMAND_NAME = "list";

    private static final String PROJECT = "--project";
    private static final String OUTPUT = "--output";
    private static final String DEFAULT_FORMAT = "json";

    private String project;

    private AppCommand appCommand;
    private GlobalParametersSupport globalParametersSupport;

    private AppListCommand(AppCommand appCommand, OdoExecutor odoExecutor) {
        super(odoExecutor, AppListCommand::parse);
        this.appCommand = appCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(appCommand.getCliCommand());

        arguments.add(COMMAND_NAME);

        if (project != null) {
            arguments.add(PROJECT);
            arguments.add(project);
        }

        arguments.add(OUTPUT);
        arguments.add(DEFAULT_FORMAT);

        if (this.globalParametersSupport != null) {
            arguments.addAll(this.globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    protected static AppList parse(List<String> consoleOutput) {
        final String output = String.join(" ", consoleOutput.toArray(new String[consoleOutput.size()]));
        final JsonValue outputJson = Json.parse(output);

        final JsonObject componentJson = outputJson.asObject();
        return AppList.from(componentJson);
    }

    public static class Builder extends GlobalParametersSupport.Builder<AppListCommand.Builder> {
        private AppListCommand appListCommand;

        public Builder(AppCommand componentCommand, OdoExecutor odoExecutor) {
            this.appListCommand = new AppListCommand(componentCommand, odoExecutor);
        }

        public AppListCommand.Builder withProject(String project) {
            this.appListCommand.project = project;
            return this;
        }

        public AppListCommand build() {
            appListCommand.globalParametersSupport = buildGlobalParameters();
            return appListCommand;
        }

    }

}
