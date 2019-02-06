package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class AppCommand implements Command {

    private static final String COMMAND_NAME = "app";

    private AppCreateCommand appCreateCommand;
    private AppDeleteCommand appDeleteCommand;
    private AppSetCommand appSetCommand;

    private AppCommand(AppCreateCommand appCreateCommand) {
        this.appCreateCommand = appCreateCommand;
    }

    private AppCommand(AppDeleteCommand appDeleteCommand) {
        this.appDeleteCommand = appDeleteCommand;
    }

    private AppCommand(AppSetCommand appSetCommand) {
        this.appSetCommand = appSetCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        if (appCreateCommand != null) {
            arguments.addAll(appCreateCommand.getCliCommand());
        } else if (appDeleteCommand != null) {
            arguments.addAll(appDeleteCommand.getCliCommand());
        } else if (appSetCommand != null) {
            arguments.addAll(appSetCommand.getCliCommand());
        } else {
            throw new IllegalArgumentException("App command requires a subcommand.");
        }

        return arguments;
    }

    public static class Builder {

        private AppCommand appCommand;

        public Builder(AppCreateCommand appCreateCommand) {
            appCommand = new AppCommand(appCreateCommand);
        }

        public Builder(AppDeleteCommand appDeleteCommand) {
            appCommand = new AppCommand(appDeleteCommand);
        }

        public Builder(AppSetCommand appSetCommand) {
            appCommand = new AppCommand(appSetCommand);
        }

        public AppCommand build() {
            return this.appCommand;
        }

    }

}
