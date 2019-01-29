package com.lordofthejars.odo.testbed.junit5;

import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;
import java.util.Optional;

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

}
