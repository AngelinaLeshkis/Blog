package com.example.blog.persistence;

import com.example.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByArticleId(Long id);

    @Query(value = "SELECT user_id FROM comment WHERE id = ?", nativeQuery = true)
    Long findUserIdById(Long id);
}
