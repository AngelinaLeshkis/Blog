package com.example.blog.service;

import com.example.blog.dto.ArticleDTO;
import com.example.blog.entity.Article;
import com.example.blog.entity.Tag;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface ArticleService {

    void saveArticle(ArticleDTO articleDTO, Long userId);

    void updateArticle(Long id, ArticleDTO articleDTO, Long userId);

    List<ArticleDTO> getAllPublicArticles();

    List<ArticleDTO> getArticlesByTag(List<String> tagNames);

    List<ArticleDTO> getAllPublicArticlesByUserId(Long id);

    List<ArticleDTO> getAllArticlesByUserId(Long id);

    List<ArticleDTO> getArticlesByTitle(String title);

    boolean deleteArticle(Long id, Long userId);

    Page<Article> getArticlePage(Integer skip, Integer limit, String fieldName, String order);
}
