package com.lordofthejars.odo.core;

import com.lordofthejars.odo.core.commands.CreateCommand;
import com.lordofthejars.odo.core.commands.LinkCommand;
import com.lordofthejars.odo.core.commands.PushCommand;
import com.lordofthejars.odo.core.commands.UrlCreateCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OdoTest {

    @Mock
    OdoExecutor odoExecutor;

    @Test
    public void should_create_deployment() {

        // Given
        final Odo odo = new Odo(odoExecutor);
        final CreateCommand nodejs = odo.create("nodejs").build();

        // When

        nodejs.execute();

        // Then

        verify(odoExecutor).execute(nodejs);

    }

    @Test
    public void should_push_deployment() {

        // Given
        final Odo odo = new Odo(odoExecutor);
        final PushCommand pushCommand = odo.push().build();

        // When

        pushCommand.execute();

        // Then

        verify(odoExecutor).execute(pushCommand);

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
        final LinkCommand linkCommand = odo.link("provider").withComponent("consumer").withPort("8080").build();

        // When

        linkCommand.execute();

        // Then

        verify(odoExecutor).execute(linkCommand);

    }

}
