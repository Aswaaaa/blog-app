package com.edstem.blogapp.repository;

import com.edstem.blogapp.model.Post;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE lower(p.title) LIKE concat('%', :query, '%') OR lower(p.content) LIKE concat('%', :query, '%') OR lower(p.codeSnippet) LIKE concat('%', :query, '%')")
    Page<Post> searchAllFields(@Param("query") String query, Pageable pageable);




    @Override
    List<Post> findAll();

    Post findById(long id);

    List<Post> findAllByCategoriesIn(List<String> categories);
}
