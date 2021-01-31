package com.example.blog.controller;

import com.example.blog.dto.CommentDTO;
import com.example.blog.exception.BusinessException;
import com.example.blog.service.CommentService;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<String> saveArticle(@Valid @RequestBody CommentDTO commentDTO,
                                              @PathVariable(value = "articleId") Long articleId) {
        Long userId = userService.getLoggedInUserId();
        try {
            CommentDTO.fromComment(commentService.saveComment(commentDTO, articleId, userId));
            return new ResponseEntity<>("Comment saved successfully", HttpStatus.OK);
        } catch (BusinessException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "/{articleId}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "id") Long id) {
        try {
            CommentDTO savedComment = commentService.getCommentByCommentId(id);
            return new ResponseEntity<>(savedComment, HttpStatus.OK);
        } catch (BusinessException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "/{articleId}/comments")
    public List<CommentDTO> getCommentByArticleId(@PathVariable(value = "articleId")
                                                                          Long articleId) {
        return commentService.getCommentsByArticleId(articleId);
    }

    @DeleteMapping(value = "/{articleId}/comments/{id}")
    public ResponseEntity<Object> deleteArticle(@PathVariable(name = "articleId") Long articleId,
                                                @PathVariable(name = "id") Long id) {
        Long userId = userService.getLoggedInUserId();
        try {
            if (commentService.deleteComment(id, userId, articleId)) {
                return new ResponseEntity<>("Comment was deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Comment can`t be deleted", HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (BusinessException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "/comments")
    public Page<CommentDTO> getAllArticles(
            @RequestParam(defaultValue = "0") Integer skip,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order) {

        return commentService.getCommentPage(skip, limit, sort, order)
                .map(CommentDTO::fromComment);
    }
}
