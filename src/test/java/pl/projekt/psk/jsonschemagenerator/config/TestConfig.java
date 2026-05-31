package pl.projekt.psk.jsonschemagenerator.config;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.mongodb.MongoDBContainer;

public class TestConfig {
    static final MongoDBContainer mongo = new MongoDBContainer("mongo:latest");

    static {
        mongo.start();
    }
}
