package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import java.util.ArrayList;
import java.util.List;

public class ComponentUpdateCommand extends AbstractRunnableCommand<Void> {

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

    private ComponentCommand componentCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ComponentUpdateCommand(ComponentCommand componentCommand, OdoExecutor odoExecutor) {
        super(odoExecutor);
        this.componentCommand = componentCommand;
    }

    @Override
    public List<String> getCliCommand() {

        final List<String> argumentList = new ArrayList<>();

        argumentList.addAll(componentCommand.getCliCommand());

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

    public static class Builder extends GlobalParametersSupport.Builder<ComponentUpdateCommand.Builder> {

        private ComponentUpdateCommand componentUpdateCommand;

        public Builder(ComponentCommand componentCommand, OdoExecutor odoExecutor) {
            componentUpdateCommand = new ComponentUpdateCommand(componentCommand, odoExecutor);
        }

        public Builder withComponentName(String componentName) {
            this.componentUpdateCommand.componentName = componentName;
            return this;
        }

        public Builder withApp(String app) {
            this.componentUpdateCommand.app = app;
            return this;
        }

        public Builder withProject(String project) {
            this.componentUpdateCommand.project = project;
            return this;
        }

        public Builder withLocal(String local) {
            this.componentUpdateCommand.local = local;
            return this;
        }

        public Builder withGit(String git) {
            this.componentUpdateCommand.git = git;
            return this;
        }

        public Builder withBinary(String binary) {
            this.componentUpdateCommand.binary = binary;
            return this;
        }

        public Builder withRef(String ref) {
            this.componentUpdateCommand.ref = ref;
            return this;
        }

        public ComponentUpdateCommand build() {
            componentUpdateCommand.globalParametersSupport = buildGlobalParameters();
            return this.componentUpdateCommand;
        }
    }
}