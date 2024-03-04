//1
package com.springboot.blog.springbootblogrestapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

//@Data-->Leads to the stack overflow error as while capturing one post it will have multiple comments and its is also overding toString() so use apprpraite annotations only.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name="posts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(name="title",nullable = false)
    private String title;
    @Column(name="description",nullable = false)
    private String description;
    @Column(name="content",nullable = false)
    private String content;
    //nullable = false --> Not null

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true) //One-->Post,Many-->Comments //SET wont allow duplicate comments
    private Set<Comment> comments=new HashSet<>();
    //mapped by--> mtlb ki ye kisse map hai kiske lie hai

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
