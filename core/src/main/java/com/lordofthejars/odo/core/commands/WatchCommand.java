package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WatchCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "watch";

    private static final String APP = "--app";
    private static final String DELAY = "--delay";
    private static final String PROJECT = "--project";
    private static final String IGNORE = "--ignore";

    private String componentName;

    private String app;
    private String project;
    private Integer delay;
    private List<String> ignore = new ArrayList<>();

    private GlobalParametersSupport globalParametersSupport;

    protected WatchCommand(OdoExecutor odoExecutor) {
        super(odoExecutor);
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.add(COMMAND_NAME);

        if (componentName != null) {
            arguments.add(componentName);
        }

        if (app != null) {
            arguments.add(APP);
            arguments.add(app);
        }

        if (project != null) {
            arguments.add(PROJECT);
            arguments.add(project);
        }

        if (delay != null) {
            arguments.add(DELAY);
            arguments.add(Integer.toString(delay));
        }

        if (ignore != null && ignore.size() > 0) {
            arguments.add(IGNORE);
            arguments.add(toCsv(ignore));
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    private String toCsv(List<String> args) {
        return args.stream()
            .collect(Collectors.joining(", "));
    }

    public static class Builder extends GlobalParametersSupport.Builder<WatchCommand.Builder> {

        private WatchCommand watchCommand;

        public Builder(OdoExecutor odoExecutor) {
            this.watchCommand = new WatchCommand(odoExecutor);
        }

        public WatchCommand.Builder withComponentName(String componentName) {
            this.watchCommand.componentName = componentName;
            return this;
        }

        public WatchCommand.Builder withApp(String app) {
            this.watchCommand.app = app;
            return this;
        }

        public WatchCommand.Builder withProject(String project) {
            this.watchCommand.project = project;
            return this;
        }

        public WatchCommand.Builder withDelay(Integer delay) {
            this.watchCommand.delay = delay;
            return this;
        }

        public WatchCommand.Builder withIgnore(List<String> ignore) {
            this.watchCommand.ignore.addAll(ignore);
            return this;
        }

        public WatchCommand build() {
            watchCommand.globalParametersSupport = buildGlobalParameters();
            return watchCommand;
        }

    }

}
