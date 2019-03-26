package io.jshift.odo.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import io.jshift.odo.core.CliExecutor;

public class ComponentCreateCommand extends AbstractRunnableCommand<Void> {

    private static final String COMMAND_NAME = "create";

    private static final String APP = "--app";
    private static final String BINARY = "--binary";
    private static final String CPU = "--cpu";
    private static final String ENV = "--env";
    private static final String GIT = "--git";
    private static final String LOCAL = "--local";
    private static final String MAX_CPU = "--max-cpu";
    private static final String MAX_MEMORY = "--max-memory";
    private static final String MEMORY = "--memory";
    private static final String MIN_CPU = "--min-cpu";
    private static final String MIN_MEMORY = "--min-memory";
    private static final String PORT = "--port";
    private static final String PROJECT = "--project";
    private static final String REF = "--ref";
    private static final String WAIT = "--wait";

    private String componentType;
    private String componentName;

    private String app;
    private String binary;
    private String cpu;
    private List<String> env;
    private String git;
    private String local;
    private String maxCpu;
    private String maxMemory;
    private String memory;
    private String minCpu;
    private String minMemory;
    private List<String> port;
    private String project;
    private String ref;
    private Boolean wait;

    private ComponentCommand componentCommand;
    private GlobalParametersSupport globalParametersSupport;

    private ComponentCreateCommand(ComponentCommand componentCommand, String componentType, CliExecutor odoExecutor) {
        super(odoExecutor);
        this.componentType = componentType;
        this.componentCommand = componentCommand;
    }

    @Override
    public List<String> getCliCommand() {
        final List<String> arguments = new ArrayList<>();

        arguments.addAll(this.componentCommand.getCliCommand());

        arguments.add(COMMAND_NAME);
        arguments.add(componentType);

        if (componentName != null) {
            arguments.add(componentName);
        }

        if (app != null) {
            arguments.add(APP);
            arguments.add(app);
        }

        if (binary != null) {
            arguments.add(BINARY);
            arguments.add(binary);
        }

        if (cpu != null) {
            arguments.add(CPU);
            arguments.add(cpu);
        }

        if (env != null) {
            arguments.add(ENV);
            arguments.add(toCsv(env));
        }

        if (git != null) {
            arguments.add(GIT);
            arguments.add(git);
        }

        if (local != null) {
            arguments.add(LOCAL);
            arguments.add(local);
        }

        if (maxCpu != null) {
            arguments.add(MAX_CPU);
            arguments.add(maxCpu);
        }

        if (maxMemory != null) {
            arguments.add(MAX_MEMORY);
            arguments.add(maxMemory);
        }

        if (memory != null) {
            arguments.add(MEMORY);
            arguments.add(memory);
        }

        if (minCpu != null) {
            arguments.add(MIN_CPU);
            arguments.add(minCpu);
        }

        if (minMemory != null) {
            arguments.add(MIN_MEMORY);
            arguments.add(minMemory);
        }

        if (port != null) {
            arguments.add(PORT);
            arguments.add(toCsv(port));
        }

        if (project != null) {
            arguments.add(PROJECT);
            arguments.add(project);
        }

        if (ref != null) {
            arguments.add(REF);
            arguments.add(ref);
        }

        if (wait != null && wait.booleanValue()) {
            arguments.add(WAIT);
        }

        if (globalParametersSupport != null) {
            arguments.addAll(globalParametersSupport.getCliCommand());
        }

        return arguments;
    }

    private String toCsv(List<String> args) {
        return args.stream()
            .collect(Collectors.joining(", "));
    }

    public static class Builder extends GlobalParametersSupport.Builder<ComponentCreateCommand.Builder> {
        private ComponentCreateCommand componentCreateCommand;

        public Builder(ComponentCommand componentCommand, String componentType, CliExecutor odoExecutor) {
            this.componentCreateCommand = new ComponentCreateCommand(componentCommand, componentType, odoExecutor);
        }

        public Builder withComponentName(String componentName) {
            this.componentCreateCommand.componentName = componentName;
            return this;
        }

        public Builder withApp(String app) {
            this.componentCreateCommand.app = app;
            return this;
        }

        public Builder withBinary(String binary) {
            this.componentCreateCommand.binary = binary;
            return this;
        }

        public Builder withCpu(String cpu) {
            this.componentCreateCommand.cpu = cpu;
            return this;
        }

        public Builder withEnv(List<String> env) {
            this.componentCreateCommand.env = env;
            return this;
        }

        public Builder withGit(String git) {
            this.componentCreateCommand.git = git;
            return this;
        }

        public Builder withLocal(String local) {
            this.componentCreateCommand.local = local;
            return this;
        }

        public Builder withMaxCpu(String maxCpu) {
            this.componentCreateCommand.maxCpu = maxCpu;
            return this;
        }

        public Builder withMaxMemory(String maxMemory) {
            this.componentCreateCommand.maxMemory = maxMemory;
            return this;
        }

        public Builder withMemory(String memory) {
            this.componentCreateCommand.memory = memory;
            return this;
        }

        public Builder withMinCpu(String minCpu) {
            this.componentCreateCommand.minCpu = minCpu;
            return this;
        }

        public Builder withMinMemory(String minMemory) {
            this.componentCreateCommand.minMemory = minMemory;
            return this;
        }

        public Builder withPorts(List<String> ports) {
            this.componentCreateCommand.port = ports;
            return this;
        }

        public Builder withProject(String project) {
            this.componentCreateCommand.project = project;
            return this;
        }

        public Builder withRef(String ref) {
            this.componentCreateCommand.ref = ref;
            return this;
        }

        public Builder withWait() {
            this.componentCreateCommand.wait = true;
            return this;
        }

        public ComponentCreateCommand build() {
            componentCreateCommand.globalParametersSupport = buildGlobalParameters();
            return componentCreateCommand;
        }

    }

}
