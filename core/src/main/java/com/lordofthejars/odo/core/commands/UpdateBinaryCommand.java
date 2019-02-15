package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;

import java.util.ArrayList;
import java.util.List;

public class UpdateBinaryCommand implements Command {
    private final static String COMMAND_FLAG = "--binary";

    private String component;
    private String binary;
    private List<String> extraCommands;

    private UpdateBinaryCommand(){
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        if (component != null) {
            arguments.add(component);
        }

        arguments.add(COMMAND_FLAG);
        arguments.add(binary);

        if (extraCommands != null) {
            arguments.addAll(extraCommands);
        }

        return arguments;
    }

    public static class Builder {
        private UpdateBinaryCommand updateBinaryCommand;

        public Builder() {
            this.updateBinaryCommand = new UpdateBinaryCommand();
        }

        public UpdateBinaryCommand.Builder forComponent(String componentName) {
            this.updateBinaryCommand.component = componentName;
            return this;
        }

        public UpdateBinaryCommand.Builder binary(String binary) {
            this.updateBinaryCommand.binary = binary;
            return this;
        }

        public UpdateBinaryCommand.Builder withExtraArguments(List<String> extraArguments) {
            this.updateBinaryCommand.extraCommands = extraArguments;
            return this;
        }

        public UpdateBinaryCommand build() {
            return updateBinaryCommand;
        }
    }
}