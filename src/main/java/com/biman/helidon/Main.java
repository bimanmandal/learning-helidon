package com.biman.helidon;

import io.helidon.config.Config;
import io.helidon.logging.common.LogConfig;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;

public class Main {
    private Main() {
    }

    public static void main(String[] args) {
        LogConfig.configureRuntime();

        Config config = Config.create();
        WebServer webServer = WebServer.builder()
                .config(config.get("server"))
                .routing(Main::routing)
                .build();
        webServer.start();

        System.out.printf("Helidon WebServer started on port %d\n", webServer.port());

    }

    private static void routing(HttpRouting.Builder routing) {
        routing.register("/greet", new GreetService());
    }
}
