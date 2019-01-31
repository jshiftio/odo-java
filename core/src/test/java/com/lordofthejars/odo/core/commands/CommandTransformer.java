package com.lordofthejars.odo.core.commands;

public class CommandTransformer {

    public static String[] transform(String command) {
        return command.split(" ");
    }

}
