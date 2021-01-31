package com.example.blog.controller;

import com.example.blog.dto.TagDTO;
import com.example.blog.exception.BusinessException;
import com.example.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = "/tags/tags-cloud")
    public ResponseEntity<Map<String, Integer>> getArticlesCountByTag(@RequestParam(value = "tag") String tag) {
        try {
            Map<String, Integer> articlesCount = tagService.getArticlesCountByTag(tag);
            return new ResponseEntity<>(articlesCount, HttpStatus.OK);
        } catch (BusinessException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/tags-cloud")
    public List<TagDTO> getTagsCloud() {
        return tagService.getTagsCloud();
    }

}
