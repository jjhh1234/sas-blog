package com.ex.sas.Domain.Category.Entity;

import com.ex.sas.Domain.Post.Entity.PostEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Category")
public class CategoryEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer category_pk;

    @OneToOne
    @JoinColumn(name="post_pk")
    private PostEntity post;

    @Column
    private String category_name;
}
