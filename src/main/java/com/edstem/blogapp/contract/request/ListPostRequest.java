package com.edstem.blogapp.contract.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListPostRequest {
    private int pageNumber;
    private int pageSize;
}
