package com.pragma.loans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableConfigurationProperties
public class LoansApplication {
    private final ApplicationProperties applicationProperties;

    public LoansApplication(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        validateProperties();
    }

    public static void main(String[] args) {
        SpringApplication.run(LoansApplication.class, args);
    }

    @Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(applicationProperties.getCorePoolSize());
        executor.setMaxPoolSize(applicationProperties.getMaxPoolSize());
        executor.setQueueCapacity(applicationProperties.getQueueCapacity());
        executor.setThreadNamePrefix("loan-async-");
        executor.initialize();
        return executor;
    }

    private void validateProperties() {
        if (applicationProperties.getCorePoolSize() <= 0) {
            throw new IllegalStateException("corePoolSize debe ser mayor que 0");
        }
        if (applicationProperties.getMaxPoolSize() < applicationProperties.getCorePoolSize()) {
            throw new IllegalStateException("maxPoolSize debe ser mayor o igual que corePoolSize");
        }
        if (applicationProperties.getQueueCapacity() <= 0) {
            throw new IllegalStateException("queueCapacity debe ser mayor que 0");
        }
        if (applicationProperties.getDb().getUrl() == null || applicationProperties.getDb().getUrl().isBlank()) {
            throw new IllegalStateException("La URL de la base de datos no puede estar vacía");
        }
    }
}

record ApplicationProperties(
    DbProperties db,
    int corePoolSize,
    int maxPoolSize,
    int queueCapacity
) {}

record DbProperties(
    String url,
    String username,
    String password,
    String driverClassName,
    int maxPoolSize,
    int minIdle,
    long connectionTimeout,
    long idleTimeout,
    long maxLifetime
) {}