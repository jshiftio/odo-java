package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceCreateCommand implements Command {

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

    private List<String> extraCommands;

    private ServiceCreateCommand(String serviceType, String plan) {
        this.serviceType= serviceType;
        this.planName = plan;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
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

        if (extraCommands != null) {
            arguments.addAll(extraCommands);
        }

        return arguments;
    }

    public static class Builder {
        private ServiceCreateCommand serviceCreateCommand;

        public Builder(String serviceType, String plan) {
            this.serviceCreateCommand = new ServiceCreateCommand(serviceType, plan);
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

        public ServiceCreateCommand.Builder withExtraArguments(List<String> extraArguments) {
            this.serviceCreateCommand.extraCommands = extraArguments;
            return this;
        }

        public ServiceCreateCommand build() {
            return serviceCreateCommand;
        }
    }

}
