package com.lordofthejars.odo.core.commands;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.lordofthejars.odo.core.OdoExecutor;
import com.lordofthejars.odo.core.commands.output.StorageList;
import java.util.ArrayList;
import java.util.List;

public class StorageListCommand extends AbstractRunnableCommand<StorageList> {

    private static final String COMMAND_NAME = "list";

    private static final String APP = "--app";
    private static final String PROJECT = "--project";
    private static final String OUTPUT = "--output";
    private static final String COMPONENT = "--component";
    private static final String DEFAULT_FORMAT = "json";
    private static final String ALL = "--all";

    private String app;
    private String project;
    private String component;
    private Boolean all;

    private StorageCommand storageCommand;
    private GlobalParametersSupport globalParametersSupport;

    private StorageListCommand(StorageCommand storageCommand, OdoExecutor odoExecutor) {
        super(odoExecutor, StorageListCommand::parse);
        this.storageCommand = storageCommand;
    }

    protected static StorageList parse(List<String> consoleOutput) {
        final String output = String.join(" ", consoleOutput.toArray(new String[consoleOutput.size()]));
        final JsonValue outputJson = Json.parse(output);

        final JsonObject storageJson = outputJson.asObject();

        return StorageList.from(storageJson);
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(storageCommand.getCliCommand());

        arguments.add(COMMAND_NAME);

        if (all != null && all.booleanValue()) {
            arguments.add(ALL);
        }

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

    public static class Builder extends GlobalParametersSupport.Builder<StorageListCommand.Builder> {
        private StorageListCommand storageListCommand;

        public Builder(StorageCommand storageCommand, OdoExecutor odoExecutor) {
            this.storageListCommand = new StorageListCommand(storageCommand, odoExecutor);
        }

        public StorageListCommand.Builder withApp(String app) {
            this.storageListCommand.app = app;
            return this;
        }

        public StorageListCommand.Builder withProject(String project) {
            this.storageListCommand.project = project;
            return this;
        }

        public StorageListCommand.Builder withComponent(String component) {
            this.storageListCommand.component = component;
            return this;
        }

        public StorageListCommand.Builder withAll() {
            this.storageListCommand.all = Boolean.TRUE;
            return this;
        }

        public StorageListCommand build() {
            storageListCommand.globalParametersSupport = buildGlobalParameters();
            return storageListCommand;
        }

    }

}
