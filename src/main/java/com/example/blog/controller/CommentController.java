package com.example.blog.controller;

import com.example.blog.dto.CommentDTO;
import com.example.blog.service.CommentService;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/articles")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping(value = "/{articleId}/comments")
    public ResponseEntity<CommentDTO> saveArticle(@RequestBody CommentDTO commentDTO,
                                                  @PathVariable(value = "articleId") Long articleId) {
        Long userId = userService.getLoggedInUserId();
        return new ResponseEntity<>(CommentDTO.fromComment(commentService.saveComment(commentDTO, articleId, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{articleId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(commentService.getCommentByCommentId(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{articleId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentByArticleId(@PathVariable(value = "articleId")
                                                                              Long articleId) {
        return new ResponseEntity<>(commentService.getCommentsByArticleId(articleId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{articleId}/comments/{id}")
    public ResponseEntity<Object> deleteArticle(@PathVariable (name = "articleId") Long articleId,
                                                @PathVariable (name = "id") Long id) {
        Long userId = userService.getLoggedInUserId();

        if (commentService.deleteComment(id, userId, articleId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
