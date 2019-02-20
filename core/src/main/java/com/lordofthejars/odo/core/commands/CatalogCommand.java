package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;

public class CatalogCommand implements Command {

    private static final String COMMAND_NAME = "catalog";

    private CatalogCommand() {
    }

    @Override
    public List<String> getCliCommand() {

        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);
        return arguments;
    }

    public static class Builder {

        private CatalogCommand catalogCommand;

        public Builder() {
            this.catalogCommand = new CatalogCommand();
        }

        public CatalogCommand build() {
            return this.catalogCommand;
        }

    }

}
