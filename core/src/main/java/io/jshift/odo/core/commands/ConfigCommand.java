package io.jshift.odo.core.commands;

import io.jshift.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class ConfigCommand implements Command {

    private static final String COMMAND_NAME = "config";

    private ConfigCommand() {
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        return arguments;
    }

    public static class Builder {
        private ConfigCommand configCommand;

        public Builder() {
            configCommand = new ConfigCommand();
        }
        public ConfigCommand build() { return this.configCommand; }
    }
}
