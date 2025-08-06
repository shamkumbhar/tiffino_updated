package com.tiffino.reviewservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Transactional
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
            (strategy = GenerationType.AUTO)
    private Long id;
    private String username;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @JsonManagedReference // Matches Review.user
//    private List<Review> reviews;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @JsonManagedReference // Matches ReviewComment.user
//    private List<ReviewComment> reviewsComment;
}