package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.CliExecutor;
import java.util.ArrayList;
import java.util.List;

public class StorageDeleteCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "delete";

    private String storageName;

    private static final String APP = "--app";
    private static final String COMPONENT = "--component";
    private static final String PROJECT = "--project";
    private static final String FORCE = "--force";

    private String app;
    private String component;
    private String project;
    private Boolean force = Boolean.TRUE;


    private StorageCommand storageCommand;
    private GlobalParametersSupport globalParametersSupport;

    private StorageDeleteCommand(StorageCommand storageCommand, String storageName, CliExecutor odoExecutor) {
        super(odoExecutor);
        this.storageName = storageName;
        this.storageCommand = storageCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(storageCommand.getCliCommand());

        arguments.add(COMMAND_NAME);

        arguments.add(storageName);

        if (app != null) {
            arguments.add(APP);
            arguments.add(app);
        }

        if (component != null) {
            arguments.add(COMPONENT);
            arguments.add(component);
        }

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

    public static class Builder extends GlobalParametersSupport.Builder<StorageDeleteCommand.Builder> {
        private StorageDeleteCommand storageDeleteCommand;

        public Builder(StorageCommand storageCommand, String storageName, CliExecutor odoExecutor) {
            this.storageDeleteCommand = new StorageDeleteCommand(storageCommand, storageName, odoExecutor);
        }

        public StorageDeleteCommand.Builder withApp(String app) {
            this.storageDeleteCommand.app = app;
            return this;
        }

        public StorageDeleteCommand.Builder withProject(String project) {
            this.storageDeleteCommand.project = project;
            return this;
        }

        public StorageDeleteCommand.Builder withComponent(String component) {
            this.storageDeleteCommand.component = component;
            return this;
        }

        public StorageDeleteCommand.Builder withForce(boolean force) {
            this.storageDeleteCommand.force = force;
            return this;
        }

        public StorageDeleteCommand build() {
            storageDeleteCommand.globalParametersSupport = buildGlobalParameters();
            return storageDeleteCommand;
        }

    }
}
