//5
package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.dto.PostDto;
import com.springboot.blog.springbootblogrestapi.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNumber, int pageSize,String sortBy,String sortDir);
                            //Pagination & sorting --Step2
    PostDto getPostById(Long id);

    PostDto updatePostById(PostDto postDto,Long id);

    void deletePostById(Long id);

    List<PostDto> getPostsByCategory(Long categoryId);
}
