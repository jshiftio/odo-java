package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.api.Command;
import com.lordofthejars.odo.api.RunnableCommand;
import com.lordofthejars.odo.core.OdoExecutor;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractRunnableCommand<RETURNTYPE> implements RunnableCommand<RETURNTYPE>, Command {

    protected OdoExecutor odoExecutor;
    protected Function<List<String>, RETURNTYPE> parse;

    protected AbstractRunnableCommand(final OdoExecutor odoExecutor) {
        this.odoExecutor = odoExecutor;
    }

    protected AbstractRunnableCommand(final OdoExecutor odoExecutor, Function<List<String>, RETURNTYPE> parse) {
        this.odoExecutor = odoExecutor;
        this.parse = parse;
    }

    @Override
    public RETURNTYPE execute(Path directory) {

        final List<String> output = this.odoExecutor.execute(directory, this);

        if (parse != null) {
            return parse.apply(output);
        }

        return null;
    }

    @Override
    public RETURNTYPE execute() {
        final List<String> output = this.odoExecutor.execute(this);

        if (parse != null) {
            return parse.apply(output);
        }

        return null;
    }
}
