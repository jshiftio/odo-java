package io.jshift.odo.core.commands;

import io.jshift.odo.core.CliExecutor;
import java.util.ArrayList;
import java.util.List;

public class ConfigViewCommand extends AbstractRunnableCommand<String> {

    private static final String COMMAND_NAME = "view";
    private static final String CONTEXT = "--context";

    private ConfigCommand configCommand;
    private GlobalParametersSupport globalParametersSupport;

    private String context;

    private ConfigViewCommand(ConfigCommand configCommand, CliExecutor odoExecutor) {
        super(odoExecutor);
        this.configCommand = configCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(configCommand.getCliCommand());

        arguments.add(COMMAND_NAME);

        if (context != null) {
            arguments.add(CONTEXT);
            arguments.add(context);
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<ConfigViewCommand.Builder> {
        private ConfigViewCommand configViewCommand;

        public Builder(ConfigCommand configCommand, CliExecutor odoExecutor) {
            configViewCommand = new ConfigViewCommand(configCommand, odoExecutor);
        }

        public ConfigViewCommand.Builder withContext(String context) {
            configViewCommand.context = context;
            return this;
        }

        public ConfigViewCommand build() {
            configViewCommand.globalParametersSupport = buildGlobalParameters();
            return configViewCommand;
        }
    }
}
