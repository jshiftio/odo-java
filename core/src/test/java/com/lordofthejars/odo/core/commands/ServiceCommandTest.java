package com.lordofthejars.odo.core.commands;

import java.util.List;
import org.junit.jupiter.api.Test;

import static com.lordofthejars.odo.core.commands.CommandTransformer.transform;
import static org.assertj.core.api.Assertions.assertThat;

public class ServiceCommandTest {

    @Test
    public void should_execute_service_create_command() {

        // Given

        final ServiceCreateCommand serviceCreateCommand = new ServiceCreateCommand
            .Builder("dh-postgresql-apb", "dev")
            .withServiceName("my-postgresql-db")
            .withParameters("postgresql_user=luke", "postgresql_password=secret")
            .build();

        final ServiceCommand serviceCommand = new ServiceCommand.Builder(serviceCreateCommand).build();

        // When

        final List<String> cliCommand = serviceCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(
            transform("service create dh-postgresql-apb my-postgresql-db --plan dev -p postgresql_user=luke -p postgresql_password=secret")
        );
    }

    @Test
    public void should_execute_service_delete_command() {

        // Given

        final ServiceDeleteCommand serviceDeleteCommand = new ServiceDeleteCommand.Builder("my-postgresql-db").build();
        final ServiceCommand serviceCommand = new ServiceCommand.Builder(serviceDeleteCommand).build();

        // When

        final List<String> cliCommand = serviceCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(transform("service delete my-postgresql-db --force"));
    }
}
