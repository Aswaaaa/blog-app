package com.edstem.blogapp.repository;

import com.edstem.blogapp.model.post.Post;
import com.edstem.blogapp.model.post.PostStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE " +
            "p.title LIKE CONCAT('%',:query, '%')" +
            "Or p.content LIKE CONCAT('%', :query, '%')" + "Or p.codeSnippet LIKE CONCAT('%', :query, '%')")
    List<Post> searchPosts(String query, Sort sort);

    @Override
    List<Post> findAll();

    Post findById(long id);


    @Query("SELECT p FROM Post p WHERE p.status <> ?1")
    List<Post> findAllByStatusNot(PostStatus status, Pageable page);

    @Query("SELECT COUNT(p) FROM Post p WHERE p.status <> ?1")
    long countByStatusNot(PostStatus status);

}
