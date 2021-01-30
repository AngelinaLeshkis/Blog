package com.example.blog.controller;

import com.example.blog.dto.TagDTO;
import com.example.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Map<String, Integer> getArticlesCountByTag(@RequestParam(value = "tag") String tag) {
        return tagService.getArticlesCountByTag(tag);
    }

    @GetMapping(value = "/tags-cloud")
    public List<TagDTO> getTagsCloud() {
        return tagService.getTagsCloud();
    }

}
