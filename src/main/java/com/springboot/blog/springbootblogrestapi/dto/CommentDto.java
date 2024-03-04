package com.springboot.blog.springbootblogrestapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        description = "CommentDto Model Information"
)
public class CommentDto {
    private Long id;
    //name should not be null or empty
    @NotEmpty(message = "Name should not be null or empty")
    @Schema(
            description = "Comment name"
    )
    private String name;

    @NotEmpty(message = "Email should not be null or empty")
    @Email
    @Schema(
            description = "email of the commenter"
    )
    private String email;

    @NotEmpty(message = "Comment body should not be null or empty")
    @Size(min=10,message = "Comment body must contains at least 10 characters")
    @Schema(
            description = "Comment's body"
    )
    private String body;
}
