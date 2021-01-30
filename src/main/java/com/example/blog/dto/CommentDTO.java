package com.example.blog.dto;

import com.example.blog.entity.Article;
import com.example.blog.entity.Comment;
import com.example.blog.entity.User;

import java.util.Date;

public class CommentDTO {

    private Long id;
    private String message;
    private String firstName;
    private String lastName;
    private Long articleId;
    private Long userId;
    private Date createdAt;

    public CommentDTO() {

    }

    public CommentDTO(Long id, String message,  String firstName,Date createdAt,
                      String lastName, Long articleId, Long userId) {
        this.id = id;
        this.message = message;
        this.firstName = firstName;
        this.lastName = lastName;
        this.articleId = articleId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public CommentDTO(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Comment toComment(Article article, User user) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setMessage(message);
        comment.setArticle(article);
        comment.setUser(user);

        return comment;
    }

    public static CommentDTO fromComment(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setMessage(comment.getMessage());
        commentDTO.setArticleId(comment.getArticle().getId());
        commentDTO.setUserId(comment.getUser().getId());
        commentDTO.setFirstName(comment.getUser().getFirstName());
        commentDTO.setLastName(comment.getUser().getLastName());
        commentDTO.setCreatedAt(comment.getCreatedAt());

        return commentDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
