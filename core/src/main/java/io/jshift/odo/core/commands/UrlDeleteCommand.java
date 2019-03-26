package io.jshift.odo.core.commands;

import java.util.ArrayList;
import java.util.List;
import io.jshift.odo.core.CliExecutor;

public class UrlDeleteCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "delete";

    private String urlName;
    private UrlCommand urlCommand;
    private GlobalParametersSupport globalParametersSupport;

    private UrlDeleteCommand(UrlCommand urlCommand, String urlName, CliExecutor odoExecutor) {
        super(odoExecutor);
        this.urlName = urlName;
        this.urlCommand = urlCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(this.urlCommand.getCliCommand());

        arguments.add(COMMAND_NAME);

        arguments.add(urlName);

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<UrlDeleteCommand.Builder> {
        private UrlDeleteCommand urlDeleteCommand;

        public Builder(UrlCommand urlCommand, String urlName, CliExecutor odoExecutor) {
            this.urlDeleteCommand = new UrlDeleteCommand(urlCommand, urlName, odoExecutor);
        }

        public UrlDeleteCommand build() {
            urlDeleteCommand.globalParametersSupport =  buildGlobalParameters();
            return urlDeleteCommand;
        }
    }
}
