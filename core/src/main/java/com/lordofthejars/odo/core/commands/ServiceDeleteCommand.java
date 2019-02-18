package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceDeleteCommand implements Command {

    private static final String COMMAND_NAME = "delete";

    private String serviceNme;

    private static final String APP = "--app";
    private static final String PROJECT = "--project";
    private static final String FORCE = "--force";

    private String project;
    private String app;
    private Boolean force = Boolean.TRUE;

    private GlobalParametersSupport globalParametersSupport;

    private List<String> extraCommands;

    private ServiceDeleteCommand(String serviceNme){
        this.serviceNme = serviceNme;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);
        arguments.add(serviceNme);

        if (project != null) {
            arguments.add(PROJECT);
            arguments.add(project);
        }

        if (app != null) {
            arguments.add(APP);
            arguments.add(app);
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

    public static class Builder extends GlobalParametersSupport.Builder<ServiceDeleteCommand.Builder> {
        private ServiceDeleteCommand serviceDeleteCommand;

        public Builder(String serviceName) {
            this.serviceDeleteCommand = new ServiceDeleteCommand(serviceName);
        }

        public ServiceDeleteCommand.Builder withApp(String app) {
            this.serviceDeleteCommand.app = app;
            return this;
        }

        public ServiceDeleteCommand.Builder withProject(String project) {
            this.serviceDeleteCommand.project = project;
            return this;
        }

        public ServiceDeleteCommand.Builder withForce(boolean force) {
            this.serviceDeleteCommand.force = force;
            return this;
        }

        public ServiceDeleteCommand.Builder withExtraArguments(List<String> extraArguments) {
            this.serviceDeleteCommand.extraCommands = extraArguments;
            return this;
        }

        public ServiceDeleteCommand build() {
            serviceDeleteCommand.globalParametersSupport = buildGlobalParameters();
            return serviceDeleteCommand;
        }
    }

}
