package com.example.blog.persistence;

import com.example.blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByUserId(Long id);

    @Query(value = "SELECT user_id FROM article WHERE id = ?", nativeQuery = true)
    Long findUserIdById(Long id);

    List<Article> findAllByTitleContains(String title);
}
