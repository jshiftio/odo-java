package io.jshift.odo.core.commands;

import java.util.List;
import io.jshift.odo.core.CliExecutor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ServiceCommandTest {

    @Mock
    private CliExecutor odoExecutor;

    private ServiceCommand serviceCommand = new ServiceCommand.Builder().build();

    @Test
    public void should_execute_service_create_command() {

        // Given

        final ServiceCreateCommand serviceCreateCommand = new ServiceCreateCommand
            .Builder(serviceCommand, "dh-postgresql-apb", "dev", odoExecutor)
            .withServiceName("my-postgresql-db")
            .withParameters("postgresql_user=luke", "postgresql_password=secret")
            .build();


        // When

        final List<String> cliCommand = serviceCreateCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(
            CommandTransformer.transform("service create dh-postgresql-apb my-postgresql-db --plan dev -p postgresql_user=luke -p postgresql_password=secret")
        );
    }

    @Test
    public void should_execute_service_delete_command() {

        // Given

        final ServiceDeleteCommand serviceDeleteCommand = new ServiceDeleteCommand.Builder(serviceCommand,"my-postgresql-db", odoExecutor).build();

        // When

        final List<String> cliCommand = serviceDeleteCommand.getCliCommand();

        // Then

        assertThat(cliCommand).containsExactlyInAnyOrder(CommandTransformer.transform("service delete my-postgresql-db --force"));
    }
}
