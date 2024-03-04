//7
package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.dto.PostDto;
import com.springboot.blog.springbootblogrestapi.dto.PostDtoV2;
import com.springboot.blog.springbootblogrestapi.dto.PostResponse;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import com.springboot.blog.springbootblogrestapi.utils.AppConstants;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
@Tag(
        name="CRUD REST APIs for Post resource"
)
public class PostController {
    private PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog post rest api
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST Api is used to save post into DB for each category"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/posts")
    @Operation(
            summary = "Get All Post REST API",
            description = "Get all Post REST Api is used to get all post from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<PostResponse> getAllPost(
            //pagination Support-step1
            @RequestParam(value = AppConstants.DEFAULT_PAGE_NO,defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = AppConstants.DEFAULT_PAGE_SIZE,defaultValue = "10",required = false)int pageSize,
            @RequestParam(value = AppConstants.DEFAULT_SORT_BY,defaultValue = "id",required = false)String sortBy,
            @RequestParam(value = AppConstants.DEFAULT_SORT_DIR,defaultValue = "asc",required = false)String sortDir

    ){
        return new ResponseEntity<>(postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @GetMapping("/api/v1/posts/{id}")
    @Operation(
            summary = "Get Post REST API",
            description = "Get Post REST Api is used to get a Post from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") Long id){
        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }

    /*
    * Rest API supports Versioning through URI Path
    * The API is versioned in the second release of the application.
    * All the changes given by client has been applied in the REST Api.
    * feature added --> Tags are also getting fetched along with post
     */
    @GetMapping("/api/v2/posts/{id}")
    @Operation(
            summary = "Get Post REST API - Version 2",
            description = "Get Post REST Api is used to get a Post from DB-Version2(Supports Tags)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable(name="id") Long id){
        PostDto postDto = postService.getPostById(id);
        PostDtoV2 postDtoV2 =new PostDtoV2();
        postDtoV2.setId(postDto.getId());
        postDtoV2.setTitle(postDto.getTitle());
        postDtoV2.setDescription(postDto.getDescription());
        postDtoV2.setContent(postDtoV2.getContent());
        List<String>tags= new ArrayList<>();
        tags.add("AWS");
        tags.add("JAVA");
        tags.add("SPRING BOOT");
        tags.add("JAVASCRIPT");
        postDtoV2.setTags(tags);
        return new ResponseEntity<>(postDtoV2,HttpStatus.OK);
    }

    @PutMapping("/api/v1/posts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @Operation(
            summary = "Update Post REST API",
            description = "Update Post REST Api is used to update a Post into DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto,
                                                  @PathVariable(name="id") Long id){
        return new ResponseEntity<>(postService.updatePostById(postDto,id),HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post REST Api is used to delete a post from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<String> deletePostById(@PathVariable(name="id") Long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post Deleted Successfully.",HttpStatus.OK);
    }

    //build get post by category rest API
    @GetMapping("/api/v1/posts/category/{id}")
    @Operation(
            summary = "GET Post by Category REST API",
            description = "Get Post by Category REST Api is used to Get all the posts associated by Category from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable(name="id") Long categoryId){
        List<PostDto> postDtos = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }
}
