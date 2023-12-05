package com.edstem.blogapp.contract.response;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PostResponse {
    private long id;
    private String title;
    private String content;
    private List<String> categories;
    private String codeSnippet;
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedTime;
}
