package io.jshift.odo.core.commands;

import java.util.ArrayList;
import java.util.List;
import io.jshift.odo.core.CliExecutor;

public class ServiceDeleteCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "delete";

    private String serviceName;

    private static final String APP = "--app";
    private static final String PROJECT = "--project";
    private static final String FORCE = "--force";

    private String project;
    private String app;
    private Boolean force = Boolean.TRUE;

    private ServiceCommand serviceCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ServiceDeleteCommand(ServiceCommand serviceCommand, String serviceName, CliExecutor odoExecutor) {
        super(odoExecutor);
        this.serviceName = serviceName;
        this.serviceCommand = serviceCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(serviceCommand.getCliCommand());

        arguments.add(COMMAND_NAME);
        arguments.add(serviceName);

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

        public Builder(ServiceCommand serviceCommand, String serviceName, CliExecutor odoExecutor) {
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
