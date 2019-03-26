package io.jshift.odo.core.commands;

import io.jshift.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class ProjectCommand implements Command {

    private static final String COMMAND_NAME = "project";

    private ProjectCommand() {
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        return arguments;
    }

    public static class Builder {
        private ProjectCommand projectCommand;

        public Builder() {
            projectCommand = new ProjectCommand();
        }
        public ProjectCommand build() { return this.projectCommand; }
    }
}