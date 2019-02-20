package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import java.util.ArrayList;
import java.util.List;

public class UnlinkCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "unlink";

    private String name;

    private static final String APP = "--app";
    private static final String COMPONENT = "--component";
    private static final String PORT = "--port";
    private static final String PROJECT = "--project";

    private String app;
    private String component;
    private String port;
    private String project;

    private GlobalParametersSupport globalParametersSupport;

    protected UnlinkCommand(String name, OdoExecutor odoExecutor) {
        super(odoExecutor);
        this.name = name;
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

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<UnlinkCommand.Builder> {

        private UnlinkCommand unlinkCommand;

        public Builder(String name, OdoExecutor odoExecutor) {
            unlinkCommand = new UnlinkCommand(name, odoExecutor);
        }

        public UnlinkCommand.Builder withComponent(String component) {
            unlinkCommand.component = component;
            return this;
        }

        public UnlinkCommand.Builder withApp(String app) {
            unlinkCommand.app = app;
            return this;
        }

        public UnlinkCommand.Builder withPort(String port) {
            unlinkCommand.port = port;
            return this;
        }

        public UnlinkCommand.Builder withProject(String project) {
            unlinkCommand.project = project;
            return this;
        }

        public UnlinkCommand build() {
            this.unlinkCommand.globalParametersSupport = buildGlobalParameters();
            return unlinkCommand;
        }

    }
}
