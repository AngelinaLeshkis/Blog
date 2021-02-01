package com.example.blog.integration;

import com.example.blog.BlogApplication;
import com.example.blog.dto.CommentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = BlogApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentControllerTest {

    @LocalServerPort
    private int port;

    private Long articleId;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAddComment() {
        CommentDTO commentDTO = new CommentDTO("Bad", 7L, 5L);
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/articles/" + articleId +"/comments",
                        commentDTO, String.class, 7L);
        assertEquals(403, responseEntity.getStatusCodeValue());
    }
}
