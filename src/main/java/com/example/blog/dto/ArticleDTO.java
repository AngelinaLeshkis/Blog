package com.example.blog.dto;

import com.example.blog.entity.*;

import java.util.List;
import java.util.Set;

public class ArticleDTO {

    private Long id;
    private String title;
    private String text;
    private Status status;
    private Long userId;
    private Set<Tag> tags;
    private List<Comment> comments;

    public ArticleDTO() {
    }

    public ArticleDTO(Long id, String title, String text, Status status, Long userId, Set<Tag> tags) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.status = status;
        this.userId = userId;
        this.tags = tags;
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
}
