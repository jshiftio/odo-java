package com.lordofthejars.odo.core.commands.output;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class UrlSpec {

    private String URL;
    private String protocol;
    private int port;

    public static UrlSpec from(JsonObject jsonObject) {

        final UrlSpec urlSpec = new UrlSpec();

        final JsonValue path = jsonObject.get("path");
        if (path != null) {
            urlSpec.URL = path.asString();
        }

        final JsonValue protocol = jsonObject.get("protocol");
        if (protocol != null) {
            urlSpec.protocol = protocol.asString();
        }

        final JsonValue port = jsonObject.get("port");
        if (port != null) {
            urlSpec.port = port.asInt();
        }

        return urlSpec;
    }

    public String getURL() {
        return URL;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getPort() {
        return port;
    }
}
