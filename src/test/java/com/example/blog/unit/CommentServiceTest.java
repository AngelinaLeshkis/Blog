package com.example.blog.unit;

import com.example.blog.dto.CommentDTO;
import com.example.blog.persistence.CommentRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void saveComment() {
        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setMessage("Message");
        commentDTO.setUserId(8L);
        commentDTO.setArticleId(7L);

        CommentDTO savedComment = commentRepository.findById(10L)
                .map(CommentDTO::fromComment)
                .orElse(null);

        Assert.assertNotNull(savedComment);
        Assert.assertEquals(savedComment.getMessage(), "Nice");
    }
}
