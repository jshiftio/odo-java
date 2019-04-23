package io.jshift.odo.detectors.service;

import java.util.Objects;

public class DatabaseConfiguration {

    private String username = "";
    private String password = "";
    private String database = "";

    public DatabaseConfiguration() {
    }

    public DatabaseConfiguration(String username, String password, String database) {
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DatabaseConfiguration that = (DatabaseConfiguration) o;
        return Objects.equals(username, that.username) &&
            Objects.equals(password, that.password) &&
            Objects.equals(database, that.database);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, password, database);
    }
}
