package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.dto.CommentDto;
import com.springboot.blog.springbootblogrestapi.entity.Comment;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.BlogAPIException;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.repository.CommentRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,
                              ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        //retrieve post by Id
        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post","id",postId));
        //set post to comment entity
        comment.setPost(post);
        Comment comment1=commentRepository.save(comment);
        return mapToDto(comment1);
    }

    @Override
    public List<CommentDto> getAllCommentByPostId(Long postId) {
        //Retrieve comments by postID
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {

        //retrieve post by Id
        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post","id",postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("Comment","id",commentId));

        //if comment does not beLong to particular post throw the blog api exception
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.NOT_FOUND,"Comment does not belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto) {
        //retrieve post by Id
        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post","id",postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("Comment","id",commentId));

        //if comment does not beLong to particular post throw the blog api exception
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.NOT_FOUND,"Comment does not belong to post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        //retrieve post by Id
        Post post=postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post","id",postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("Comment","id",commentId));

        //if comment does not beLong to particular post throw the blog api exception
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.NOT_FOUND,"Comment does not belong to post");
        }
        else{
            commentRepository.delete(comment);
        }
    }


    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto= modelMapper.map(comment,CommentDto.class);
        /*
        *Manual Code to convert entities
        //convert Entity to DTO
        CommentDto commentDto =new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
         */

        return commentDto;
    }
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment =modelMapper.map(commentDto,Comment.class);

        /*
        //Manual Conversion of entities
        //convert DTO to Entity
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
         */
        return comment;
    }
}
