package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class StorageDeleteCommand implements Command {

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

    private List<String> extraCommands;

    private StorageDeleteCommand(String storageName) {
        this.storageName = storageName;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
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

        if (extraCommands != null) {
            arguments.addAll(extraCommands);
        }

        return arguments;
    }

    public static class Builder {
        private StorageDeleteCommand storageDeleteCommand;

        public Builder(String storageName) {
            this.storageDeleteCommand = new StorageDeleteCommand(storageName);
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

        public StorageDeleteCommand.Builder withExtraArguments(List<String> extraArguments) {
            this.storageDeleteCommand.extraCommands = extraArguments;
            return this;
        }

        public StorageDeleteCommand build() {
            return storageDeleteCommand;
        }

    }
}
