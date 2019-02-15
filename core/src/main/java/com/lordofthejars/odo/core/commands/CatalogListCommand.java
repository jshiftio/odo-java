package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogListCommand implements Command<List<String>> {

    private static final String COMMAND_NAME = "list";

    private String command;

    private CatalogListCommand(String command) {
        this.command = command;
    }

    @Override
    public List<String> getCliCommand() {

        final List<String> arguments = new ArrayList<>();
        arguments.add(COMMAND_NAME);
        arguments.add(this.command);

        return arguments;
    }

    @Override
    public List<String> parse(List<String> consoleOutput) {

        final List<String> catalogElements = new ArrayList<>();

        //TODO This will change when we have the machine output format so now we just do the minimal

        if (consoleOutput.size() > 1) {
            catalogElements.addAll(consoleOutput.stream()
                .skip(1) // First line is the header
                .map( row -> {
                    int index = row.indexOf(' ');
                    if (index > -1) {
                        return row.substring(0, index);
                    }
                    return "";
                })
                .map(String::trim)
                .collect(Collectors.toList()));
        }

        return catalogElements;
    }

    public static class Builder {

        private CatalogListCommand catalogListCommand;

        public Builder(String command) {
            this.catalogListCommand = new CatalogListCommand(command);
        }

        public CatalogListCommand build(){
            return this.catalogListCommand;
        }

    }
}
