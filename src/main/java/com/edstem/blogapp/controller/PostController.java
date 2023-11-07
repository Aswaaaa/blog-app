package com.edstem.blogapp.controller;

import com.edstem.blogapp.contract.request.PostRequest;
import com.edstem.blogapp.contract.response.PostResponse;
import com.edstem.blogapp.model.Post;
import com.edstem.blogapp.repository.PostRepository;
import com.edstem.blogapp.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog/post")
@RequiredArgsConstructor
public class PostController {
    private  final PostService postService;

    @PostMapping("/create")
    public PostResponse createPost(@Valid @RequestBody PostRequest request) {
        return this.postService.createPost(request);
    }
    @GetMapping
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }
    @PutMapping("/update/{id}")
    public PostResponse updatePostById(@Valid @PathVariable Long id,@RequestBody PostRequest request){
        return postService.updatePostById(id,request);
    }
    @DeleteMapping("/{id}")
    public void deletePostById(@PathVariable Long id){
        postService.deletePostById(id);
    }
    @GetMapping("/categories/{category}")
    public List<PostResponse> getPostsByCategory(@PathVariable String category) {
        return postService.getPostsByCategory(category);
    }
@GetMapping("{id}")
    public PostResponse getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
}







}
