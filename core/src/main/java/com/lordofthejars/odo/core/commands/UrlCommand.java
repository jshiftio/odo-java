package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class UrlCommand implements Command {

    private static final String COMMAND_NAME = "url";

    private UrlCreateCommand urlCreateCommand;
    private UrlDeleteCommand urlDeleteCommand;

    private UrlCommand(UrlCreateCommand urlCreateCommand) {
        this.urlCreateCommand = urlCreateCommand;
    }

    private UrlCommand(UrlDeleteCommand urlDeleteCommand) {
        this.urlDeleteCommand = urlDeleteCommand;
    }

    @Override
    public List<String> getCliCommand() {

        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        if (urlCreateCommand != null) {
            arguments.addAll(urlCreateCommand.getCliCommand());
        } else {
            if (urlDeleteCommand != null) {
                arguments.addAll(urlDeleteCommand.getCliCommand());
            } else {
                throw new IllegalArgumentException("Url command requires a subcommand.");
            }
        }

        return arguments;
    }

    public static class Builder {
        private UrlCommand urlCommand;

        public Builder(UrlCreateCommand urlCreateCommand) {
            urlCommand = new UrlCommand((urlCreateCommand));
        }

        public Builder(UrlDeleteCommand urlDeleteCommand) {
            urlCommand = new UrlCommand((urlDeleteCommand));
        }

        public UrlCommand build() {
            return urlCommand;
        }

    }
}
