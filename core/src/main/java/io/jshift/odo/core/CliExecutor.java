package io.jshift.odo.core;

import io.jshift.odo.api.Command;
import java.nio.file.Path;
import java.util.List;

public interface CliExecutor {
    List<String> execute(Command command);

    List<String> execute(Path directory, Command command);
}
