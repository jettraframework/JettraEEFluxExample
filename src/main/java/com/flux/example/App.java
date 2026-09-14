package com.flux.example;

import io.jettra.ee.JettraEE;
import io.jettra.ee.server.JettraEEServer;
import org.eclipse.microprofile.config.ConfigProvider;

/**
 * Servidor principal de JettraEEFluxExample.
 * Combina el servidor ultra-ligero JettraEE (MicroProfile, Jakarta EE, Virtual Threads Loom)
 * con la suite completa de interfaces reactivas de JettraFlux y validaciones JettraRules.
 */
public class App {

    public static JettraEEServer serverInstance;

    public static void main(String[] args) {
        int port = 8080;
        String contextPath = "/";

        try {
            var cfg = ConfigProvider.getConfig();
            port = cfg.getOptionalValue("server.port", Integer.class).orElse(port);
            contextPath = cfg.getOptionalValue("server.contextpath", String.class).orElse(contextPath);
        } catch (Exception ignored) {}

        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if ("--port".equals(args[i]) && i + 1 < args.length) {
                    port = Integer.parseInt(args[++i]);
                } else if ("--context-path".equals(args[i]) && i + 1 < args.length) {
                    contextPath = args[++i];
                }
            }
        }

        // Configurar redirección de ErrorPage
        io.jettra.flux.complex.ErrorPage.path = "http://localhost:" + port + contextPath;

        System.out.println("Iniciando JettraEEFluxExample en puerto " + port + "...");

        serverInstance = JettraEE.builder()
                .port(port)
                .contextPath(contextPath)
                .title("JettraEEFlux Example API")
                .version("1.0.0")
                .scanPackages("com.flux", "jcf")
                .registerFluxPage("/", com.flux.example.pages.login.LoginPage.class)
                .build();

        serverInstance.start();
    }
}
