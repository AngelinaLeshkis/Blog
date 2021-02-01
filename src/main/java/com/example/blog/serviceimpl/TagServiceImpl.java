package com.example.blog.serviceimpl;

import com.example.blog.dto.TagDTO;
import com.example.blog.entity.Tag;
import com.example.blog.exception.BusinessException;
import com.example.blog.persistence.TagRepository;
import com.example.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    @Override
    public Map<String, Integer> getArticlesCountByTag(String tagName) {
        Map<String, Integer> tagWithArticleCount = new HashMap<>();
        Tag tagFromDB = tagRepository.findByName(tagName);

        if (tagFromDB == null) {
            throw new BusinessException("Tag not found");
        }

        tagWithArticleCount.put(tagName, tagFromDB.getArticles().size());

        return tagWithArticleCount;
    }

    @Override
    public List<TagDTO> getTagsCloud() {
        return tagRepository.findAll().stream()
                .map(TagDTO::fromTag)
                .collect(Collectors.toList());
    }
}
