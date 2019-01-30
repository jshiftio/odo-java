package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class StorageCommand implements Command {

    private static final String COMMAND_NAME = "storage";

    private StorageCreateCommand storageCreateCommand;
    private StorageUnmountCommand storageUnmountCommand;
    private StorageDeleteCommand storageDeleteCommand;
    private StorageMountCommand storageMountCommand;

    private StorageCommand(StorageCreateCommand storageCreateCommand) {
        this.storageCreateCommand = storageCreateCommand;
    }

    private StorageCommand(StorageUnmountCommand storageUnmountCommand) {
        this.storageUnmountCommand = storageUnmountCommand;
    }

    private StorageCommand(StorageDeleteCommand storageDeleteCommand) {
        this.storageDeleteCommand = storageDeleteCommand;
    }

    private StorageCommand(StorageMountCommand storageMountCommand) {
        this.storageMountCommand = storageMountCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        if (storageCreateCommand != null) {
            arguments.addAll(storageCreateCommand.getCliCommand());
        } else {
            if (storageDeleteCommand != null) {
                arguments.addAll(storageDeleteCommand.getCliCommand());
            } else {
                if (storageMountCommand != null) {
                    arguments.addAll(storageMountCommand.getCliCommand());
                } else {
                    if (storageUnmountCommand != null) {
                        arguments.addAll(storageUnmountCommand.getCliCommand());
                    } else {
                        throw new IllegalArgumentException("Storage command requires a subcommand.");
                    }
                }
            }
        }

        return arguments;
    }

    public static class Builder {
        private StorageCommand storageCommand;

        public Builder(StorageMountCommand storageMountCommand) {
            storageCommand = new StorageCommand(storageMountCommand);
        }

        public Builder(StorageDeleteCommand storageDeleteCommand) {
            storageCommand = new StorageCommand(storageDeleteCommand);
        }

        public Builder(StorageUnmountCommand storageUnmountCommand) {
            storageCommand = new StorageCommand(storageUnmountCommand);
        }

        public Builder(StorageCreateCommand storageCreateCommand) {
            storageCommand = new StorageCommand(storageCreateCommand);
        }

        public StorageCommand build() {
            return storageCommand;
        }
    }
}
