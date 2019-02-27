package com.lordofthejars.odo.core;

import com.lordofthejars.odo.api.Command;
import java.nio.file.Path;
import java.util.List;

public interface CliExecutor {
    List<String> execute(Command command);

    List<String> execute(Path directory, Command command);
}
