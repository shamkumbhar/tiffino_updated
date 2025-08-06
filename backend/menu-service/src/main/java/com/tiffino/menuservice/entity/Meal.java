package com.tiffino.menuservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "meal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mealName;

    @Column(nullable = false)
    private String mealType; // Example: VEG, NON_VEG (can later use an Enum)

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "cuisine_id")
    private Cuisine cuisine;
}
