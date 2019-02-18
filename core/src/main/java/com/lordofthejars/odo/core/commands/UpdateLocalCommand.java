package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;

import java.util.ArrayList;
import java.util.List;

public class UpdateLocalCommand implements Command {
    private final static String COMMAND_FLAG = "--local";

    private String component;
    private String localDir;

    private GlobalParametersSupport globalParametersSupport;

    private UpdateLocalCommand(){
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        if (component != null) {
            arguments.add(component);
        }

        arguments.add(COMMAND_FLAG);

        if (localDir != null) {
            arguments.add(localDir);
        }


        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<UpdateLocalCommand.Builder> {
        private UpdateLocalCommand updateLocalCommand;

        public Builder() {
            this.updateLocalCommand = new UpdateLocalCommand();
        }

        public UpdateLocalCommand.Builder forComponent(String componentName) {
            this.updateLocalCommand.component = componentName;
            return this;
        }

        public UpdateLocalCommand.Builder directory(String dirName) {
            this.updateLocalCommand.localDir = dirName;
            return this;
        }

        public UpdateLocalCommand build() {
            updateLocalCommand.globalParametersSupport = buildGlobalParameters();
            return updateLocalCommand;
        }
    }
}
