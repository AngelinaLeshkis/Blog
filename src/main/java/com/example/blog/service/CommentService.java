package com.example.blog.service;

import com.example.blog.dto.CommentDTO;
import com.example.blog.entity.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {

    Comment saveComment(CommentDTO comment, Long articleId, Long userId);

    List<CommentDTO> getCommentsByArticleId(Long id);

    boolean deleteComment(Long id, Long userId, Long articleId);

    CommentDTO getCommentByCommentId(Long id);

    Page<Comment> getCommentPage(Integer skip, Integer limit, String fieldName, String order);
}
