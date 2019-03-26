package io.jshift.odo.junit5;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import me.snowdrop.servicecatalog.api.client.ServiceCatalogClient;
import me.snowdrop.servicecatalog.api.model.ServiceInstance;
import me.snowdrop.servicecatalog.api.model.ServiceInstanceList;

import static org.awaitility.Awaitility.await;

public class OpenShiftOperation {

    private OpenShiftClient openShiftClient = new DefaultOpenShiftClient();

    public Optional<String> getHostOfRouteStartingWith(String prefix) {
        return openShiftClient.routes()
            .list()
            .getItems()
            .stream()
            .filter(r -> r.getSpec().getHost().startsWith(prefix))
            .map(r -> r.getSpec().getHost())
            .findFirst();
    }

    public Optional<ServiceInstance> getServiceInstance(String name) {
        final ServiceCatalogClient sc = openShiftClient.adapt(ServiceCatalogClient.class);
        final ServiceInstanceList serviceInstanceList = sc.serviceInstances().list();

        final List<ServiceInstance> items = serviceInstanceList.getItems();

        for (ServiceInstance serviceInstance : items) {
            if (serviceInstance.getMetadata().getName().equals(name)) {
                return Optional.of(serviceInstance);
            }
        }

        return Optional.empty();
    }

    public void awaitUntilServiceInstanceRegistered(String name) {
        await().atMost(30, TimeUnit.SECONDS)
            .until(() -> getServiceInstance(name).isPresent());
    }

    public void awaitUntilServiceInstanceIsProvisioned(String name) {
        await().atMost(120, TimeUnit.SECONDS)
            .until(() -> getServiceInstance(name).get().getStatus().getProvisionStatus().equalsIgnoreCase("Provisioned"));
    }

}