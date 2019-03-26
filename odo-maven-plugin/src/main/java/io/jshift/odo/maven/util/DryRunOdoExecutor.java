package io.jshift.odo.maven.util;

import io.jshift.odo.api.Command;
import io.jshift.odo.core.CliExecutor;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class DryRunOdoExecutor implements CliExecutor {

    private static final String ODO_COMMAND = "odo";

    @Override
    public List<String> execute(Command command) {
        System.out.println(join(ODO_COMMAND, command.getCliCommand()));
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<String> execute(Path directory, Command command) {
        return execute(command);
    }

    private String join(String command, List<String> commands) {
        return command + " " + String.join(" ", commands);
    }


}
