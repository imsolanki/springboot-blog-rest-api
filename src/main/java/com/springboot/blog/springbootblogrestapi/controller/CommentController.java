package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.dto.CommentDto;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(
        name="CRUD REST APIs for Comment resource"
)
public class CommentController {
    private CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @Operation(
            summary = "Create Comment REST API",
            description = "Create Comment REST Api is used to save comments into DB for each post"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") Long postId,
                                                    @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto),
                HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    @Operation(
            summary = "Get All Comments REST API",
            description = "Get all Comments REST Api is used to get all comments from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable(value="postId") Long postId){
        return new ResponseEntity<>(commentService.getAllCommentByPostId(postId),HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}/comments/{commentId}")
    @Operation(
            summary = "Get Comment REST API",
            description = "Get Comment REST Api is used to get a comment from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name="postId") Long postId,
                                                     @PathVariable(name="commentId") Long commentId){
        return new ResponseEntity<>(commentService.getCommentById(postId,commentId),HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @Operation(
            summary = "Update Comment REST API",
            description = "Update Category REST Api is used to update a comment into DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable(name="postId") Long postId,
                                                        @PathVariable(name="commentId")Long commentId,
                                                        @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateCommentById(postId,commentId,commentDto),
                HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @Operation(
            summary = "Delete Category REST API",
            description = "Delete Category REST Api is used to delete a comment from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<String> deleteCommentById(@PathVariable(name="postId") Long postId,
                                                    @PathVariable(name="commentId") Long commentId){
        commentService.deleteCommentById(postId,commentId);
        return new ResponseEntity<>("Comment Deleted Successfully",
                HttpStatus.OK);
    }
}
