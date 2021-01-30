package com.example.blog.serviceimpl;

import com.example.blog.dto.CommentDTO;
import com.example.blog.entity.Article;
import com.example.blog.entity.Comment;
import com.example.blog.entity.User;
import com.example.blog.persistence.ArticleRepository;
import com.example.blog.persistence.CommentRepository;
import com.example.blog.persistence.UserRepository;
import com.example.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ArticleRepository articleRepository,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment saveComment(CommentDTO commentDTO, Long articleId, Long userId) {

        User authorizedUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException(("Article not found with id" + articleId)));


        return commentRepository.save(commentDTO.toComment(article, authorizedUser));
    }

    @Override
    public List<CommentDTO> getCommentsByArticleId(Long id) {
        return commentRepository.findAllByArticleId(id).stream()
                .map(CommentDTO::fromComment)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteComment(Long id, Long userId, Long articleId) {
        User articleOwner = userRepository.findById(articleRepository.findUserIdById(articleId))
                .orElseThrow(() -> new RuntimeException("User not found"));

        User authorizedUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        User commentOwner = userRepository.findById(commentRepository.findUserIdById(id))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (articleOwner.equals(authorizedUser) || commentOwner.equals(authorizedUser)) {
            commentRepository.delete(commentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Comment not found with id " + id)));

            return true;
        }

        return false;
    }

    @Override
    public CommentDTO getCommentByCommentId(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id" + id));
        return CommentDTO.fromComment(comment);
    }
}
