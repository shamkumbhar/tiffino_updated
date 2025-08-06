package com.tiffino.reviewservice.controller;


import com.tiffino.reviewservice.dto.ReviewCommentDTO;
import com.tiffino.reviewservice.dto.ReviewDTO;
import com.tiffino.reviewservice.entity.*;
import com.tiffino.reviewservice.repo.*;
import com.tiffino.reviewservice.serviceImpl.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class Controller {


    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;

    @Autowired
    ReviewCommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MealRepository mealRepository;

    //    __________User_____________
    @PostMapping("/user")
    public ResponseEntity<?> saveuser(@RequestBody User user){
        return new ResponseEntity<>( userRepository.save(user),HttpStatus.CREATED);
    }

    @PostMapping("/order")
    public ResponseEntity<?> saveOrder(@RequestBody Order order){
        return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
    }
    @PostMapping("/meal")
    public ResponseEntity<?> saveMeal(@RequestBody Meal meal){
        return new ResponseEntity<>(mealRepository.save(meal), HttpStatus.OK);
    }

    @GetMapping("/meal/{id}")
    public ResponseEntity<?> getMealWithReviews(@PathVariable Long id) {
        Optional<Meal> mealOpt = mealRepository.findById(id);

        if (mealOpt.isEmpty()) {

            return new ResponseEntity<>("Meal ID " + id + " not found", HttpStatus.NOT_FOUND);
        }

        Meal meal = mealOpt.get();

        return ResponseEntity.ok(meal);
    }

//   ________________________________Review_________________________________________  //
//   ________________________________      _________________________________________  //

    @PostMapping("/reviewsSave")
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO dto) {
        try {
            reviewService.createReview(dto);
            if (dto.getStatus() == null) {
                return new ResponseEntity<>("Status must not be null", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Review saved successfully.", HttpStatus.CREATED);
        }catch (Exception e) {
            throw new EntityNotFoundException("Error saving review: " + e.getMessage());
        }
    }

    // Get all reviews
    @GetMapping("/ReviewGetAll")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        List<ReviewDTO> dtoList = reviews.stream().map(review -> {
            ReviewDTO dto = new ReviewDTO();
            dto.setId(review.getId());
            dto.setRating(review.getRating());
            dto.setComment(review.getComment());
            dto.setReviewDate(review.getReviewDate());
            dto.setPhotoUrls(review.getPhotoUrls());
            dto.setLikes(review.getLikes());
            dto.setDislikes(review.getDislikes());
            dto.setStatus(review.getStatus());
            dto.setUserId(review.getUser() != null ? review.getUser().getId() : null);
            dto.setMealId(review.getMeal() != null ? review.getMeal().getId() : null);
            dto.setOrderId(review.getOrder() != null ? review.getOrder().getId() : null);
            return dto;
        }).toList();
        return ResponseEntity.ok(dtoList);
    }
    // Get review by ID
    @GetMapping("/ReviewGetById/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long id) {
        try {
            return reviewService.getReviewById(id);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete review by ID (keeping only this one)
    @DeleteMapping("/ReviewDeleteById/{id}")
    public ResponseEntity<?> deleteReviews(@PathVariable Long id) {
         reviewService.DeleteReviewById(id);
        return new ResponseEntity<>(id +" Id Delete successfully !", HttpStatus.ACCEPTED);
    }

        // All IDs Delete
    @DeleteMapping("/ReviewDelete-all")
    public ResponseEntity<?> deleteMultipleReviews(@RequestBody List<Long> ids) {
        try {
             reviewService.deleteAllReviewsById(ids);
            return new ResponseEntity<>(ids.size() + " Ids Deleted ", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>( e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting reviews: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update Review With Comment
    @PutMapping("/ReviewUpdate/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody ReviewDTO updateDTO) {
        if (updateDTO.getStatus() == null) {
            return new ResponseEntity<>("Status must not be null", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(reviewService.ReviewUpdateById(id, updateDTO), HttpStatus.OK);
    }


    // ----------------- Review Comments --------------------//
    // ----------------- Review Comments --------------------//


    // Save Comment
    @PostMapping("/commentsSave")
    public ResponseEntity<?> saveComments(@RequestBody ReviewCommentDTO dto) {
        try {
            reviewService.SaveComments(dto);
            return new ResponseEntity<>("Comment saved successfully.", HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all comments
    @GetMapping("/commentsGetByAll")
    public ResponseEntity<List<ReviewComment>> getAllComments() {
        List<ReviewComment> comments = reviewService.getAllComments();;
        return new ResponseEntity<>(comments, HttpStatus.OK);

    }

    // Get comments by ID
    @GetMapping("/commentsGetById/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Long id) {
        return new ResponseEntity<>(reviewService.getCommentById(id),HttpStatus.OK);
    }

    // Update  Comment
    @PutMapping("/commentsUpdate/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody ReviewCommentDTO updatedComment) {
        return new ResponseEntity<>(reviewService.UpdateComments(id, updatedComment),HttpStatus.OK);
    }

    // Delete comment by ID (keeping only this one)
    @DeleteMapping("/commentDeleteById/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        reviewService.deleteComment(id);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

    // All IDs Delete
    @DeleteMapping("/comment-Delete-all")
    public ResponseEntity<?> deleteMultipleComment(@RequestBody List<Long> ids) {
        try {
            String result = reviewService.deleteMultipleComment(ids);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting comments: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
