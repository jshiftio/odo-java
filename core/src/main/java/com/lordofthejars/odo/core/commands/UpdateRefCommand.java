package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;

import java.util.ArrayList;
import java.util.List;

public class UpdateRefCommand implements Command {
    private static final String COMMAND_FLAG = "--ref";

    private String component;
    private String refName;

    private GlobalParametersSupport globalParametersSupport;

    private UpdateRefCommand(){
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        if (component != null) {
            arguments.add(component);
        }

        arguments.add(COMMAND_FLAG);
        arguments.add(refName);

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<UpdateRefCommand.Builder> {
        private UpdateRefCommand updateRefCommand;

        public Builder() {
            this.updateRefCommand = new UpdateRefCommand();
        }

        public UpdateRefCommand.Builder forComponent(String component) {
            this.updateRefCommand.component = component;
            return this;
        }
        public UpdateRefCommand.Builder ref(String refName) {
            this.updateRefCommand.refName = refName;
            return this;
        }

        public UpdateRefCommand build() {
            updateRefCommand.globalParametersSupport = buildGlobalParameters();
            return updateRefCommand;
        }
    }
}