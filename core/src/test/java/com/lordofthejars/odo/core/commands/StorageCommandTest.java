package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lordofthejars.odo.core.commands.CommandTransformer.transform;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class StorageCommandTest {

    @Mock
    private OdoExecutor odoExecutor;

    private StorageCommand storageCommand = new StorageCommand.Builder().build();

    @Test
    public void should_execute_create_storage_command() {

        // Given

        final StorageCreateCommand storageCreateCommand = new StorageCreateCommand.Builder(storageCommand,"mystorage", odoExecutor)
            .withPath("/opt/app-root/src/storage/")
            .withSize("1Gi")
            .build();

        // When

        final List<String> cliCommand = storageCreateCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("storage create mystorage --path /opt/app-root/src/storage/ --size 1Gi"));

    }

    @Test
    public void should_execute_simple_delete_command() {

        // Given

        final StorageDeleteCommand storageDeleteCommand = new StorageDeleteCommand.Builder(storageCommand, "mystorage", odoExecutor)
            .build();

        // When

        final List<String> cliCommand = storageDeleteCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("storage delete mystorage --force"));
    }

    @Test
    public void should_execute_delete_command_with_component() {

        // Given

        final StorageDeleteCommand storageDeleteCommand = new StorageDeleteCommand.Builder(storageCommand, "mystorage", odoExecutor)
            .withComponent("mongodb")
            .build();

        // When

        final List<String> cliCommand = storageDeleteCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("storage delete mystorage --component mongodb --force"));
    }

    @Test
    public void should_execute_complete_mount_command() {

        // Given

        final StorageMountCommand storageMountCommand = new StorageMountCommand.Builder(storageCommand, "dbstorage", odoExecutor)
            .withPath("/data")
            .withComponent("mongodb")
            .build();

        // When

        final List<String> cliCommand = storageMountCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("storage mount dbstorage --component mongodb --path /data"));

    }

    @Test
    public void should_execute_complete_unmount_command() {

        // Given

        final StorageUnmountCommand storageUnmountCommand = new StorageUnmountCommand.Builder(storageCommand, "database", odoExecutor)
            .withComponent("mongodb")
            .build();

        // When

        final List<String> cliCommand = storageUnmountCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("storage unmount database --component mongodb"));
    }

    @Test
    public void should_execute_unmount_from_path_command() {

        // Given

        final StorageUnmountCommand storageUnmountCommand = new StorageUnmountCommand.Builder(storageCommand, "/data", odoExecutor)
            .build();

        // When

        final List<String> cliCommand = storageUnmountCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactlyInAnyOrder(transform("storage unmount /data"));

    }

}
