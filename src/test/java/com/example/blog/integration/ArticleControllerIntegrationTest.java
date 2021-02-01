package com.example.blog.integration;

import com.example.blog.BlogApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = BlogApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAllPublicArticles() {
        assertTrue(
                restTemplate
                        .getForObject("http://localhost:" + port + "/articles/allPublicArticles", List.class)
                        .size() > 0);
    }
}
