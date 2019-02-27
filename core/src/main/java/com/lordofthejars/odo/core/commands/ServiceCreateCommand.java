package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.CliExecutor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceCreateCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "create";

    private String serviceType;
    private String planName;

    private static final String PLAN = "--plan";
    private static final String PROJECT = "--project";
    private static final String APP = "--app";
    private static final String PARAMETERS = "-p";


    private String serviceName;
    private String app;
    private String project;
    private List<String> parameters;

    private ServiceCommand serviceCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ServiceCreateCommand(ServiceCommand serviceCommand, String serviceType, String plan, CliExecutor odoExecutor) {
        super(odoExecutor);
        this.serviceType= serviceType;
        this.planName = plan;
        this.serviceCommand = serviceCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(serviceCommand.getCliCommand());

        arguments.add(COMMAND_NAME);
        arguments.add(serviceType);

        if (serviceName != null) {
            arguments.add(serviceName);
        }

        arguments.add(PLAN);
        arguments.add(planName);

        if (app != null) {
            arguments.add(APP);
            arguments.add(app);
        }

        if (project != null) {
            arguments.add(PROJECT);
            arguments.add(project);
        }

        if (parameters != null && !parameters.isEmpty()) {

            for (String param : parameters) {
                arguments.add(PARAMETERS);
                arguments.add(param);
            }
        }

        if (this.globalParametersSupport != null) {
            arguments.addAll(this.globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<ServiceCreateCommand.Builder> {
        private ServiceCreateCommand serviceCreateCommand;

        public Builder(ServiceCommand serviceCommand, String serviceType, String plan, CliExecutor odoExecutor) {
            this.serviceCreateCommand = new ServiceCreateCommand(serviceCommand, serviceType, plan, odoExecutor);
        }

        public ServiceCreateCommand.Builder withServiceName(String serviceName) {
            this.serviceCreateCommand.serviceName = serviceName;
            return this;
        }

        public ServiceCreateCommand.Builder withApp(String app) {
            this.serviceCreateCommand.app = app;
            return this;
        }

        public ServiceCreateCommand.Builder withProject(String project) {
            this.serviceCreateCommand.project = project;
            return this;
        }

        public ServiceCreateCommand.Builder withParameters(List<String> parameters) {
            this.serviceCreateCommand.parameters = parameters;
            return this;
        }

        public ServiceCreateCommand.Builder withParameters(String... parameters) {
            this.serviceCreateCommand.parameters = Arrays.asList(parameters);
            return this;
        }

        public ServiceCreateCommand build() {
            this.serviceCreateCommand.globalParametersSupport = buildGlobalParameters();
            return serviceCreateCommand;
        }
    }

}
