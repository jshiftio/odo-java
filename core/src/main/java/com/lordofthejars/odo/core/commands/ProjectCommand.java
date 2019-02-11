package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;

import java.util.ArrayList;
import java.util.List;

public class ProjectCommand implements Command {

    private static final String COMMAND_NAME = "project";

    private final List<String> arguments = new ArrayList<>();

    private ProjectCreateCommand projectCreateCommand;
    private ProjectDeleteCommand projectDeleteCommand;
    private ProjectSetCommand projectSetCommand;

    private ProjectCommand(ProjectCreateCommand projectCreateCommand) {
        this.projectCreateCommand = projectCreateCommand;
    }

    private ProjectCommand(ProjectDeleteCommand projectDeleteCommand) {
        this.projectDeleteCommand = projectDeleteCommand;
    }

    private ProjectCommand(ProjectSetCommand projectSetCommand) {
        this.projectSetCommand = projectSetCommand;
    }

    private ProjectCommand() {
        this.arguments.add(COMMAND_NAME);
    }

    @Override
    public List<String> getCliCommand() {
        if (this.arguments.size() >= 3) {
            return this.arguments;
        }

        final List<String> argumentList = new ArrayList<>();
        argumentList.add(COMMAND_NAME);
        if (projectCreateCommand != null) {
            argumentList.addAll(projectCreateCommand.getCliCommand());
        } else if (projectDeleteCommand != null) {
            argumentList.addAll(projectDeleteCommand.getCliCommand());
        } else if (projectSetCommand != null) {
            argumentList.addAll(projectSetCommand.getCliCommand());
        } else{
            throw new IllegalArgumentException("Project command requires a subcommand.");
        }
        return argumentList;
    }


    public static class Builder {

        private ProjectCommand projectCommand;

        public Builder(ProjectCreateCommand projectCreateCommand) {
            projectCommand = new ProjectCommand(projectCreateCommand);
        }

        public Builder(ProjectDeleteCommand projectDeleteCommand) {
            projectCommand = new ProjectCommand(projectDeleteCommand);
        }

        public Builder(ProjectSetCommand projectSetCommand) {
            projectCommand = new ProjectCommand(projectSetCommand);
        }

        public Builder() {
            projectCommand = new ProjectCommand();
        }

        public ProjectCommand.Builder create() {
            projectCommand.arguments.add("create");
            return this;
        }

        public ProjectCommand.Builder delete() {
            projectCommand.arguments.add("delete");
            return this;
        }

        public ProjectCommand.Builder set() {
            projectCommand.arguments.add("set");
            return this;
        }

        public ProjectCommand.Builder name(String projectName) {
            projectCommand.arguments.add(projectName);
            return this;
        }

        public ProjectCommand.Builder withForce() {
            projectCommand.arguments.add("--force");
            return this;
        }

        public ProjectCommand build() { return this.projectCommand; }

    }
}