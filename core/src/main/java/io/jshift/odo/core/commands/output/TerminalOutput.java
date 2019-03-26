package io.jshift.odo.core.commands.output;

public interface TerminalOutput {

    boolean isSimple();

    default boolean isList() {
        return !isSimple();
    }

    default <T> T as(Class<T> clazz) {

        if (!TerminalOutput.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(String.format("%s must implement %s", clazz, TerminalOutput.class));
        }

        return clazz.cast(this);
    }

}
