package com.example.blog.controller;

import com.example.blog.dto.CommentDTO;
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
    public ResponseEntity<CommentDTO> saveArticle(@Valid @RequestBody CommentDTO commentDTO,
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

    @GetMapping(value = "/comments")
    public ResponseEntity<Page<CommentDTO>> getAllArticles(
            @RequestParam(defaultValue = "0") Integer skip,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order)
    {

        Page<CommentDTO> commentPage = commentService.getCommentPage(skip, limit, sort, order)
                .map(CommentDTO::fromComment);

        return new ResponseEntity<>(commentPage, HttpStatus.OK);
    }
}
