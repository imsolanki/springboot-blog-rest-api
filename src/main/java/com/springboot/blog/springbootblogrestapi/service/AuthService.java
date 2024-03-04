package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.dto.LoginDto;
import com.springboot.blog.springbootblogrestapi.dto.SignUpDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String signUp(SignUpDto signUp);
}
