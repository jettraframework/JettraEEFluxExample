package com.flux.example.health;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

@ApplicationScoped
@Liveness
@Readiness
public class AppHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory() / (1024 * 1024);
        long totalMemory = runtime.totalMemory() / (1024 * 1024);
        int availableProcessors = runtime.availableProcessors();

        return HealthCheckResponse.named("JettraEEFluxLiveness")
                .up()
                .withData("freeMemoryMB", freeMemory)
                .withData("totalMemoryMB", totalMemory)
                .withData("availableProcessors", availableProcessors)
                .withData("serverEngine", "JettraEE Virtual Threads Loom")
                .build();
    }
}
