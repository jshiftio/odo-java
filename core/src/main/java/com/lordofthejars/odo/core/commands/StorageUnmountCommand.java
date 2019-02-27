package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.CliExecutor;
import java.util.ArrayList;
import java.util.List;

public class StorageUnmountCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "unmount";

    private String storageName;

    private static final String APP = "--app";
    private static final String COMPONENT = "--component";
    private static final String PROJECT = "--project";

    private String app;
    private String component;
    private String project;

    private StorageCommand storageCommand;
    private GlobalParametersSupport globalParametersSupport;

    private StorageUnmountCommand(StorageCommand storageCommand, String storageName, CliExecutor odoExecutor) {
        super(odoExecutor);
        this.storageCommand = storageCommand;
        this.storageName = storageName;
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

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<StorageUnmountCommand.Builder> {
        private StorageUnmountCommand storageUnmountCommand;

        public Builder(StorageCommand storageCommand, String storageName, CliExecutor odoExecutor) {
            storageUnmountCommand = new StorageUnmountCommand(storageCommand, storageName, odoExecutor);
        }

        public StorageUnmountCommand.Builder withPath(String path) {
            this.storageUnmountCommand.storageName = path;
            return this;
        }

        public StorageUnmountCommand.Builder withApp(String app) {
            this.storageUnmountCommand.app = app;
            return this;
        }

        public StorageUnmountCommand.Builder withProject(String project) {
            this.storageUnmountCommand.project = project;
            return this;
        }

        public StorageUnmountCommand.Builder withComponent(String component) {
            this.storageUnmountCommand.component = component;
            return this;
        }


        public StorageUnmountCommand build() {
            storageUnmountCommand.globalParametersSupport = buildGlobalParameters();
            return storageUnmountCommand;
        }

    }
}
