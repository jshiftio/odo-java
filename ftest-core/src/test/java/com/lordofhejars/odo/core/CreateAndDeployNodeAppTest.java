package com.lordofhejars.odo.core;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.testbed.assertj.UrlAssertion;
import com.lordofthejars.odo.testbed.api.GitClone;
import com.lordofthejars.odo.testbed.junit5.GitExtension;
import com.lordofthejars.odo.testbed.junit5.OpenShiftConditionExtension;
import com.lordofthejars.odo.testbed.junit5.OpenShiftInjector;
import com.lordofthejars.odo.testbed.junit5.OpenShiftOperation;
import java.nio.file.Path;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
    public void removeComponentsAndRoutes(Path cloneRepo) { // Clean components created by odo
        odo.deleteUrl("route").build().execute(cloneRepo);
        odo.deleteComponent("nodejs").build().execute(cloneRepo);
    }

    @Test
    public void should_create_and_deploy_apps(Path cloneRepo, OpenShiftOperation openShiftOperation) {

        // When

        odo.createComponent("nodejs").withComponentName("nodejs").build().execute(cloneRepo);
        odo.pushComponent().build().execute(cloneRepo);
        odo.createUrl().withComponentName("route").build().execute(cloneRepo);

        // Then

        final Optional<String> exposedRoute = openShiftOperation.getHostOfRouteStartingWith("route");
        assertThat(exposedRoute).isNotEmpty();

        UrlAssertion.assertThat(exposedRoute.get()).isUpAndRunning(); // Custom assertj assertion that validate that the service (nodejs) is up and running
    }

}
