package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;

import java.util.ArrayList;
import java.util.List;

public class UpdateCommand implements Command {

    private static final String COMMAND_NAME = "update";

    private final List<String> arguments = new ArrayList<>();

    private UpdateAppCommand updateAppCommand;
    private UpdateRefCommand updateRefCommand;
    private UpdateProjectCommand updateProjectCommand;
    private UpdateBinaryCommand updateBinaryCommand;
    private UpdateGitCommand updateGitCommand;
    private UpdateLocalCommand updateLocalCommand;

    private UpdateCommand() {
        this.arguments.add(COMMAND_NAME);
    }

    private UpdateCommand(UpdateAppCommand updateAppCommand) {
        this.updateAppCommand = updateAppCommand;
    }
    private UpdateCommand(UpdateProjectCommand updateProjectCommand) {
        this.updateProjectCommand = updateProjectCommand;
    }
    private UpdateCommand(UpdateLocalCommand updateLocalCommand) {
        this.updateLocalCommand = updateLocalCommand;
    }
    private UpdateCommand(UpdateGitCommand updateGitCommand) {
        this.updateGitCommand = updateGitCommand;
    }
    private UpdateCommand(UpdateBinaryCommand updateBinaryCommand) {
        this.updateBinaryCommand = updateBinaryCommand;
    }
    private UpdateCommand(UpdateRefCommand updateRefCommand) {
        this.updateRefCommand = updateRefCommand;
    }

    @Override
    public List<String> getCliCommand() {
        if (this.arguments.size() >= 2) {
            return this.arguments;
        }

        final List<String> argumentList = new ArrayList<>();

        argumentList.add(COMMAND_NAME);

        if (updateAppCommand != null) {
            argumentList.addAll(updateAppCommand.getCliCommand());
        } else if (updateBinaryCommand != null) {
            argumentList.addAll(updateBinaryCommand.getCliCommand());
        } else if (updateGitCommand != null) {
            argumentList.addAll(updateGitCommand.getCliCommand());
        } else if (updateLocalCommand != null) {
            argumentList.addAll(updateLocalCommand.getCliCommand());
        } else if (updateProjectCommand != null) {
            argumentList.addAll(updateProjectCommand.getCliCommand());
        } else if (updateRefCommand != null) {
            argumentList.addAll(updateRefCommand.getCliCommand());
        } else {
            throw new IllegalArgumentException("Project command requires a subcommand.");
        }

        return argumentList;
    }

    public static class Builder {

        private UpdateCommand updateCommand;

        public Builder() {
            updateCommand = new UpdateCommand();
        }

        public Builder(UpdateAppCommand updateAppCommand) {
            updateCommand = new UpdateCommand(updateAppCommand);
        }
        public Builder(UpdateProjectCommand updateProjectCommand) {
            updateCommand = new UpdateCommand(updateProjectCommand);
        }
        public Builder(UpdateGitCommand updateGitCommand) {
            updateCommand = new UpdateCommand(updateGitCommand);
        }
        public Builder(UpdateLocalCommand updateLocalCommand) {
            updateCommand = new UpdateCommand(updateLocalCommand);
        }
        public Builder(UpdateRefCommand updateRefCommand) {
            updateCommand = new UpdateCommand(updateRefCommand);
        }
        public Builder(UpdateBinaryCommand updateBinaryCommand) {
            updateCommand = new UpdateCommand(updateBinaryCommand);
        }

        public UpdateCommand.Builder component(String componentName) {
            updateCommand.arguments.add(componentName);
            return this;
        }

        public UpdateCommand.Builder app(String appName) {
            updateCommand.arguments.add("--app");
            updateCommand.arguments.add(appName);
            return this;
        }

        public UpdateCommand.Builder binary(String binaryName) {
            updateCommand.arguments.add("--binary");
            updateCommand.arguments.add(binaryName);
            return this;
        }

        public UpdateCommand.Builder git(String gitSource) {
            updateCommand.arguments.add("--git");
            updateCommand.arguments.add(gitSource);
            return this;
        }

        public UpdateCommand.Builder local() {
            updateCommand.arguments.add("--local");
            return this;
        }
        public UpdateCommand.Builder local(String dirPath) {
            updateCommand.arguments.add("--local");
            updateCommand.arguments.add(dirPath);
            return this;
        }

        public UpdateCommand.Builder ref(String refName) {
            updateCommand.arguments.add("--ref");
            updateCommand.arguments.add(refName);
            return this;
        }

        public UpdateCommand.Builder project() {
            updateCommand.arguments.add("--project");
            return this;
        }
        public UpdateCommand.Builder project(String projectName) {
            updateCommand.arguments.add("--project");
            updateCommand.arguments.add(projectName);
            return this;
        }

        public UpdateCommand.Builder project(List<String> extraArgs) {
            updateCommand.arguments.addAll(extraArgs);
            return this;
        }

        public UpdateCommand build() { return this.updateCommand; }
    }
}