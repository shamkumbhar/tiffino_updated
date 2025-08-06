package com.tiffino.reviewservice.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Transactional
@NoArgsConstructor
@Getter
@Setter
@Table(name = "reviews")

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long rating;
    private String comment;

    @CreationTimestamp                      // ✅ Auto-sets current time when saved
    @Column(updatable = false, nullable = false)
    private LocalDateTime reviewDate;

    @ElementCollection
    private List<String> photoUrls;

    private Integer likes;
    private Integer dislikes;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;


@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name = "review_id")
@JsonIgnore  // ❗️ Optional, if you don't want deep comments returned
private List<ReviewComment> reviewComments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference  // 🔁 Prevents infinite serialization with User
    private User user;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    @JsonBackReference  // Add this if Meal has a @OneToMany to Review
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference  // Same as above
    private Order order;

}

