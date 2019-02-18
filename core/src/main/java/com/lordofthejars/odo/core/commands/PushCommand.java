package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class PushCommand implements Command {

    private static final String COMMAND_NAME = "push";

    private String componentName;

    private static final String APP = "--app";
    private static final String LOCAL = "--local";
    private static final String PROJECT = "--project";

    private String app;
    private String local;
    private String project;

    private GlobalParametersSupport globalParametersSupport;


    private PushCommand(){

    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        if (componentName != null) {
            arguments.add(componentName);
        }

        if (app != null) {
            arguments.add(APP);
            arguments.add(app);
        }

        if (local != null) {
            arguments.add(LOCAL);
            arguments.add(local);
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

    public static class Builder extends GlobalParametersSupport.Builder<PushCommand.Builder> {
        private PushCommand pushCommand;

        public Builder() {
            this.pushCommand = new PushCommand();
        }

        public PushCommand.Builder withComponentName(String componentName) {
            this.pushCommand.componentName = componentName;
            return this;
        }

        public PushCommand.Builder withApp(String app) {
            this.pushCommand.app = app;
            return this;
        }

        public PushCommand.Builder withLocal(String local) {
            this.pushCommand.local = local;
            return this;
        }

        public PushCommand.Builder withProject(String project) {
            this.pushCommand.project = project;
            return this;
        }

        public PushCommand build() {
            this.pushCommand.globalParametersSupport = buildGlobalParameters();
            return pushCommand;
        }
    }
}
