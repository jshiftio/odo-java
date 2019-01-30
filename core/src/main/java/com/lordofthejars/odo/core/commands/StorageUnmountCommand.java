package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class StorageUnmountCommand implements Command {

    private static final String COMMAND_NAME = "unmount";

    private String storageName;

    private static final String APP = "--app";
    private static final String COMPONENT = "--component";
    private static final String PROJECT = "--project";

    private String app;
    private String component;
    private String project;

    private List<String> extraCommands;

    private StorageUnmountCommand() {
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        if (storageName != null) {
            arguments.add(storageName);
        }

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

        if (extraCommands != null) {
            arguments.addAll(extraCommands);
        }

        return arguments;
    }

    public static class Builder {
        private StorageUnmountCommand storageUnmountCommand;

        public Builder() {
            storageUnmountCommand = new StorageUnmountCommand();
        }

        public StorageUnmountCommand.Builder withPath(String path) {
            this.storageUnmountCommand.storageName = path;
            return this;
        }

        public StorageUnmountCommand.Builder withStorageName(String storageName) {
            this.storageUnmountCommand.storageName = storageName;
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

        public StorageUnmountCommand.Builder withExtraArguments(List<String> extraArguments) {
            this.storageUnmountCommand.extraCommands = extraArguments;
            return this;
        }

        public StorageUnmountCommand build() {
            return storageUnmountCommand;
        }

    }
}
