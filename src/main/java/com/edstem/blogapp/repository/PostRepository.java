package com.edstem.blogapp.repository;

import com.edstem.blogapp.model.post.Post;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
            "SELECT p FROM Post p WHERE "
                    + "LOWER(p.title) LIKE LOWER(CONCAT('%',:query, '%'))"
                    + "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :query, '%'))"
                    + "OR LOWER(p.codeSnippet) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Post> searchPosts(String query, Sort sort);


    @Query("SELECT p FROM Post p WHERE :category MEMBER OF p.categories")
    List<Post> findByCategoriesContaining(@Param("category") String category);
}
