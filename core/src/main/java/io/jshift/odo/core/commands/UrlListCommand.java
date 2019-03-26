package io.jshift.odo.core.commands;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import io.jshift.odo.core.CliExecutor;
import io.jshift.odo.core.commands.output.UrlList;
import java.util.ArrayList;
import java.util.List;

public class UrlListCommand extends AbstractRunnableCommand<UrlList> {

    private static final String COMMAND_NAME = "list";
    private static final String OUTPUT = "--output";
    private static final String DEFAULT_FORMAT = "json";

    private UrlCommand urlCommand;
    private GlobalParametersSupport globalParametersSupport;

    private UrlListCommand(UrlCommand urlCommand, CliExecutor odoExecutor) {
        super(odoExecutor, UrlListCommand::parse);
        this.urlCommand = urlCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(urlCommand.getCliCommand());

        arguments.add(COMMAND_NAME);

        arguments.add(OUTPUT);
        arguments.add(DEFAULT_FORMAT);

        if (this.globalParametersSupport != null) {
            arguments.addAll(this.globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    protected static UrlList parse(List<String> consoleOutput) {
        final String output = String.join(" ", consoleOutput.toArray(new String[consoleOutput.size()]));

        if (output.length() == 0) return null;
        final JsonValue outputJson = Json.parse(output);

        final JsonObject urlJson = outputJson.asObject();
        return UrlList.from(urlJson);
    }

    public static class Builder extends GlobalParametersSupport.Builder<UrlListCommand.Builder> {
        private UrlListCommand urlListCommand;

        public Builder(UrlCommand urlCommand, CliExecutor odoExecutor) {
            this.urlListCommand = new UrlListCommand(urlCommand, odoExecutor);
        }

        public UrlListCommand build() {
            urlListCommand.globalParametersSupport = buildGlobalParameters();
            return urlListCommand;
        }

    }
}