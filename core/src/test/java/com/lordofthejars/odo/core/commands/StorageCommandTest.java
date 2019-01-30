package com.lordofthejars.odo.core.commands;

import java.util.List;
import org.junit.jupiter.api.Test;

import static com.lordofthejars.odo.core.commands.CommandTransformer.transform;
import static org.assertj.core.api.Assertions.assertThat;

public class StorageCommandTest {

    @Test
    public void should_execute_create_storage_command() {

        // Given

        final StorageCreateCommand storageCreateCommand = new StorageCreateCommand.Builder("mystorage")
            .withPath("/opt/app-root/src/storage/")
            .withSize("1Gi")
            .build();

        final StorageCommand storageCommand = new StorageCommand.Builder(storageCreateCommand).build();

        // When

        final List<String> cliCommand = storageCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("storage create mystorage --path=/opt/app-root/src/storage/ --size=1Gi"));

    }

    @Test
    public void should_execute_simple_delete_command() {

        // Given

        final StorageDeleteCommand storageDeleteCommand = new StorageDeleteCommand.Builder("mystorage")
            .build();

        final StorageCommand storageCommand = new StorageCommand.Builder(storageDeleteCommand).build();

        // When

        final List<String> cliCommand = storageCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("storage delete mystorage --force"));
    }

    @Test
    public void should_execute_delete_command_with_component() {

        // Given

        final StorageDeleteCommand storageDeleteCommand = new StorageDeleteCommand.Builder("mystorage")
            .withComponent("mongodb")
            .build();

        final StorageCommand storageCommand = new StorageCommand.Builder(storageDeleteCommand).build();

        // When

        final List<String> cliCommand = storageCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("storage delete mystorage --component mongodb --force"));
    }

    @Test
    public void should_execute_complete_mount_command() {

        // Given

        final StorageMountCommand storageMountCommand = new StorageMountCommand.Builder("dbstorage")
            .withPath("/data")
            .withComponent("mongodb")
            .build();

        StorageCommand storageCommand = new StorageCommand.Builder(storageMountCommand).build();

        // When

        final List<String> cliCommand = storageCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("storage mount dbstorage --component mongodb --path /data"));

    }

    @Test
    public void should_execute_complete_unmount_command() {

        // Given

        final StorageUnmountCommand storageUnmountCommand = new StorageUnmountCommand.Builder()
            .withStorageName("database")
            .withComponent("mongodb")
            .build();

        final StorageCommand storageCommand = new StorageCommand.Builder(storageUnmountCommand).build();

        // When

        final List<String> cliCommand = storageCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("storage unmount database --component mongodb"));
    }

    @Test
    public void should_execute_unmount_from_path_command() {

        // Given

        final StorageUnmountCommand storageUnmountCommand = new StorageUnmountCommand.Builder()
            .withPath("/data")
            .build();

        final StorageCommand storageCommand = new StorageCommand.Builder(storageUnmountCommand).build();

        // When

        final List<String> cliCommand = storageCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("storage unmount /data"));

    }

}
