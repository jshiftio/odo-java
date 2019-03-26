package io.jshift.odo.core;

import io.jshift.odo.junit5.OpenShiftCatalogConditionExtension;
import java.util.Optional;
import me.snowdrop.servicecatalog.api.model.ServiceInstance;
import io.jshift.odo.api.Catalog;
import io.jshift.odo.junit5.OpenShiftConditionExtension;
import io.jshift.odo.junit5.OpenShiftInjector;
import io.jshift.odo.junit5.OpenShiftOperation;
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
        odo.deleteService("postgresql-persistent").build().execute();
    }

    @Test
    public  void should_provision_a_service(OpenShiftOperation openShiftOperation) {

        // Given

        odo.createService("postgresql-persistent", "default")
            .withParameters("postgresql_user=luke",
                "postgresql_password=secret",
                "postgresql_database=my_data", "postgresql_version=9.6")
            .withWait()
            .build()
            .execute();

        // When

        openShiftOperation.awaitUntilServiceInstanceRegistered("postgresql-persistent");
        openShiftOperation.awaitUntilServiceInstanceIsProvisioned("postgresql-persistent");
        final Optional<ServiceInstance> serviceInstance = openShiftOperation.getServiceInstance("postgresql-persistent");

        // Then

        assertThat(serviceInstance)
            .isNotEmpty();

        assertThat(serviceInstance.get().getStatus().getCurrentOperation())
            .isNullOrEmpty();

    }


}
