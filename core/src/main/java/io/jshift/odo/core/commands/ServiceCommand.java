package io.jshift.odo.core.commands;

import io.jshift.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommand implements Command {

    private static final String COMMAND_NAME = "service";

    private ServiceCommand() {
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        return arguments;
    }

    public static class Builder {

        private ServiceCommand serviceCommand;

        public Builder() {
            this.serviceCommand = new ServiceCommand();
        }

        public ServiceCommand build() {
            return this.serviceCommand;
        }

    }
}
