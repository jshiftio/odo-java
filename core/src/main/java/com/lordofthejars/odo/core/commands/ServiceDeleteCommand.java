package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import java.util.ArrayList;
import java.util.List;

public class ServiceDeleteCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "delete";

    private String serviceNme;

    private static final String APP = "--app";
    private static final String PROJECT = "--project";
    private static final String FORCE = "--force";

    private String project;
    private String app;
    private Boolean force = Boolean.TRUE;

    private ServiceCommand serviceCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ServiceDeleteCommand(ServiceCommand serviceCommand, String serviceName, OdoExecutor odoExecutor) {
        super(odoExecutor);
        this.serviceNme = serviceName;
        this.serviceCommand = serviceCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(serviceCommand.getCliCommand());

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

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<ServiceDeleteCommand.Builder> {
        private ServiceDeleteCommand serviceDeleteCommand;

        public Builder(ServiceCommand serviceCommand, String serviceName, OdoExecutor odoExecutor) {
            this.serviceDeleteCommand = new ServiceDeleteCommand(serviceCommand, serviceName, odoExecutor);
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

        public ServiceDeleteCommand build() {
            serviceDeleteCommand.globalParametersSupport = buildGlobalParameters();
            return serviceDeleteCommand;
        }
    }

}
