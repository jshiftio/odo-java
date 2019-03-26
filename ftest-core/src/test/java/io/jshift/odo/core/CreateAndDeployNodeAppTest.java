package io.jshift.odo.core;

import io.jshift.odo.api.GitClone;
import io.jshift.odo.assertj.UrlAssertion;
import io.jshift.odo.junit5.GitExtension;
import io.jshift.odo.junit5.OpenShiftConditionExtension;
import io.jshift.odo.junit5.OpenShiftInjector;
import io.jshift.odo.junit5.OpenShiftOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.file.Path;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith
    ({
        OpenShiftConditionExtension.class, // Checks if there is an OpenShift cluster running
        GitExtension.class, // Clones project to temporal directory
        OpenShiftInjector.class // Injects OpeShift client to do assertions
    })
@GitClone("https://github.com/openshift/nodejs-ex") // Project to clone
public class CreateAndDeployNodeAppTest {

    private final Odo odo = new Odo(); // Creates Odo instance

    @AfterEach
    public void removeComponentsAndRoutes(Path cloneRepo) { // Clean components created by io.jshift.odo
        odo.deleteUrl("route").build().execute(cloneRepo);
        odo.deleteComponent("nodejs").build().execute(cloneRepo);
    }

    @Test
    public void should_create_and_deploy_apps(Path cloneRepo, OpenShiftOperation openShiftOperation) {

        // When

        odo.createComponent("nodejs").withComponentName("nodejs").build().execute(cloneRepo);
        odo.pushComponent().build().execute(cloneRepo);
        odo.createUrl().withComponent("route").build().execute(cloneRepo);
        // Then

        final Optional<String> exposedRoute = openShiftOperation.getHostOfRouteStartingWith("route");
        assertThat(exposedRoute).isNotEmpty();

        UrlAssertion.assertThat(exposedRoute.get()).isUpAndRunning(); // Custom assertj assertion that validate that the service (nodejs) is up and running
    }

}
