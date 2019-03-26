package io.jshift.odo.junit5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public class OpenShiftConditionExtension implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        String checkOpenShiftStatus = checkOpenShiftStatus();
        if (checkOpenShiftStatus.isEmpty()) {
            return ConditionEvaluationResult.enabled("OpenShift cluster is installed and running.");
        }

        return ConditionEvaluationResult.disabled("No OpenShift cluster is installed. " + checkOpenShiftStatus);
    }

    private String checkOpenShiftStatus() {
        try {
            Process startedProcess = new ProcessBuilder("oc", "status", "--request-timeout=4s")
                .start();
            final int exitCode = startedProcess.waitFor();
            return checkForFailure(startedProcess, exitCode);
        } catch (IOException | InterruptedException e) {
            return e.getMessage();
        }
    }

    String checkForFailure(Process startedProcess, int exitCode) {
        if (exitCode > 0) {
            final InputStream errorStream = startedProcess.getErrorStream();
            String errorContent = new BufferedReader(new InputStreamReader(errorStream)).lines()
                .collect(Collectors.joining(System.lineSeparator()));
            return errorContent;
        }

        return "";
    }
}
