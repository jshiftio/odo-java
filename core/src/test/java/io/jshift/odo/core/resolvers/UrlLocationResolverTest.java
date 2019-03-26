package io.jshift.odo.core.resolvers;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

public class UrlLocationResolverTest {

    public static final String REMOTE_URL =
        "https://gist.githubusercontent.com/lordofthejars/ac2823cec7831697d09444bbaa76cd50/raw/e4b43f1b6494766dfc635b5959af7730c1a58a93/deployment.yaml";

    @Test
    public void should_download_content_from_url() throws IOException {

        assumeThat(
            isUpAndRunning(
                REMOTE_URL)
        ).isTrue();

        // Given

        final UrlLocationResolver urlLocationResolver = new UrlLocationResolver(REMOTE_URL);

        // When

        final InputStream inputStream = urlLocationResolver.loadResource();

        // Then

        assertThat(inputStream)
            .isNotNull();
    }

    private boolean isUpAndRunning(String url) throws IOException {
        final URL request = new URL(url);

        HttpURLConnection requestConnection =  ( HttpURLConnection )  request.openConnection ();
        requestConnection.setRequestMethod ("HEAD");
        requestConnection.connect () ;
        int code = requestConnection.getResponseCode() ;
        requestConnection.disconnect();

        return code > 199 && code < 300;

    }


}
