package io.jshift.odo.core.commands;

import java.util.ArrayList;
import java.util.List;
import io.jshift.odo.core.CliExecutor;

public class UrlCreateCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "create";

    private String urlName;

    private static final String COMPONENT = "--component";
    private static final String PORT = "--port";

    private String component;
    private Integer port;

    private UrlCommand urlCommand;
    private GlobalParametersSupport globalParametersSupport;

    private UrlCreateCommand(UrlCommand urlCommand, Integer port, CliExecutor odoExecutor){
        super(odoExecutor);
        this.urlCommand = urlCommand;
        this.port = port;
    }

    @Override
    public List<String> getCliCommand() {

        final List<String> arguments = new ArrayList<>();

        arguments.addAll(urlCommand.getCliCommand());

        arguments.add(COMMAND_NAME);

        if (urlName != null) {
            arguments.add(urlName);
        }

        if (component != null) {
            arguments.add(COMPONENT);
            arguments.add(component);
        }

        if (port != null) {
            arguments.add(PORT);
            arguments.add(Integer.toString(port));
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    public static class Builder extends GlobalParametersSupport.Builder<UrlCreateCommand.Builder> {
        private UrlCreateCommand urlCreateCommand;

        public Builder(UrlCommand urlCommand, Integer port, CliExecutor odoExecutor) {
            this.urlCreateCommand = new UrlCreateCommand(urlCommand, port, odoExecutor);
        }

        public UrlCreateCommand.Builder withName(String urlName) {
            this.urlCreateCommand.urlName = urlName;
            return this;
        }

        public UrlCreateCommand.Builder withComponent(String component) {
            this.urlCreateCommand.component = component;
            return this;
        }

        public UrlCreateCommand build() {
            urlCreateCommand.globalParametersSupport = buildGlobalParameters();
            return urlCreateCommand;
        }

    }
}
