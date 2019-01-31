package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class LinkCommand implements Command {

    private static final String COMMAND_NAME = "link";

    private String name;

    private static final String APP = "--app";
    private static final String COMPONENT = "--component";
    private static final String PORT = "--port";
    private static final String PROJECT = "--project";
    private static final String WAIT = "--wait";

    private String app;
    private String component;
    private String port;
    private String project;
    private Boolean wait;

    private LinkCommand(String name) {
        this.name =name;
    }

    @Override
    public List<String> getCliCommand() {

        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);
        arguments.add(name);

        if (component != null) {
            arguments.add(COMPONENT);
            arguments.add(component);
        }

        if (app != null) {
            arguments.add(APP);
            arguments.add(app);
        }

        if (port != null) {
            arguments.add(PORT);
            arguments.add(port);
        }

        if (project != null) {
            arguments.add(PROJECT);
            arguments.add(project);
        }

        if (wait != null && wait.booleanValue()) {
            arguments.add(WAIT);
        }

        return arguments;
    }

    public static class Builder {

        private LinkCommand linkCommand;

        public Builder(String name) {
            linkCommand = new LinkCommand(name);
        }

        public LinkCommand.Builder withComponent(String component) {
            linkCommand.component = component;
            return this;
        }

        public LinkCommand.Builder withApp(String app) {
            linkCommand.app = app;
            return this;
        }

        public LinkCommand.Builder withPort(String port) {
            linkCommand.port = port;
            return this;
        }

        public LinkCommand.Builder withProject(String project) {
            linkCommand.project = project;
            return this;
        }

        public LinkCommand.Builder withWait() {
            linkCommand.wait = Boolean.TRUE;
            return this;
        }

        public LinkCommand build() {
            return linkCommand;
        }

    }

}
