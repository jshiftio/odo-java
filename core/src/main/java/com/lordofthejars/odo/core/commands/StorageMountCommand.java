package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class StorageMountCommand implements Command {

    private static final String COMMAND_NAME = "mount";

    private static final String APP = "--app";
    private static final String COMPONENT = "--component";
    private static final String PROJECT = "--project";
    private static final String PATH = "--path";

    private String storageName;

    private String app;
    private String component;
    private String project;
    private String path;

    private GlobalParametersSupport globalParametersSupport;

    private StorageMountCommand(String storageName) {
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

        if (path != null) {
            arguments.add(PATH);
            arguments.add(path);
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<StorageMountCommand.Builder> {
        private StorageMountCommand storageMountCommand;

        public Builder(String storageName) {
            this.storageMountCommand = new StorageMountCommand(storageName);
        }

        public StorageMountCommand.Builder withApp(String app) {
            this.storageMountCommand.app = app;
            return this;
        }

        public StorageMountCommand.Builder withProject(String project) {
            this.storageMountCommand.project = project;
            return this;
        }

        public StorageMountCommand.Builder withComponent(String component) {
            this.storageMountCommand.component = component;
            return this;
        }

        public StorageMountCommand.Builder withPath(String path) {
            this.storageMountCommand.path = path;
            return this;
        }

        public StorageMountCommand build() {
            storageMountCommand.globalParametersSupport = buildGlobalParameters();
            return storageMountCommand;
        }

    }
}
