package com.edstem.blogapp.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSummaryResponse {
    private Long id;
    private String title;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private List<String> categories;
}
