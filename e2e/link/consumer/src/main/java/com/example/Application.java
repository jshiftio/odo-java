package com.example;

import io.javalin.Javalin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Application {

    public static void main(String[] args) {
        final Javalin app = Javalin.create().start(8080);
        app.get("/", ctx -> ctx.result(consume().toUpperCase()));
    }

    private static String consume() throws IOException {

        final String url = String.format("http://%s:%s/",
            System.getenv("COMPONENT_PROVIDER_HOST"),
            System.getenv("COMPONENT_PROVIDER_PORT"));

        System.out.println("Getting message from " + url);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        final BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

}
