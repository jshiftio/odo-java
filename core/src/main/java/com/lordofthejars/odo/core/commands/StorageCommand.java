package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class StorageCommand implements Command {

    private static final String COMMAND_NAME = "storage";

    private StorageCommand() {
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        return arguments;
    }

    public static class Builder {
        private StorageCommand storageCommand;

        public Builder() {
            this.storageCommand = new StorageCommand();
        }

        public StorageCommand build() {
            return storageCommand;
        }
    }
}
