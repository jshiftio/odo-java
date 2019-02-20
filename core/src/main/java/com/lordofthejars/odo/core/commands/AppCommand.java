package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class AppCommand implements Command {

    private static final String COMMAND_NAME = "app";

    private AppCommand() {
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        return arguments;
    }

    public static class Builder {

        private AppCommand appCommand;

        public Builder() {
            appCommand = new AppCommand();
        }
        public AppCommand build() {
            return this.appCommand;
        }

    }

}
