package com.seibel.basicspring.database.connection;

import com.seibel.basicspring.BasicspringApplicationTests;
import org.springframework.core.env.Environment; // ✅ Correct import (not Hibernate's)
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootTest(classes = BasicspringApplicationTests.class)
@ActiveProfiles("test-database")
class DbConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment env; // Spring Environment

    @Test
    void printSpringEnvValues() {
        System.out.println("Spring property BASICSPRING_USERNAME = " + env.getProperty("BASICSPRING_USERNAME"));
        System.out.println("Spring property BASICSPRING_PASSWORD = " + env.getProperty("BASICSPRING_PASSWORD"));
    }

    @Test
    void printEnvValues() {
        System.out.println("System ENV username: " + System.getenv("BASICSPRING_USERNAME"));
        System.out.println("System ENV password: " + System.getenv("BASICSPRING_PASSWORD"));
    }

    @Test
    void testConnection() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("✅ Connected to DB: " + conn.getMetaData().getURL());
        }
    }
}
