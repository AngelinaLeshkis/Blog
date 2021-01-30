package com.example.blog.dto;

import com.example.blog.entity.Tag;

public class TagDTO {

    private String tag;
    private Integer postCount;

    public static TagDTO fromTag(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setTag(tag.getName());
        tagDTO.setPostCount(tag.getArticles().size());

        return tagDTO;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }
}
