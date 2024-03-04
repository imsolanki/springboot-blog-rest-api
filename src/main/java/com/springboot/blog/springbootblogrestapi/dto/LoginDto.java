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
        description = "LoginDto Model Information"
)
public class LoginDto {

    @Schema(
            description = "Required Username or Email"
    )
    private String usernameOrEmail;
    @Schema(
            description = "Password"
    )
    private String password;

}
