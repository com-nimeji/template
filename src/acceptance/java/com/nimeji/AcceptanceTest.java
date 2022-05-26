package com.nimeji;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.images.PullPolicy;

public class AcceptanceTest {
    private static final Network network = Network.newNetwork();
    private static final PostgreSQLContainer dbContainer = new PostgreSQLContainer<>("postgres:latest")
            .withNetwork(network)
            .withNetworkAliases("db")
            .withInitScript("init.sql");
    private static final GenericContainer appContainer = new GenericContainer<>("com.nimeji/todo:accetance-test")
            .dependsOn(dbContainer)
            .withNetwork(network)
            .withExposedPorts(8080)
            .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger(AcceptanceTest.class)))
            .withImagePullPolicy(PullPolicy.defaultPolicy());

    protected static Integer PORT;

    @BeforeAll
    static void setup() {
        dbContainer.start();
        appContainer.addEnv("DB_URL", "db/license");
        appContainer.addEnv("DB_USER", dbContainer.getUsername());
        appContainer.addEnv("DB_PASS", dbContainer.getPassword());
        appContainer.start();
        PORT = appContainer.getFirstMappedPort();
    }

    @AfterAll
    static void teardown() {
        appContainer.stop();
        dbContainer.stop();
    }
}
