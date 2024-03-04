package com.springboot.blog.springbootblogrestapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "PostResponseDto Model Information"
)
public class PostResponse {
    private List<PostDto> content;
    private int pageNo;
    @Schema(
            description = "Page Size of a page in the response"
    )
    private int pageSize;
    @Schema(
            description = "Total entries present inside a page in the response"
    )
    private Long totalElements;
    @Schema(
            description = "Total number of pages exist in the response"
    )
    private int totalPages;
    @Schema(
            description = "isLastPage=true/else:false"
    )
    private boolean last;


}
//we wanted to provide all these fields in the response of the Pagination Rest Api, i.e.
// Get all post by ID
