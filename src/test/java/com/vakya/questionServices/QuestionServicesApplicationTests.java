package com.vakya.questionServices;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class QuestionServicesApplicationTests {

    @Test
    void contextLoads() {
        // This just checks if the Spring context loads successfully
    }
}
