package com.lordofhejars.odo.core;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.core.commands.CreateCommand;
import com.lordofthejars.odo.core.commands.DeleteCommand;
import com.lordofthejars.odo.core.commands.LinkCommand;
import com.lordofthejars.odo.core.commands.PushCommand;
import com.lordofthejars.odo.core.commands.UrlCommand;
import com.lordofthejars.odo.core.commands.UrlCreateCommand;
import com.lordofthejars.odo.core.commands.UrlDeleteCommand;
import com.lordofthejars.odo.testbed.api.Catalog;
import com.lordofthejars.odo.testbed.junit5.OpenShiftCatalogConditionExtension;
import com.lordofthejars.odo.testbed.junit5.OpenShiftConditionExtension;
import com.lordofthejars.odo.testbed.junit5.OpenShiftInjector;
import com.lordofthejars.odo.testbed.junit5.OpenShiftOperation;
import com.lordofthejars.odo.testbed.net.SimpleHttpClient;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
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
@Catalog(components = "openjdk18")
public class CreateAndDeployConsumerProviderTest {

    private final Path projectDir = Paths.get(".").toAbsolutePath()
        .getParent()
        .getParent()
        .resolve("e2e/link");
    private final Odo odo = new Odo(); // Creates Odo instance

    @AfterEach
    public void removeComponentsAndRoutes() { // Clean components created by odo

        final UrlDeleteCommand urlDeleteCommand = new UrlDeleteCommand.Builder("route").build();
        final UrlCommand urlCommand = new UrlCommand
            .Builder(urlDeleteCommand)
            .build();

        odo.execute(urlCommand);

        final DeleteCommand deleteProviderCommand = new DeleteCommand.Builder("provider").build();
        odo.execute(deleteProviderCommand);

        final DeleteCommand deleteConsumerCommand = new DeleteCommand.Builder("consumer").build();
        odo.execute(deleteConsumerCommand);

    }

    @Test
    public void should_create_deploy_and_link_components(OpenShiftOperation openShiftOperation)
        throws InterruptedException, IOException {

        // Given

        final CreateCommand createProviderCommand = new CreateCommand
            .Builder("openjdk18")
            .withComponentName("provider")
            .withLocal(projectDir.resolve("provider").toAbsolutePath().toString())
            .build();

        final PushCommand pushProviderCommand = new PushCommand
            .Builder()
            .withComponentName("provider")
            .build();

        final CreateCommand createConsumerCommand = new CreateCommand
            .Builder("openjdk18")
            .withComponentName("consumer")
            .withLocal(projectDir.resolve("consumer").toAbsolutePath().toString())
            .build();

        final PushCommand pushConsumerCommand = new PushCommand
            .Builder()
            .withComponentName("consumer")
            .build();

        final UrlCreateCommand urlCreateCommand = new UrlCreateCommand
            .Builder()
            .withComponentName("route")
            .withComponent("consumer")
            .withPort(8080)
            .build();

        final UrlCommand urlCommand = new UrlCommand
            .Builder(urlCreateCommand)
            .build();

        final LinkCommand linkCommand = new LinkCommand.Builder("provider")
            .withComponent("consumer")
            .withPort("8080")
            .withWait()
            .build();

        // When

        odo.execute(createProviderCommand);
        odo.execute(pushProviderCommand);

        odo.execute(createConsumerCommand);
        odo.execute(pushConsumerCommand);
        odo.execute(urlCommand);

        odo.execute(linkCommand);

        // Then

        final Optional<String> exposedRoute = openShiftOperation.getHostOfRouteStartingWith("route");
        assertThat(exposedRoute).isNotEmpty();

        // TODO wait flag has a bug and currently does not work, to not complicate anything now I just put an sleep
        // When bug fixed then we could remove this sleep.

        TimeUnit.SECONDS.sleep(25);

        final SimpleHttpClient simpleHttpClient = new SimpleHttpClient(exposedRoute.get());
        final String read = simpleHttpClient.read();

        assertThat(read).isEqualToIgnoringNewLines("HELLO WORLD");

    }

}
