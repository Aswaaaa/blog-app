package com.edstem.blogapp.contract.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListPostResponse {
    private List<PostSummaryResponse> posts;
    private Long totalPosts;
}
