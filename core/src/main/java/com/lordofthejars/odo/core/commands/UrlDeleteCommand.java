package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import com.lordofthejars.odo.core.OdoExecutor;
import java.util.ArrayList;
import java.util.List;

public class UrlDeleteCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "delete";

    private String urlName;

    private static final String APP = "--app";
    private static final String COMPONENT = "--component";
    private static final String PROJECT = "--project";
    private static final String FORCE = "--force";

    private String app;
    private String component;
    private String project;
    private Boolean force = Boolean.TRUE;

    private UrlCommand urlCommand;
    private GlobalParametersSupport globalParametersSupport;

    private UrlDeleteCommand(UrlCommand urlCommand, String urlName, OdoExecutor odoExecutor) {
        super(odoExecutor);
        this.urlName = urlName;
        this.urlCommand = urlCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(this.urlCommand.getCliCommand());

        arguments.add(COMMAND_NAME);

        arguments.add(urlName);

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

    public static class Builder extends GlobalParametersSupport.Builder<UrlDeleteCommand.Builder> {
        private UrlDeleteCommand urlDeleteCommand;

        public Builder(UrlCommand urlCommand, String urlName, OdoExecutor odoExecutor) {
            this.urlDeleteCommand = new UrlDeleteCommand(urlCommand, urlName, odoExecutor);
        }

        public UrlDeleteCommand.Builder withApp(String app) {
            this.urlDeleteCommand.app = app;
            return this;
        }

        public UrlDeleteCommand.Builder withProject(String project) {
            this.urlDeleteCommand.project = project;
            return this;
        }

        public UrlDeleteCommand.Builder withComponent(String component) {
            this.urlDeleteCommand.component = component;
            return this;
        }

        public UrlDeleteCommand.Builder withForce(boolean force) {
            this.urlDeleteCommand.force = force;
            return this;
        }

        public UrlDeleteCommand build() {
            urlDeleteCommand.globalParametersSupport =  buildGlobalParameters();
            return urlDeleteCommand;
        }

    }
}
