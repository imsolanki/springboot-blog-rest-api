package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long postId,CommentDto commentDto);

    List<CommentDto> getAllCommentByPostId(Long postId);

    CommentDto getCommentById(Long postId,Long commentId);

    CommentDto updateCommentById(Long postId,Long commentId,CommentDto commentDto);

    void deleteCommentById(Long postId,Long commentId);
}
