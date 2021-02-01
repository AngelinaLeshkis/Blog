package com.example.blog.service;

import com.example.blog.dto.TagDTO;

import java.util.List;
import java.util.Map;

public interface TagService {

    Map<String, Integer> getArticlesCountByTag(String tagName);

    List<TagDTO> getTagsCloud();
}
