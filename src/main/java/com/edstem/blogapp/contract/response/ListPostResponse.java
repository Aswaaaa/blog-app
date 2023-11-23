package com.edstem.blogapp.contract.response;

import com.edstem.blogapp.contract.request.PostSummaryRequest;
import com.edstem.blogapp.model.post.Post;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListPostResponse {
    private List<Post> posts;
    private Long totalPosts;
}
