package com.example.blog.serviceimpl;

import com.example.blog.dto.ArticleDTO;
import com.example.blog.entity.Article;
import com.example.blog.entity.Tag;
import com.example.blog.entity.User;
import com.example.blog.persistence.ArticleRepository;
import com.example.blog.persistence.TagRepository;
import com.example.blog.persistence.UserRepository;
import com.example.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, UserRepository userRepository,
                              TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Article saveArticle(ArticleDTO articleDTO, Long userId) {
        User authorizedUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        if (authorizedUser == null) {
            return null;
        }

        setExistedTagsFromDB(articleDTO);

        return articleRepository.save(articleDTO.toArticle(authorizedUser));
    }

    @Override
    public Article updateArticle(Long id, ArticleDTO articleDTO, Long userId) {

        User userFromDB = userRepository.findById(articleRepository.findUserIdById(id))
                .orElseThrow(() -> new RuntimeException("User not found"));

        User authorizedUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        if (!userFromDB.equals(authorizedUser)) {
            return null;
        }

        setExistedTagsFromDB(articleDTO);
        articleDTO.setId(id);

        return articleRepository.save(articleDTO.toArticle(authorizedUser));
    }

    @Override
    public List<ArticleDTO> getAllPublicArticles() {
        return articleRepository.findAll().stream()
                .filter(article -> "PUBLIC".equals(article.getStatus().getValueOfStatus()))
                .map(ArticleDTO::fromArticle)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> getAllPublicArticlesByUserId(Long id) {
        return articleRepository.findAllByUserId(id).stream()
                .filter(article -> "PUBLIC".equals(article.getStatus().getValueOfStatus()))
                .map(ArticleDTO::fromArticle)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> getArticlesByTag(List<String> tagNames) {
        Set<Article> articlesByTags = new HashSet<>();
        for (String tagName : tagNames) {
            Tag tag = tagRepository.findByName(tagName);
            if (tag == null) {
                break;
            } else {
                articlesByTags.addAll(tag.getArticles().stream()
                        .filter(article -> "PUBLIC".equals(article.getStatus().getValueOfStatus()))
                        .collect(Collectors.toSet()));
            }
        }
        return articlesByTags.stream()
                .map(ArticleDTO::fromArticle)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteArticle(Long id, Long userId) {
        User userFromDB = userRepository.findById(articleRepository.findUserIdById(id))
                .orElseThrow(() -> new RuntimeException("User not found"));

        User authorizedUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        if (userFromDB.equals(authorizedUser)) {

            articleRepository.delete(articleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Article not found with id " + id)));

            return true;
        }

        return false;
    }

    @Override
    public List<ArticleDTO> getAllArticlesByUserId(Long id) {
        return articleRepository.findAllByUserId(id).stream()
                .map(ArticleDTO::fromArticle)
                .collect(Collectors.toList());
    }

    private void setExistedTagsFromDB(ArticleDTO articleDTO) {
        for (Tag tag : articleDTO.getTags()) {
            Tag existedTag = tagRepository.findByName(tag.getName());
            if (existedTag != null) {
                tag.setId(existedTag.getId());
            } else {
                tagRepository.save(tag);
            }
        }
    }

    @Override
    public Page<Article> getArticlePage(Integer skip, Integer limit, String fieldName, String order) {
        Sort resultSort = Sort.by(getSortOrder(order, fieldName));
        Pageable pageInfo = PageRequest.of(skip, limit, resultSort);
        return articleRepository.findAll(pageInfo);
    }

    private Sort.Order getSortOrder(String order, String fieldName) {
        if ("asc".equalsIgnoreCase(order)) {
            return Sort.Order.asc(fieldName);
        } else if ("desc".equalsIgnoreCase(order)) {
            return Sort.Order.desc(fieldName);
        }
        throw new RuntimeException("The order = " + order + " is invalid.");
    }
}
