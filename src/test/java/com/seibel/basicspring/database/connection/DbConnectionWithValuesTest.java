package com.seibel.basicspring.database.connection;

import com.seibel.basicspring.BasicspringApplicationTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootTest(classes = BasicspringApplicationTests.class)
@ActiveProfiles("test-database")
@TestPropertySource(properties = {
        "BASICSPRING_USERNAME=[username]",
        "BASICSPRING_PASSWORD=[password]"
})
class DbConnectionWithValuesTest {
    @Autowired
    DataSource dataSource;

//    @Test
    void testConnection() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("✅ Connected to DB: " + conn.getMetaData().getURL());
        }
    }
}
