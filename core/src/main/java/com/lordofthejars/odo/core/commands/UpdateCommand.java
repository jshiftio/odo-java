package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import java.util.ArrayList;
import java.util.List;

public class UpdateCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "update";
    private static final String APP = "--app";
    private static final String PROJECT = "--project";
    private static final String BINARY = "--binary";
    private static final String GIT = "--git";
    private static final String LOCAL = "--local";
    private static final String REF = "--ref";

    private String componentName;
    private String project;
    private String app;

    private String local;
    private String git;
    private String binary;
    private String ref;

    private GlobalParametersSupport globalParametersSupport;

    private UpdateCommand(OdoExecutor odoExecutor) {
        super(odoExecutor);
    }

    @Override
    public List<String> getCliCommand() {

        final List<String> argumentList = new ArrayList<>();
        argumentList.add(COMMAND_NAME);

        if (componentName != null) {
            argumentList.add(componentName);
        }

        if (project != null) {
            argumentList.add(PROJECT);
            argumentList.add(project);
        }

        if (app != null) {
            argumentList.add(APP);
            argumentList.add(app);
        }

        if (binary != null) {
            argumentList.add(BINARY);
            argumentList.add(binary);
        }

        if (local != null) {
            argumentList.add(LOCAL);
            argumentList.add(local);
        }

        if (git != null) {
            argumentList.add(GIT);
            argumentList.add(git);
        }

        if (ref != null) {
            argumentList.add(REF);
            argumentList.add(ref);
        }

        argumentList.addAll(globalParametersSupport.getCliCommand());
        return argumentList;
    }

    public static class Builder extends GlobalParametersSupport.Builder<UpdateCommand.Builder> {

        private UpdateCommand updateCommand;

        public Builder(OdoExecutor odoExecutor) {
            updateCommand = new UpdateCommand(odoExecutor);
        }

        public Builder withComponentName(String componentName) {
            this.updateCommand.componentName = componentName;
            return this;
        }

        public Builder withApp(String app) {
            this.updateCommand.app = app;
            return this;
        }

        public Builder withProject(String project) {
            this.updateCommand.project = project;
            return this;
        }

        public Builder withLocal(String local) {
            this.updateCommand.local = local;
            return this;
        }

        public Builder withGit(String git) {
            this.updateCommand.git = git;
            return this;
        }

        public Builder withBinary(String binary) {
            this.updateCommand.binary = binary;
            return this;
        }

        public Builder withRef(String ref) {
            this.updateCommand.ref = ref;
            return this;
        }

        public UpdateCommand build() {
            updateCommand.globalParametersSupport = buildGlobalParameters();
            return this.updateCommand;
        }
    }
}