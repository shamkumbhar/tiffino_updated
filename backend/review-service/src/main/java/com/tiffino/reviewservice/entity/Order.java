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
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String orderType;

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    @JsonManagedReference // Matches Review.order
//    private List<Review> reviews;
}
