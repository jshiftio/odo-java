package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.OdoExecutor;
import com.lordofthejars.odo.core.commands.output.Storage;
import com.lordofthejars.odo.core.commands.output.StorageList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lordofthejars.odo.core.commands.CommandTransformer.transform;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
            .containsExactly(transform("storage create mystorage --path /opt/app-root/src/storage/ --size 1Gi"));

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
            .containsExactly(transform("storage delete mystorage --force"));
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
            .containsExactly(transform("storage delete mystorage --component mongodb --force"));
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
            .containsExactly(transform("storage mount dbstorage --component mongodb --path /data"));

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
            .containsExactly(transform("storage unmount database --component mongodb"));
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
            .containsExactly(transform("storage unmount /data"));

    }

    @Test
    public void should_execute_describe_command() {

        //Given

        final StorageListCommand storageListCommand = new StorageListCommand.Builder(storageCommand, odoExecutor).build();

        // When

        final List<String> cliCommand = storageListCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactly(transform("storage list --output json"));

    }

    @Test
    public void should_list_storage() throws IOException {

        // Given

        final List<String> listDescribe = Files.readAllLines(Paths.get("src/test/resources", "storage_list.json"));
        final StorageListCommand storageListCommand = new StorageListCommand.Builder(storageCommand, odoExecutor).build();

        when(odoExecutor.execute(storageListCommand)).thenReturn(listDescribe);

        // When

        final StorageList storageList = storageListCommand.execute();

        // Then

        assertThat(storageList.getItems()).hasSize(1);

        final Storage storage = storageList.getItems().get(0);
        assertThat(storage.getTypeMeta().getKind()).isEqualTo("Storage");
        assertThat(storage.getStatus().isMounted()).isTrue();
        assertThat(storage.getSpec().getSize()).isEqualTo("1Gi");
        assertThat(storage.getSpec().getPath()).isEqualTo("/opt/app-root/src/storage/");

    }

}
