package io.jshift.odo.core.commands;

import io.jshift.odo.core.CliExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigSetCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "set";
    private static final String CONTEXT = "--context";
    private static final String FORCE = "--force";
    private static final String ENV = "--env";

    private String parameter;
    private String value;

    private ConfigCommand configCommand;
    private GlobalParametersSupport globalParametersSupport;

    private String context;
    private Boolean force;
    private List<String> env;

    private ConfigSetCommand(ConfigCommand configCommand, String parameter, String value, CliExecutor odoExecutor) {
        super(odoExecutor);
        this.configCommand = configCommand;
        this.parameter = parameter;
        this.value = value;
    }

    @Override
    public List<String> getCliCommand() {

        final List<String> arguments = new ArrayList<>();

        arguments.addAll(this.configCommand.getCliCommand());

        arguments.add(COMMAND_NAME);
        arguments.add(parameter);
        arguments.add(value);

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

    public static class Builder extends GlobalParametersSupport.Builder<ConfigSetCommand.Builder> {
        private ConfigSetCommand configSetCommand;

        public Builder(ConfigCommand configCommand, String parameter, String value, CliExecutor odoExecutor) {
            this.configSetCommand = new ConfigSetCommand(configCommand, parameter, value, odoExecutor);
        }

        public ConfigSetCommand.Builder withForce() {
            configSetCommand.force = Boolean.TRUE;
            return this;
        }

        public ConfigSetCommand.Builder withContext(String context) {
            configSetCommand.context = context;
            return this;
        }

        public ConfigSetCommand.Builder withEnv(List<String> env) {
            this.configSetCommand.env = env;
            return this;
        }

        public ConfigSetCommand build() {
            configSetCommand.globalParametersSupport = buildGlobalParameters();
            return configSetCommand;
        }
    }

}
