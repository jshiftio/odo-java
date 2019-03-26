package io.jshift.odo.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleHttpClient {

    private String url;

    public SimpleHttpClient(String url) {
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        this.url = url;
    }

    public String read() throws IOException {
        return doHttpUrlConnectionAction();
    }

    private String doHttpUrlConnectionAction() throws IOException {
        StringBuilder stringBuilder;

        final URL url = new URL(this.url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // just want to do an HTTP GET here
        connection.setRequestMethod("GET");

        connection.setReadTimeout(10 * 1000);
        connection.connect();

        // read the output from the server
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            stringBuilder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }
}
