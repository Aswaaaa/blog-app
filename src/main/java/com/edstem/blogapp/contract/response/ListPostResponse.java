package com.edstem.blogapp.contract.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListPostResponse {
    private List<PostSummaryResponse> posts;
    private Long totalPosts;
}
