package com.springboot.blog.springbootblogrestapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "JwtAuth Response Model Information"
)
public class JWTAuthResponse {
    @Schema(
            description = "Bearer Access Token for LogIn into protected resource"
    )
    private String accessToken;
    private String tokenType="Bearer";
}
