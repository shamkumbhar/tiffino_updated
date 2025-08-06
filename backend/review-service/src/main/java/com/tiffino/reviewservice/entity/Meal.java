package com.tiffino.reviewservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Transactional
@NoArgsConstructor
public class Meal {
    @Id
    @GeneratedValue
    private Long id;
    private String mealOrder;


//    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL)
//    @JsonManagedReference // Matches Review.meal
//    private List<Review> reviews;


}
