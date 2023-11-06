package com.edstem.blogapp.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edstem.blogapp.contract.request.PostRequest;
import com.edstem.blogapp.contract.response.PostResponse;
import com.edstem.blogapp.model.Post;
import com.edstem.blogapp.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;


    @Test
    void testGetAllPosts() throws Exception {

        List<Post> posts = new ArrayList<>();
        List<String> categories = Arrays.asList("Test Category1", "Test Category2");

        posts.add(new Post(1L, "Test Title1", "Test Content1", categories, LocalDate.now()));
        posts.add(new Post(2L, "Test Title2", "Test Content2", categories, LocalDate.now()));

        when(postService.getAllPosts()).thenReturn(posts);

        mockMvc.perform(get("/blog/post"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(posts)));
    }
}
