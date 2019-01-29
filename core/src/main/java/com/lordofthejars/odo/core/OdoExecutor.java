package com.lordofthejars.odo.core;

import com.lordofthejars.odo.api.Command;
import com.lordofthejars.odo.api.OdoConfiguration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

class OdoExecutor {

    private static final Logger logger = Logger.getLogger(OdoExecutor.class.getName());

    private Process startedProcess;
    private OdoConfiguration odoConfiguration;

    OdoExecutor(OdoConfiguration odoConfiguration) {
        this.odoConfiguration = odoConfiguration;
    }

    void execute(Path binary, Command command) {
        this.execute(binary, binary.getParent(), command);
    }

    void execute(Path binary, Path directory, Command command) {

        if (!Files.isRegularFile(binary)) {
            throw new IllegalArgumentException(String.format("%s binary path should point to odo executable.", binary));
        }

        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(String.format("%s should be a directory where running odo (typically the cloned project)", directory));
        }

        final List<String> executionCommand = new ArrayList<>();
        executionCommand.add(binary.toString());
        executionCommand.addAll(command.getCliCommand());

        logger.info(String.format("Executing binary: %s at %s", executionCommand.stream().collect(Collectors.joining(" ")), directory));

        try {
            startedProcess = new ProcessBuilder(executionCommand)
                .directory(directory.toFile())
                .start();
            final int exitCode = startedProcess.waitFor();

            String failure = checkForFailure(exitCode);

            if (!failure.isEmpty()) {
                throw new IllegalStateException(failure);
            }

        } catch (IOException |InterruptedException e) {
            throw new IllegalStateException("Could not start Odo process", e);
        }
    }

    String checkForFailure(int exitCode) {
        if (exitCode > 0) {
            final InputStream errorStream = this.startedProcess.getErrorStream();
            String errorContent = new BufferedReader(new InputStreamReader(errorStream)).lines().collect(Collectors.joining(System.lineSeparator()));
            return  errorContent;
        }

        return "";
    }

}
