package com.springboot.blog.springbootblogrestapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;
@Data
@Schema(
        description = "PostDto Model Information"
)
public class PostDtoV2 {
    private Long id;

    //title should not be empty or null
    //title should have at least two character
    @NotEmpty
    @Size(min=2,message = "Post title must have at least 2 characters")
    @Schema(
            description = "Blog Post Title"
    )
    private String title;
    //description should not be empty or null
    //description should have at least 2 character
    @NotEmpty
    @Size(min=10,message = "Post description must have at least 10 characters")
    @Schema(
            description = "Blog Post Description"
    )
    private String description;

    //should not be null or empty
    @NotEmpty
    @Schema(
            description = "Blog Post Content"
    )
    private String content;

    //To capture comments along with posts on response.
    //This class is basically for request and response so here I am taking all the
    //commentDto in Hashset as it do not allow duplicates, it will show post & it's all comments.
    @Schema(
            description = "Blog Post associated comments"
    )
    private Set<CommentDto> comments;

    @Schema(
            description = "Blog Post falling in which Category by ID"
    )
    private Long categoryId;

    private List<String>tags;

}
