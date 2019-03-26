package io.jshift.odo.core;

import io.jshift.odo.api.Command;
import io.jshift.odo.api.OdoConfiguration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import io.jshift.odo.terminal.CaptureOutput;
import io.jshift.odo.terminal.ConsoleOutput;
import io.jshift.odo.terminal.StreamDispatcher;
import org.zeroturnaround.exec.InvalidExitValueException;
import org.zeroturnaround.exec.ProcessExecutor;

public class OdoExecutor implements CliExecutor {

    private static final Logger logger = Logger.getLogger(OdoExecutor.class.getName());

    private OdoConfiguration odoConfiguration;

    private Path odoHome;

    OdoExecutor(Path odoHome, OdoConfiguration odoConfiguration) {
        this.odoHome = odoHome;
        this.odoConfiguration = odoConfiguration;
    }

    @Override
    public List<String> execute(Command command) {
        return this.execute(odoHome.getParent(), command);
    }

    @Override
    public List<String> execute(Path directory, Command command) {

        validateInput(this.odoHome, directory);

        final List<String> executionCommand = new ArrayList<>();
        executionCommand.add(this.odoHome.toString());
        executionCommand.addAll(command.getCliCommand());

        logger.info(
            String.format("Executing binary: %s at %s", executionCommand.stream().collect(Collectors.joining(" ")),
                directory));

        final CaptureOutput captureOutput = new CaptureOutput();

        final StreamDispatcher stdStreamDispatcher =
            new StreamDispatcher(captureOutput::capture);

        if (odoConfiguration.isPrintStandardStreamToConsole()) {
            stdStreamDispatcher.addConsumer(ConsoleOutput.forStandardConsoleOutput()::print);
        }

        final StreamDispatcher errStreamDispatcher = new StreamDispatcher();
        if (odoConfiguration.isPrintErrorStreamToConsole()) {
            errStreamDispatcher.addConsumer(ConsoleOutput.forErrorConsoleOutput()::print);
        }

        try {

            new ProcessExecutor()
                .directory(directory.toFile())
                .command(executionCommand)
                .destroyOnExit()
                .exitValueNormal()
                .readOutput(true)
                .redirectOutput(stdStreamDispatcher)
                .redirectErrorStream(false)
                .redirectError(errStreamDispatcher)
                .execute();

        } catch (InvalidExitValueException e) {
            throw new IllegalStateException(
                String.format("Error code %d. Result: %s", e.getExitValue(), e.getResult().outputUTF8()));
        } catch (InterruptedException | IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }

        return captureOutput.getOutput();
    }

    private void validateInput(Path binary, Path directory) {
        if (!Files.isRegularFile(binary)) {
            throw new IllegalArgumentException(String.format("%s binary path should point to odo executable.", binary));
        }

        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(
                String.format("%s should be a directory where running odo (typically the cloned project)", directory));
        }
    }

}
