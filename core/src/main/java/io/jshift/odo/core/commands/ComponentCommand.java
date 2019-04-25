package io.jshift.odo.core.commands;

import io.jshift.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class ComponentCommand implements Command {

    private static final String COMMAND_NAME = "component";

    private static final String APP = "--app";
    private static final String PROJECT = "--project";
    private static final String CONTEXT = "--context";
    private static final String SHORT = "--short";


    protected String app;
    protected String project;
    protected String context;
    protected Boolean q;

    private ComponentCommand() {
    }

    @Override
    public List<String> getCliCommand() {
        throw new UnsupportedOperationException();
    }

    public String getCommandName() {
        return COMMAND_NAME;
    }

    public List<String> getArguments() {
        final List<String> arguments = new ArrayList<>();

        if (app != null) {
            arguments.add(APP);
            arguments.add(app);
        }

        if (project != null) {
            arguments.add(PROJECT);
            arguments.add(project);
        }

        if (context != null) {
            arguments.add(CONTEXT);
            arguments.add(context);
        }

        if (q != null && q) {
            arguments.add(SHORT);
        }

        return arguments;

    }

    public static class Builder {

        private ComponentCommand componentCommand;

        public Builder() {
            componentCommand = new ComponentCommand();
        }
        public ComponentCommand build() {
            return this.componentCommand;
        }

    }

}
