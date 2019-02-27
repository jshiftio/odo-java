package com.lordofthejars.odo.core.commands;

import com.lordofthejars.odo.core.CliExecutor;
import com.lordofthejars.odo.core.commands.output.Url;
import com.lordofthejars.odo.core.commands.output.UrlList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlCommandTest {

    @Mock
    CliExecutor odoExecutor;

    private UrlCommand urlCommand = new UrlCommand.Builder().build();

    @Test
    public void should_list_url() throws IOException {
        
        // Given

        final List<String>
            urlListDescribe = Files.readAllLines(Paths.get("src/test/resources", "url_list.json"));
        final UrlListCommand urlListCommand = new UrlListCommand.Builder(urlCommand, odoExecutor).build();

        when(odoExecutor.execute(urlListCommand)).thenReturn(urlListDescribe);

        // When

        final UrlList urlList = urlListCommand.execute();

        // Then

        assertThat(urlList.getItems()).hasSize(1);

        final Url url = urlList.getItems().get(0);

        assertThat(url.getTypeMeta().getKind()).isEqualToIgnoringCase("url");
        assertThat(url.getSpec().getURL()).isEqualTo("route-nodejs-ex-lgpu-myproject.192.168.64.97.nip.io");
        assertThat(url.getSpec().getPort()).isEqualTo(8080);
    }

    @Test
    public void execute_url_list_command() {

        // Given

        final UrlListCommand urlListCommand = new UrlListCommand.Builder(urlCommand, odoExecutor).build();

        // When

        final List<String> cliCommand = urlListCommand.getCliCommand();

        // Then

        assertThat(cliCommand)
            .containsExactly("url", "list", "--output", "json");
    }

}
