package com.biman.helidon;

import io.helidon.config.Config;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class GreetService implements HttpService {

    private final AtomicReference<String> greeting = new AtomicReference<>();

    GreetService() {
        this(Config.global().get("app"));
    }

    GreetService(Config appConfig) {
        greeting.set(appConfig.get("greeting").asString().orElse("Ciao"));
    }

    @Override
    public void routing(HttpRules rules) {
        //         curl -X GET http://localhost:8090/greet
        rules.get("/", this::getDefaultMessageHandler);
    }

    private void getDefaultMessageHandler(ServerRequest request, ServerResponse response) {
        sendResponse(response, "World");

    }

    private void sendResponse(ServerResponse response, String name) {
        String message = String.format("%s %s!", greeting.get(), name);
        JsonObject json = Json.createObjectBuilder()
                .add("message", message).build();
        response.send(json);
    }
}
