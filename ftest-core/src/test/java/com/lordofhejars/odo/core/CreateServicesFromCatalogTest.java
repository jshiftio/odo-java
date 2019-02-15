package com.lordofhejars.odo.core;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.ServiceCommand;
import com.lordofthejars.odo.core.commands.ServiceCreateCommand;
import com.lordofthejars.odo.core.commands.ServiceDeleteCommand;
import com.lordofthejars.odo.testbed.api.Catalog;
import com.lordofthejars.odo.testbed.junit5.OpenShiftCatalogConditionExtension;
import com.lordofthejars.odo.testbed.junit5.OpenShiftConditionExtension;
import com.lordofthejars.odo.testbed.junit5.OpenShiftInjector;
import com.lordofthejars.odo.testbed.junit5.OpenShiftOperation;
import java.util.Optional;
import me.snowdrop.servicecatalog.api.model.ServiceInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith
    ({
        OpenShiftConditionExtension.class, // Checks if there is an OpenShift cluster running
        OpenShiftInjector.class, // Injects OpeShift client to do assertions
        OpenShiftCatalogConditionExtension.class // Checks for catalog requirements
    })
@Catalog(services = "postgresql-persistent")
public class CreateServicesFromCatalogTest {

    private final Odo odo = new Odo(); // Creates Odo instance

    @AfterEach
    public void removeService() { // Clean components created by odo

        final ServiceDeleteCommand serviceDeleteCommand = new ServiceDeleteCommand
            .Builder("postgresql-persistent").build();

        final ServiceCommand serviceCommand = new ServiceCommand.Builder(serviceDeleteCommand).build();
        odo.execute(serviceCommand);

    }

    @Test
    public  void should_provision_a_service(OpenShiftOperation openShiftOperation) {

        // Given

        // should be able to create postgresql

        final ServiceCreateCommand serviceCreateCommand = new ServiceCreateCommand
            .Builder("postgresql-persistent", "default")
            .withParameters("postgresql_user=luke",
                "postgresql_password=secret",
                "postgresql_database=my_data", "postgresql_version=9.6")
            .build();


        final ServiceCommand serviceCommand = new ServiceCommand
            .Builder(serviceCreateCommand)
            .build();

        odo.execute(serviceCommand);
        openShiftOperation.awaitUntilServiceInstanceRegistered("postgresql-persistent");
        openShiftOperation.awaitUntilServiceInstanceIsProvisioned("postgresql-persistent");

        // When

        final Optional<ServiceInstance> serviceInstance = openShiftOperation.getServiceInstance("postgresql-persistent");

        // Then

        assertThat(serviceInstance)
            .isNotEmpty();

        assertThat(serviceInstance.get().getStatus().getCurrentOperation())
            .isNullOrEmpty();

    }


}
