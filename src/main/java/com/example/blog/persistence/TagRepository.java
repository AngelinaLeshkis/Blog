package com.example.blog.persistence;

import com.example.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);

//    @Query(value = "SELECT COUNT ", nativeQuery = true)
//    Long findArticlesCountByName(String name);

}
