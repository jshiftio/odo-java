package io.jshift.odo.core.commands;

import io.jshift.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class UrlCommand implements Command {

    private static final String COMMAND_NAME = "url";

   private UrlCommand() {
   }

    @Override
    public List<String> getCliCommand() {

        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);

        return arguments;
    }

    public static class Builder {
        private UrlCommand urlCommand;

        public Builder() {
            urlCommand = new UrlCommand();
        }

        public UrlCommand build() {
            return urlCommand;
        }

    }
}
