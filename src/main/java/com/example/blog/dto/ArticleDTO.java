package com.example.blog.dto;

import com.example.blog.entity.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class ArticleDTO {

    private Long id;
    private String title;
    private String text;
    private Status status;
    private Date createdAt;
    private Date updatedAt;
    private Long userId;
    private Set<Tag> tags;
    private List<Comment> comments;

    public ArticleDTO() {
    }

    public ArticleDTO(Long id, String title, String text, Status status, Long userId, Set<Tag> tags,
                      Date createdAt, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.status = status;
        this.userId = userId;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Article toArticle(User user) {
        Article article = new Article();
        article.setId(id);
        article.setTitle(title);
        article.setText(text);
        article.setStatus(status);
        article.setUser(user);
        article.setTags(tags);

        return article;
    }

    public static ArticleDTO fromArticle(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setText(article.getText());
        articleDTO.setStatus(article.getStatus());
        articleDTO.setUserId(article.getUser().getId());
        articleDTO.setTags(article.getTags());
        articleDTO.setComments(article.getComments());
        articleDTO.setCreatedAt(article.getCreatedAt());
        articleDTO.setUpdatedAt(article.getUpdatedAt());

        return articleDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
