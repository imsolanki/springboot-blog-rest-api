package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.dto.JWTAuthResponse;
import com.springboot.blog.springbootblogrestapi.dto.LoginDto;
import com.springboot.blog.springbootblogrestapi.dto.SignUpDto;
import com.springboot.blog.springbootblogrestapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(
        name="CRUD REST APIs for Authorization"
)
public class AuthController {
    private AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    //Build Login REST API
    @PostMapping(value = {"/login","/signIn"})
    @Operation(
            summary = "Login/SignIn REST API",
            description = "Login REST Api is used to log In into the application"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token= authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse= new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    //Build Register REST API
    @PostMapping(value = {"/register","/signUp"})
    @Operation(
            summary = "Register/SignUp REST API",
            description = "Login REST Api is used to SignUp/Register into the application"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    public ResponseEntity<String> register(@RequestBody SignUpDto signUpDto){
        String response= authService.signUp(signUpDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
