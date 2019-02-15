package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;

import java.util.ArrayList;
import java.util.List;

public class UpdateGitCommand implements Command {
    private final static String COMMAND_FLAG = "--git";

    private String component;
    private String git;
    private List<String> extraCommands;

    private UpdateGitCommand(){
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        if (component != null) {
            arguments.add(component);
        }

        arguments.add(COMMAND_FLAG);
        arguments.add(git);

        if (extraCommands != null) {
            arguments.addAll(extraCommands);
        }

        return arguments;
    }

    public static class Builder {
        private UpdateGitCommand updateGitCommand;

        public Builder() {
            this.updateGitCommand = new UpdateGitCommand();
        }

        public UpdateGitCommand.Builder forComponent(String componentName) {
            this.updateGitCommand.component = componentName;
            return this;
        }

        public UpdateGitCommand.Builder git(String git) {
            this.updateGitCommand.git = git;
            return this;
        }

        public UpdateGitCommand.Builder withExtraArguments(List<String> extraArguments) {
            this.updateGitCommand.extraCommands = extraArguments;
            return this;
        }

        public UpdateGitCommand build() {
            return updateGitCommand;
        }
    }
}
