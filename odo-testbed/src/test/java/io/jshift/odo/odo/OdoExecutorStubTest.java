package io.jshift.odo.odo;

import io.jshift.odo.core.Odo;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import io.jshift.odo.core.commands.output.UrlList;
import io.jshift.odo.assertj.OdoExecutorAssertion;
import io.jshift.odo.junit5.OdoExecutorStubInjector;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(OdoExecutorStubInjector.class)
public class OdoExecutorStubTest {

    @Test
    public void should_run_commands(OdoExecutorStub odoExecutorStub) {

        // Given

        final Odo odo = new Odo(odoExecutorStub);

        // When

        odo.createUrl().build().execute();

        // Then

        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo url create");

    }

    @Test
    public void should_run_commands_with_output(OdoExecutorStub odoExecutorStub) throws IOException {

        // Given

        final List<String> urlList = Files.readAllLines(Paths.get("src/test/resources", "url_list.json"));
        final Odo odo = new Odo(odoExecutorStub);

        // When

        odoExecutorStub.recordOutput(urlList);
        final UrlList list = odo.listUrls().build().execute();

        // Then

        OdoExecutorAssertion.assertThat(odoExecutorStub).hasExecuted("odo url list --output json");
        Assertions.assertThat(list.getItems()).hasSize(1);

    }

}
