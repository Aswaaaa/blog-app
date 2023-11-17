package com.edstem.blogapp.repository;

import com.edstem.blogapp.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE " +
            "p.title LIKE CONCAT('%',:query, '%')" +
            "Or p.content LIKE CONCAT('%', :query, '%')" + "Or p.codeSnippet LIKE CONCAT('%', :query, '%')")
    List<Post> searchPosts(String query);

    @Override
    List<Post> findAll();

    Post findById(long id);

    List<Post> findAllByCategoriesIn(List<String> categories);
}
