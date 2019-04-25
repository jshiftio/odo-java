package io.jshift.odo.core.commands;

import io.jshift.odo.core.CliExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigUnsetCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "unset";
    private static final String CONTEXT = "--context";
    private static final String FORCE = "--force";
    private static final String ENV = "--env";

    private String parameter;

    private ConfigCommand configCommand;
    private GlobalParametersSupport globalParametersSupport;

    private String context;
    private Boolean force;
    private List<String> env;

    private ConfigUnsetCommand(ConfigCommand configCommand, String parameter, CliExecutor odoExecutor) {
        super(odoExecutor);
        this.configCommand = configCommand;
        this.parameter = parameter;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(this.configCommand.getCliCommand());

        arguments.add(COMMAND_NAME);
        arguments.add(parameter);

        if (context != null) {
            arguments.add(CONTEXT);
            arguments.add(context);
        }

        if (force != null && force) {
            arguments.add(FORCE);
        }

        if (env != null) {
            arguments.add(ENV);
            arguments.add(toCsv(env));
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

    public static class Builder extends GlobalParametersSupport.Builder<ConfigUnsetCommand.Builder> {
        private ConfigUnsetCommand configUnsetCommand;

        public Builder(ConfigCommand configCommand, String parameter, CliExecutor odoExecutor) {
            this.configUnsetCommand = new ConfigUnsetCommand(configCommand, parameter, odoExecutor);
        }

        public ConfigUnsetCommand.Builder withForce() {
            configUnsetCommand.force = Boolean.TRUE;
            return this;
        }

        public ConfigUnsetCommand.Builder withContext(String context) {
            configUnsetCommand.context = context;
            return this;
        }

        public ConfigUnsetCommand.Builder withEnv(List<String> env) {
            this.configUnsetCommand.env = env;
            return this;
        }

        public ConfigUnsetCommand build() {
            configUnsetCommand.globalParametersSupport = buildGlobalParameters();
            return configUnsetCommand;
        }
    }
}
