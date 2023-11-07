package com.edstem.blogapp.repository;

import com.edstem.blogapp.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Override
    List<Post> findAll();

    Post findById(long id);

    List<Post> findAllByCategoriesIn(List<String> categories);
}
