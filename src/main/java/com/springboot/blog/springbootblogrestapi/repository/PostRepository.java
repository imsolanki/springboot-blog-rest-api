//2
package com.springboot.blog.springbootblogrestapi.repository;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


//@Repository-->not needed as SimpleJpaRepository already annotated by it.
public interface PostRepository extends JpaRepository<Post,Long> {
                                            //<EntityType,PrimaryKeyType>

    //SpringData JPA Internally provides all methods needed for CRUD
    //Jpa Repository supports Pagination & Sorting

    List<Post> findByCategoryId(Long categoryId);
}

















//    @Bean
//    public SimpleJpaRepository<YourEntity, Long> getYourEntitySimpleRepository() {
//        return new SimpleJpaRepository<>(YourEntity.class, entityManager);
//    }