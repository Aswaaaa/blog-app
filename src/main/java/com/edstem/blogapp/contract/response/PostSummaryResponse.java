package com.edstem.blogapp.contract.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostSummaryResponse {
    private Long id;
    private String title;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private List<String> categories;
}
