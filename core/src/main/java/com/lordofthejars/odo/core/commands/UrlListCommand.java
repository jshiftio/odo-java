package com.lordofthejars.odo.core.commands;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.lordofthejars.odo.core.OdoExecutor;
import com.lordofthejars.odo.core.commands.output.UrlList;
import java.util.ArrayList;
import java.util.List;

public class UrlListCommand extends AbstractRunnableCommand<UrlList> {

    private static final String COMMAND_NAME = "list";

    private static final String APP = "--app";
    private static final String PROJECT = "--project";
    private static final String OUTPUT = "--output";
    private static final String COMPONENT = "--component";
    private static final String DEFAULT_FORMAT = "json";

    private String app;
    private String project;
    private String component;

    private UrlCommand urlCommand;
    private GlobalParametersSupport globalParametersSupport;

    private UrlListCommand(UrlCommand urlCommand, OdoExecutor odoExecutor) {
        super(odoExecutor, UrlListCommand::parse);
        this.urlCommand = urlCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(urlCommand.getCliCommand());

        arguments.add(COMMAND_NAME);

        if (component != null) {
            arguments.add(COMPONENT);
            arguments.add(component);
        }

        if (app != null) {
            arguments.add(APP);
            arguments.add(app);
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

    protected static UrlList parse(List<String> consoleOutput) {
        final String output = String.join(" ", consoleOutput.toArray(new String[consoleOutput.size()]));
        final JsonValue outputJson = Json.parse(output);

        final JsonObject urlJson = outputJson.asObject();
        return UrlList.from(urlJson);
    }

    public static class Builder extends GlobalParametersSupport.Builder<UrlListCommand.Builder> {
        private UrlListCommand urlListCommand;

        public Builder(UrlCommand urlCommand, OdoExecutor odoExecutor) {
            this.urlListCommand = new UrlListCommand(urlCommand, odoExecutor);
        }

        public UrlListCommand.Builder withApp(String app) {
            this.urlListCommand.app = app;
            return this;
        }

        public UrlListCommand.Builder withProject(String project) {
            this.urlListCommand.project = project;
            return this;
        }

        public UrlListCommand.Builder withComponent(String component) {
            this.urlListCommand.component = component;
            return this;
        }

        public UrlListCommand build() {
            urlListCommand.globalParametersSupport = buildGlobalParameters();
            return urlListCommand;
        }

    }
}
