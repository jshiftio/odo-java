package io.jshift.odo.core.commands;

import io.jshift.odo.api.Command;
import io.jshift.odo.api.RunnableCommand;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import io.jshift.odo.core.CliExecutor;

public abstract class AbstractRunnableCommand<RETURNTYPE> implements RunnableCommand<RETURNTYPE>, Command {

    protected CliExecutor odoExecutor;
    protected Function<List<String>, RETURNTYPE> parse;

    protected AbstractRunnableCommand(final CliExecutor odoExecutor) {
        this.odoExecutor = odoExecutor;
    }

    protected AbstractRunnableCommand(final CliExecutor odoExecutor, Function<List<String>, RETURNTYPE> parse) {
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
