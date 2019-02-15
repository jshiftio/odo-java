package com.lordofthejars.odo.api;

import java.util.List;

public interface Command<RETURN_TYPE> {
    List<String> getCliCommand();

    default RETURN_TYPE parse(List<String> consoleOutput) {
        return null;
    }

}
