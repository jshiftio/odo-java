package com.lordofthejars.odo.core;

import com.lordofthejars.odo.core.commands.ComponentCreateCommand;
import com.lordofthejars.odo.core.commands.ComponentLinkCommand;
import com.lordofthejars.odo.core.commands.ComponentPushCommand;
import com.lordofthejars.odo.core.commands.UrlCreateCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OdoTest {

    @Mock
    CliExecutor odoExecutor;

    @Test
    public void should_create_deployment() {

        // Given
        final Odo odo = new Odo(odoExecutor);
        final ComponentCreateCommand nodejs = odo.createComponent("nodejs").build();

        // When

        nodejs.execute();

        // Then

        verify(odoExecutor).execute(nodejs);

    }

    @Test
    public void should_push_deployment() {

        // Given
        final Odo odo = new Odo(odoExecutor);
        final ComponentPushCommand componentPushCommand = odo.pushComponent().build();

        // When

        componentPushCommand.execute();

        // Then

        verify(odoExecutor).execute(componentPushCommand);

    }

    @Test
    public void should_create_url() {

        // Given

        final Odo odo = new Odo(odoExecutor);
        final UrlCreateCommand urlCreateCommand = odo.createUrl().build();

        // When

        urlCreateCommand.execute();

        // Then

        verify(odoExecutor).execute(urlCreateCommand);

    }

    @Test
    public void should_link_components() {

        // Given

        final Odo odo = new Odo(odoExecutor);
        final ComponentLinkCommand componentLinkCommand = odo.linkComponent("provider").withComponent("consumer").withPort("8080").build();

        // When

        componentLinkCommand.execute();

        // Then

        verify(odoExecutor).execute(componentLinkCommand);

    }

}
