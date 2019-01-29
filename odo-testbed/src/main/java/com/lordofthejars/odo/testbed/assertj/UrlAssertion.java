package com.lordofthejars.odo.testbed.assertj;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.assertj.core.api.AbstractAssert;

public class UrlAssertion extends AbstractAssert<UrlAssertion, String> {

    protected UrlAssertion(String s) {
        super(s, UrlAssertion.class);
    }

    public static UrlAssertion assertThat(String url) {
        return new UrlAssertion(url);
    }

    /**
     * Checks if the given URL returns an status code between 200 and 299.
     * @return this instance.
     */
    public UrlAssertion isUpAndRunning() {
        isNotNull();

        try {
            int statusCode = getStatusCode();

            if (statusCode < 200 && statusCode > 299) {
                failWithMessage("Expected status code for <%s> be between 200 and 299 but it is <%d>", actual, statusCode);
            }
        } catch (IOException e) {
            failWithMessage("Expected status code for <%s> be between 200 and 299 but exception <%s>", actual, e.getMessage());
        }

        return this;
    }

    /**
     * Checks if the given URL returns expected status code.
     * @param expectedStatusCode The expected status code.
     * @return this instance.
     */
    public UrlAssertion isStatusCode(int expectedStatusCode) {
        isNotNull();

        try {
            int statusCode = getStatusCode();

            if (statusCode != expectedStatusCode) {
                failWithMessage("Expected status code for <%s> be <%d> but it is <%d>", actual, expectedStatusCode, statusCode);
            }
        } catch (IOException e) {
            failWithMessage("Expected status code for <%s> be <%d> but exception <%s>", actual, expectedStatusCode, e.getMessage());
        }

        return this;
    }

    private int getStatusCode() throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(actual);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            return connection.getResponseCode();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
