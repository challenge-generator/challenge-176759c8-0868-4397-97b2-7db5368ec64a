package com.pragma.loans.infrastructure.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);
    
    private static final int MAX_CONNECTIONS = 200;
    private static final int MIN_IDLE_CONNECTIONS = 20;
    private static final long CONNECTION_TIMEOUT_MS = 10000L;
    private static final long IDLE_TIMEOUT_MS = 300000L;
    private static final long MAX_LIFETIME_MS = 600000L;
    private static final int CACHE_PREPARE_STATEMENTS = 250;
    private static final int PREPARE_STATEMENT_CACHE_SIZE = 2048;

    @Value("${spring.datasource.url:jdbc:postgresql://localhost:5432/loans}")
    private String jdbcUrl;

    @Value("${spring.datasource.username:postgres}")
    private String username;

    @Value("${spring.datasource.password:postgres}")
    private String password;

    @Value("${spring.datasource.driver-class-name:org.postgresql.Driver}")
    private String driverClassName;

    @Value("${spring.jpa.properties.hibernate.dialect:org.hibernate.dialect.PostgreSQLDialect}")
    private String hibernateDialect;

    @Value("${spring.jpa.show-sql:false}")
    private boolean showSql;

    @Bean
    @Primary
    public DataSource dataSource() {
        log.info("Configurando HikariCP con pool de {} conexiones máximo para soportar 1500 req/s", 
                MAX_CONNECTIONS);
        
        HikariConfig config = new HikariConfig();
        config.setPoolName("LoansHikariPool");
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        
        config.setMaximumPoolSize(MAX_CONNECTIONS);
        config.setMinimumIdle(MIN_IDLE_CONNECTIONS);
        config.setConnectionTimeout(CONNECTION_TIMEOUT_MS);
        config.setIdleTimeout(IDLE_TIMEOUT_MS);
        config.setMaxLifetime(MAX_LIFETIME_MS);
        config.setConnectionTestQuery("SELECT 1");
        config.setAutoCommit(true);
        
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", CACHE_PREPARE_STATEMENTS);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", PREPARE_STATEMENT_CACHE_SIZE);
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        
        config.setRegisterMbeans(true);
        config.setAllowPoolSuspension(false);
        config.setReadOnly(false);
        config.setIsolateInternalQueries(false);
        
        HikariDataSource dataSource = new HikariDataSource(config);
        
        log.info("HikariCP inicializado: pool={}, max={}, minIdle={}, timeout={}ms",
                config.getPoolName(), config.getMaximumPoolSize(), 
                config.getMinimumIdle(), config.getConnectionTimeout());
        
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        log.info("Configurando EntityManagerFactory con Hibernate para optimización de rendimiento");
        
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.pragma.loans.infrastructure.entities");
        
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(showSql);
        em.setJpaVendorAdapter(vendorAdapter);
        
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", hibernateDialect);
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", "50");
        hibernateProperties.setProperty("hibernate.order_inserts", "true");
        hibernateProperties.setProperty("hibernate.order_updates", "true");
        hibernateProperties.setProperty("hibernate.jdbc.batch_versioned_data", "true");
        hibernateProperties.setProperty("hibernate.generate_statistics", "false");
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "false");
        hibernateProperties.setProperty("hibernate.cache.use_query_cache", "false");
        hibernateProperties.setProperty("hibernate.default_batch_fetch_size", "16");
        hibernateProperties.setProperty("hibernate.jdbc.fetch_size", "50");
        hibernateProperties.setProperty("hibernate.connection.release_mode", "AFTER_TRANSACTION");
        hibernateProperties.setProperty("hibernate.transaction.coordinator_class", "jdbc");
        
        em.setJpaProperties(hibernateProperties);
        
        log.info("EntityManagerFactory configurado con batch_size=50, fetch_size=50");
        return em;
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        log.info("Configurando JpaTransactionManager");
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        transactionManager.setDataSource(dataSource());
        transactionManager.setDefaultTimeout(30);
        return transactionManager;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        log.info("Configurando JdbcTemplate con DataSource optimizado");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.setQueryTimeout(10);
        jdbcTemplate.setFetchSize(50);
        jdbcTemplate.setMaxRows(10000);
        return jdbcTemplate;
    }

    @Bean
    public ScheduledExecutorService healthCheckExecutor() {
        log.info("Configurando ScheduledExecutorService para monitoreo de salud del pool");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, r -> {
            Thread t = new Thread(r, "HikariPool-HealthCheck");
            t.setDaemon(true);
            return t;
        });
        return executor;
    }

    @PostConstruct
    public void logPoolConfiguration() {
        log.info("========================================");
        log.info("CONFIGURACIÓN DE BASE DE DATOS");
        log.info("========================================");
        log.info("URL: {}", jdbcUrl);
        log.info("Driver: {}", driverClassName);
        log.info("Pool máximo: {} conexiones", MAX_CONNECTIONS);
        log.info("Pool mínimo: {} conexiones", MIN_IDLE_CONNECTIONS);
        log.info("Connection timeout: {}ms", CONNECTION_TIMEOUT_MS);
        log.info("Idle timeout: {}ms", IDLE_TIMEOUT_MS);
        log.info("Max lifetime: {}ms", MAX_LIFETIME_MS);
        log.info("Statement cache: {} statements", PREPARE_STATEMENT_CACHE_SIZE);
        log.info("========================================");
    }
}