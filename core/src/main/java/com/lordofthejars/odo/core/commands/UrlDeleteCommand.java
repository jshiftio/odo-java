package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class UrlDeleteCommand implements Command {

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

    private GlobalParametersSupport globalParametersSupport;

    private List<String> extraCommands;

    private UrlDeleteCommand(String urlName){
        this.urlName = urlName;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
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

        if (extraCommands != null) {
            arguments.addAll(extraCommands);
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<UrlDeleteCommand.Builder> {
        private UrlDeleteCommand urlDeleteCommand;

        public Builder(String urlName) {
            this.urlDeleteCommand = new UrlDeleteCommand(urlName);
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

        public UrlDeleteCommand.Builder withExtraArguments(List<String> extraArguments) {
            this.urlDeleteCommand.extraCommands = extraArguments;
            return this;
        }

        public UrlDeleteCommand build() {
            urlDeleteCommand.globalParametersSupport =  buildGlobalParameters();
            return urlDeleteCommand;
        }

    }
}
