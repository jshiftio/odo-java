package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.CliExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogListCommand extends AbstractRunnableCommand<List<String>> {

    private static final String COMMAND_NAME = "list";

    private String command;

    private CatalogCommand catalogCommand;
    private GlobalParametersSupport globalParametersSupport;

    private CatalogListCommand(CatalogCommand catalogCommand, String command, CliExecutor odoExecutor) {
        super(odoExecutor, CatalogListCommand::parse);
        this.command = command;
        this.catalogCommand = catalogCommand;
    }

    @Override
    public List<String> getCliCommand() {

        final List<String> arguments = new ArrayList<>();

        arguments.addAll(catalogCommand.getCliCommand());

        arguments.add(COMMAND_NAME);
        arguments.add(this.command);

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    protected static List<String> parse(List<String> consoleOutput) {

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

    public static class Builder extends GlobalParametersSupport.Builder<CatalogListCommand.Builder> {

        private CatalogListCommand catalogListCommand;

        public Builder(CatalogCommand  catalogCommand, String command, CliExecutor odoExecutor) {
            this.catalogListCommand = new CatalogListCommand(catalogCommand, command, odoExecutor);
        }

        public CatalogListCommand build(){

            this.catalogListCommand.globalParametersSupport = buildGlobalParameters();
            return this.catalogListCommand;
        }

    }
}
