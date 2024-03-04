package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.dto.LoginDto;
import com.springboot.blog.springbootblogrestapi.dto.SignUpDto;
import com.springboot.blog.springbootblogrestapi.entity.Role;
import com.springboot.blog.springbootblogrestapi.entity.User;
import com.springboot.blog.springbootblogrestapi.exception.BlogAPIException;
import com.springboot.blog.springbootblogrestapi.repository.RoleRepository;
import com.springboot.blog.springbootblogrestapi.repository.UserRepository;
import com.springboot.blog.springbootblogrestapi.security.JWTTokenProvider;
import com.springboot.blog.springbootblogrestapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class authServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    public authServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String signUp(SignUpDto signUp) {
        //add check for check username exist in DB or not
        if(userRepository.existsByUsername(signUp.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Username already exist");
        }
        //add check for email exist in DB
        if(userRepository.existsByEmail(signUp.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Email already exist");
        }
        User user=new User();
        user.setName(signUp.getName());
        user.setUsername(signUp.getUsername());
        user.setEmail(signUp.getEmail());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));

        Set<Role> roles=new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        return "User Registered Successfully!!";
    }
}
