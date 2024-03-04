package com.springboot.blog.springbootblogrestapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "SignUpDto Model Information"
)
public class SignUpDto {
    @Schema(
            description = "Name of the user"
    )
    private String name;
    @Schema(
            description = "Username of the user"
    )
    private String username;
    @Schema(
            description = "Email of the user"
    )
    private String email;
    @Schema(
            description = "Password of the user"
    )
    private  String password;
}
