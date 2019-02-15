package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;

import java.util.ArrayList;
import java.util.List;

public class UpdateAppCommand implements Command {
    private final static String COMMAND_FLAG = "--app";

    private String component;
    private String appName;
    private List<String> extraCommands;

    private UpdateAppCommand(){
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        if (component != null) {
            arguments.add(component);
        }

        arguments.add(COMMAND_FLAG);

        if (appName != null) {
            arguments.add(appName);
        }

        if (extraCommands != null) {
            arguments.addAll(extraCommands);
        }

        return arguments;
    }

    public static class Builder {
        private UpdateAppCommand updateAppCommand;

        public Builder() {
            this.updateAppCommand = new UpdateAppCommand();
        }

        public UpdateAppCommand.Builder forComponent(String componentName) {
            this.updateAppCommand.component = componentName;
            return this;
        }

        public UpdateAppCommand.Builder app(String appName) {
            this.updateAppCommand.appName = appName;
            return this;
        }

        public UpdateAppCommand.Builder withExtraArguments(List<String> extraArguments) {
            this.updateAppCommand.extraCommands = extraArguments;
            return this;
        }

        public UpdateAppCommand build() {
            return updateAppCommand;
        }
    }
}