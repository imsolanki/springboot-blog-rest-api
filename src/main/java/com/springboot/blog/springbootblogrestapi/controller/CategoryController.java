package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.dto.CategoryDto;
import com.springboot.blog.springbootblogrestapi.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(
        name="CRUD REST APIs for Category resource"
)
public class CategoryController {
    private CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //build add category rest api
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @Operation(
            summary = "Add Category REST API",
            description = "Add Category REST Api is used to save category into DB"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Get Category REST API",
            description = "Get Category REST Api is used to get a category from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(name ="id") Long categoryId){
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
    }

    @GetMapping
    @Operation(
            summary = "Get All Categories REST API",
            description = "Get all Categories REST Api is used to get all categories from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @Operation(
            summary = "Update Category REST API",
            description = "Update Category REST Api is used to update a category into DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable(name="id") Long categoryId){
        return new ResponseEntity<>(categoryService.updateCategory(categoryDto,categoryId), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @Operation(
            summary = "Delete Category REST API",
            description = "Delete Category REST Api is used to delete a category from DB"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
    public ResponseEntity<String> deleteCategory(@PathVariable(name="id") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category Deleted Successfully!!", HttpStatus.OK);
    }
}
