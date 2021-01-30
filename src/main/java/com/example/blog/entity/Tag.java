package com.example.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tag", catalog = "blogdb")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Article> articles;

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }
}
