package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class ComponentCommand implements Command {

    private static final String COMMAND_NAME = "component";

    private ComponentCommand() {
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        return arguments;
    }

    public static class Builder {

        private ComponentCommand componentCommand;

        public Builder() {
            componentCommand = new ComponentCommand();
        }
        public ComponentCommand build() {
            return this.componentCommand;
        }

    }

}
