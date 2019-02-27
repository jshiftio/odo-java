package com.lordofthejars.odo.core.commands;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.lordofthejars.odo.core.CliExecutor;
import com.lordofthejars.odo.core.commands.output.App;
import com.lordofthejars.odo.core.commands.output.AppList;
import com.lordofthejars.odo.core.commands.output.TerminalOutput;
import java.util.ArrayList;
import java.util.List;

public class AppDescribeCommand extends AbstractRunnableCommand<TerminalOutput> {

    private static final String COMMAND_NAME = "describe";

    private String appName;

    private static final String PROJECT = "--project";
    private static final String OUTPUT = "--output";
    private static final String DEFAULT_FORMAT = "json";

    private String project;

    private AppCommand appCommand;
    private GlobalParametersSupport globalParametersSupport;

    private AppDescribeCommand(AppCommand appCommand, CliExecutor odoExecutor) {
        super(odoExecutor, AppDescribeCommand::parse);
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

        arguments.add(OUTPUT);
        arguments.add(DEFAULT_FORMAT);

        if (this.globalParametersSupport != null) {
            arguments.addAll(this.globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    protected static TerminalOutput parse(List<String> consoleOutput) {
        final String output = String.join(" ", consoleOutput.toArray(new String[consoleOutput.size()]));
        final JsonValue outputJson = Json.parse(output);

        final JsonObject componentJson = outputJson.asObject();

        final JsonValue items = componentJson.get("items");

        if (items != null) {
            return AppList.from(componentJson);
        } else {
            return App.from(componentJson);
        }
    }

    public static class Builder extends GlobalParametersSupport.Builder<AppDescribeCommand.Builder> {
        private AppDescribeCommand appDescribeCommand;

        public Builder(AppCommand appCommand, CliExecutor odoExecutor) {
            this.appDescribeCommand = new AppDescribeCommand(appCommand, odoExecutor);
        }

        public AppDescribeCommand.Builder withAppName(String appName) {
            this.appDescribeCommand.appName = appName;
            return this;
        }

        public AppDescribeCommand.Builder withProject(String project) {
            this.appDescribeCommand.project = project;
            return this;
        }

        public AppDescribeCommand build() {
            appDescribeCommand.globalParametersSupport = buildGlobalParameters();
            return appDescribeCommand;
        }
    }

}
