package com.lordofthejars.odo.testbed.odo;

import com.lordofthejars.odo.api.Command;
import com.lordofthejars.odo.core.CliExecutor;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OdoExecutorStub implements CliExecutor {

    private static final String ODO_COMMAND = "odo";
    private List<String> executedCommands = new LinkedList<>();

    private List<String> output = new ArrayList<>();

    public void recordOutput(String ...output) {
        this.output.addAll(Arrays.asList(output));
    }

    public void recordOutput(List<String>output) {
        this.output.addAll(output);
    }

    @Override
    public List<String> execute(Command command) {

        final List<String> currentOutput = this.output.stream().collect(Collectors.toList());

        this.output.clear();

        executedCommands.add(join(ODO_COMMAND, command.getCliCommand()));

        return currentOutput;
    }

    private String join(String command, List<String> commands) {
        return command + " " + String.join(" ", commands);
    }

    public void cleanOutput() {
        executedCommands.clear();
    }

    @Override
    public List<String> execute(Path directory, Command command) {
        return execute(command);
    }

    public List<String> getExecutedCommands() {
        return executedCommands;
    }
}
