package com.example.blog.controller;

import com.example.blog.dto.ArticleDTO;
import com.example.blog.entity.Article;
import com.example.blog.service.ArticleService;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    @Autowired
    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @PostMapping(value = "/addArticle")
    public ResponseEntity<Article> saveArticle(@Valid @RequestBody ArticleDTO articleDTO) {
        Long userId = userService.getLoggedInUserId();
        return new ResponseEntity<>(articleService.saveArticle(articleDTO, userId), HttpStatus.OK);
    }

    @GetMapping(value = "/allPublicArticles")
    public List<ArticleDTO> getAllPublicArticles() {
        return articleService.getAllPublicArticles();
    }

    @GetMapping(value = "/allPublicArticles/{id}")
    public List<ArticleDTO> getAllPublicArticlesById(@PathVariable(name = "id") Long id) {
        return articleService.getAllPublicArticlesByUserId(id);
    }

    @GetMapping(value = "/allUserArticles")
    public List<ArticleDTO> getAllArticlesById() {
        Long userId = userService.getLoggedInUserId();
        return articleService.getAllArticlesByUserId(userId);
    }

    @PutMapping(value = "/updateArticle/{id}")
    public ResponseEntity<Article> updateArticle(@Valid @RequestBody ArticleDTO articleDTO,
                                                 @PathVariable (name = "id") Long id) {
        Long userId = userService.getLoggedInUserId();

        return new ResponseEntity<>(articleService.updateArticle(id, articleDTO, userId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteArticle/{id}")
    public ResponseEntity<Object> deleteArticle(@PathVariable (name = "id") Long id) {
        Long userId = userService.getLoggedInUserId();

        if (articleService.deleteArticle(id, userId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(value = "")
    public List<ArticleDTO> getArticlesByTags(@RequestParam List<String> tags) {
        return articleService.getArticlesByTag(tags);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<ArticleDTO>> getAllArticles(
            @RequestParam(defaultValue = "0") Integer skip,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String order)
    {

        Page<ArticleDTO> articlePage = articleService.getArticlePage(skip, limit, sort, order)
                .map(ArticleDTO::fromArticle);

        return new ResponseEntity<>(articlePage, HttpStatus.OK);
    }

}
