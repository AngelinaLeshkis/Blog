package com.example.blog.persistence;

import com.example.blog.entity.Article;
import com.example.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByUserId(Long id);

    @Query(value = "SELECT user_id FROM article WHERE id = ?", nativeQuery = true)
    Long findUserIdById(Long id);

    List<Article> findAllByTitleContains(String title);
}
