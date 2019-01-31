package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommand implements Command {

    private static final String COMMAND_NAME = "service";

    private ServiceCreateCommand serviceCreateCommand;
    private ServiceDeleteCommand serviceDeleteCommand;

    private ServiceCommand(ServiceCreateCommand serviceCreateCommand) {
        this.serviceCreateCommand = serviceCreateCommand;
    }

    private ServiceCommand(ServiceDeleteCommand serviceDeleteCommand) {
        this.serviceDeleteCommand = serviceDeleteCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        if (serviceCreateCommand != null) {
            arguments.addAll(serviceCreateCommand.getCliCommand());
        } else {
            if (serviceDeleteCommand != null) {
                arguments.addAll(serviceDeleteCommand.getCliCommand());
            } else {
                throw new IllegalArgumentException("App command requires a subcommand.");
            }
        }

        return arguments;
    }

    public static class Builder {

        private ServiceCommand serviceCommand;

        public Builder(ServiceDeleteCommand serviceDeleteCommand) {
            serviceCommand = new ServiceCommand(serviceDeleteCommand);
        }

        public Builder(ServiceCreateCommand serviceCreateCommand) {
            serviceCommand = new ServiceCommand(serviceCreateCommand);
        }

        public ServiceCommand build() {
            return this.serviceCommand;
        }

    }
}
