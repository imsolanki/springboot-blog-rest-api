//6
package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.dto.PostDto;
import com.springboot.blog.springbootblogrestapi.dto.PostResponse;
import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.repository.CategoryRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    private CategoryRepository categoryRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper,
                           CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
        this.categoryRepository=categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category=categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category","id", postDto.getCategoryId()));

        Post post = mapToEntity(postDto);
        post.setCategory(category);
        Post newPost=postRepository.save(post);
        PostDto postResponse= mapToDto(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
        //Pagination support --> step3
        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> posts=postRepository.findAll(pageable);
        //get content for page object
        List<Post>listOfPosts=posts.getContent();

        List<PostDto> content= listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse= new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePostById(PostDto postDto,Long id) {
        System.out.println(id);
        //get post by id
        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);
        Post updatedPost = postRepository.save(post);

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(Long id) {
        //get post by id
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream().map((post)->mapToDto(post))
                .collect(Collectors.toList());
    }

    private PostDto mapToDto(Post post){
        PostDto postDto= modelMapper.map(post,PostDto.class);
/*
*Manual code to convert the entities without model mapper
        //convert Entity to DTO
        PostDto postDto1 =new PostDto();
        postDto1.setId(post.getId());
        postDto1.setTitle(post.getTitle());
        postDto1.setDescription(post.getDescription());
        postDto1.setContent(post.getContent());
*/
        return postDto;
    }
    private Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto,Post.class);
        /*
        *Converting manually
         //convert DTO to Entity
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
         */

        return post;
    }
}
