package com.edstem.blogapp.repository;

import com.edstem.blogapp.contract.request.PostSummaryRequest;
import com.edstem.blogapp.model.post.Post;
import com.edstem.blogapp.model.post.PostStatus;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
            "SELECT p FROM Post p WHERE "
                    + "LOWER(p.title) LIKE LOWER(CONCAT('%',:query, '%'))"
                    + "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :query, '%'))"
                    + "OR LOWER(p.codeSnippet) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Post> searchPosts(String query, Sort sort);

    @Override
    List<Post> findAll();

    Post findById(long id);

    @Query(
            "SELECT p.id AS id, p.title AS title, p.categories AS categories, p.createdTime AS"
                    + " createdTime FROM Post p WHERE p.status <> ?1")
    List<PostSummaryRequest> findAllByStatusNot(PostStatus status, Pageable page);

    @Query("SELECT COUNT(p) FROM Post p WHERE p.status <> ?1")
    long countByStatusNot(PostStatus status);
}
