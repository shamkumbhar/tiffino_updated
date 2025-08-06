package com.tiffino.reviewservice.serviceImpl;


import com.tiffino.reviewservice.dto.ReviewCommentDTO;
import com.tiffino.reviewservice.dto.ReviewDTO;
import com.tiffino.reviewservice.entity.*;
import com.tiffino.reviewservice.repo.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MealRepository mealRepository;

    public ResponseEntity<?> findByMealId(Long id){
       try {
           return new ResponseEntity<>(mealRepository.findById(id),HttpStatus.OK) ;
       }catch (Exception e){
           return new ResponseEntity<>("Id Not Found" , HttpStatus.BAD_REQUEST);
       }


    }

    // ----------------- Review --------------------**
    // ----------------- Review --------------------**

    @Autowired
    ReviewRepository  reviewRepository;

    public Review createReview(ReviewDTO dto) {
        try {
            Review review = new Review();

            review.setRating(dto.getRating());
            review.setComment(dto.getComment());
            review.setPhotoUrls(dto.getPhotoUrls());
            review.setLikes(dto.getLikes());
            review.setDislikes(dto.getDislikes());
            review.setStatus(dto.getStatus());
            ReviewStatus status = dto.getStatus() != null ? dto.getStatus() : ReviewStatus.PENDING;//automatically assign a default status
            review.setStatus(status);
            // Retrieve related entities from DB
            User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
            Meal meal = mealRepository.findById(dto.getMealId()).orElseThrow(() -> new RuntimeException("Meal not found"));
            Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(() -> new RuntimeException("Order not found"));

            review.setUser(user);
            review.setMeal(meal);
            review.setOrder(order);
            return  reviewRepository.save(review);
//           return new  ResponseEntity("Review saved successfully.",HttpStatus.CREATED);
        } catch (Exception e) {
            throw new EntityNotFoundException("Error saving review: " + e.getMessage());
        }
    }

    // Get all reviews
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    // Get review by ID
    public ResponseEntity<?> getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        ReviewDTO dto = new ReviewDTO();
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setReviewDate(review.getReviewDate());
        dto.setPhotoUrls(review.getPhotoUrls());
        dto.setLikes(review.getLikes());
        dto.setDislikes(review.getDislikes());
        dto.setStatus(review.getStatus());
        dto.setUserId(review.getUser().getId());
        dto.setMealId(review.getMeal().getId());
        dto.setOrderId(review.getOrder().getId());

        return ResponseEntity.ok(dto);
    }

    // Delete review by ID (keeping only this one)
    public ResponseEntity<?> DeleteReviewById(Long id) {
        try {
            Optional<Review> optionalReview = reviewRepository.findById(id);
            if (optionalReview.isPresent()) {
                Review review = optionalReview.get();
                // Map to DTO
                ReviewDTO dto = new ReviewDTO();
                dto.setId(review.getId());
                // Delete the review
                reviewRepository.deleteById(id);

                return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<>("Review not found !", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // All IDs Delete
    public List<ReviewDTO> deleteAllReviewsById(List<Long> ids) {
        List<Review> reviews = reviewRepository.findAllById(ids);

        if (reviews.isEmpty()) {
            throw new EntityNotFoundException("No reviews found for the given IDs.");
        }
        // Map to DTOs before deletion
        List<ReviewDTO> deletedDTOs = reviews.stream().map(review -> {
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

        reviewRepository.deleteAllById(ids); // Delete after mapping

        return deletedDTOs; // Return deleted DTOs
    }

    // Update Review With Comment
        public ResponseEntity<?> ReviewUpdateById(Long id, ReviewDTO updateDTO) {
            try {
                Optional<Review> existReviewOptional = reviewRepository.findById(id);

                if (existReviewOptional.isPresent()) {
                    Review existReview = existReviewOptional.get();
                    // Update base fields from DTO
                    existReview.setRating(updateDTO.getRating());
                    existReview.setComment(updateDTO.getComment());
//                    existReview.setReviewDate(updateDTO.getReviewDate());
                    existReview.setPhotoUrls(updateDTO.getPhotoUrls());
                    existReview.setLikes(updateDTO.getLikes());
                    existReview.setDislikes(updateDTO.getDislikes());
                    existReview.setStatus(updateDTO.getStatus());
                    // (Optional) handle reviewComments if included in DTO — otherwise skip

                    reviewRepository.save(existReview);
                    // Map updated Review back to DTO
                    ReviewDTO updatedDTO = new ReviewDTO();
                    updatedDTO.setId(existReview.getId());
                    updatedDTO.setRating(existReview.getRating());
                    updatedDTO.setComment(existReview.getComment());
                    updatedDTO.setReviewDate(existReview.getReviewDate());
                    updatedDTO.setPhotoUrls(existReview.getPhotoUrls());
                    updatedDTO.setLikes(existReview.getLikes());
                    updatedDTO.setDislikes(existReview.getDislikes());
                    updatedDTO.setStatus(existReview.getStatus());
                    updatedDTO.setUserId(existReview.getUser() != null ? existReview.getUser().getId() : null);
                    updatedDTO.setMealId(existReview.getMeal() != null ? existReview.getMeal().getId() : null);
                    updatedDTO.setOrderId(existReview.getOrder() != null ? existReview.getOrder().getId() : null);

                    if (updateDTO.getStatus() == null) {
                        return new ResponseEntity<>("Status must not be null", HttpStatus.BAD_REQUEST);
                    }

                    return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Review with given ID does not exist", HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Error updating Review: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }


    // ----------------- Review Comments --------------------//
    // ----------------- Review Comments --------------------//

@Autowired
ReviewCommentRepository commentRepository;

    // Save Comment
    public ReviewComment SaveComments(@RequestBody ReviewCommentDTO dto) {
        ReviewComment comment = new ReviewComment();
        comment.setComment(dto.getComment());
        comment.setCommentDate(LocalDateTime.now());

        // Fetch related entities
        Review review = reviewRepository.findById(dto.getReviewId())
                .orElseThrow(() -> new RuntimeException("Review not found"));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        comment.setReview(review);
        comment.setUser(user);

        return  commentRepository.save(comment);

    }

    // Get comments by ID
    public ResponseEntity<?> getCommentById(Long id) {
        Optional<ReviewComment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            return new ResponseEntity<>(optionalComment.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }
    }

    // Get all comments
    public List<ReviewComment> getAllComments() {
        return commentRepository.findAll();
    }

    // Update  Comment
    public ResponseEntity<?> UpdateComments( Long id, ReviewCommentDTO updatedComment) {
        Optional<ReviewComment> optional = commentRepository.findById(id);
        if (optional.isPresent()) {
            if (updatedComment.getComment() == null || updatedComment.getComment().trim().isEmpty()) {
                return new ResponseEntity<>("Comment text cannot be empty", HttpStatus.BAD_REQUEST);
            }
            ReviewComment existingcomment = optional.get();
            existingcomment.setComment(updatedComment.getComment());
            existingcomment.setCommentDate(updatedComment.getCommentDate());
            return new ResponseEntity<>(commentRepository.save(existingcomment), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Review with given ID does not exist", HttpStatus.NOT_FOUND);
        }
    }

    // Delete comment by ID (keeping only this one)
    public ResponseEntity<?> deleteComment(Long id) {
        commentRepository.findById(id);
        if (!commentRepository.existsById(id)) {
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }
        commentRepository.deleteById(id);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

    // All IDs Delete
    public String deleteMultipleComment(List<Long> ids) {
        List<ReviewComment> comments =commentRepository.findAllById(ids);
        if (comments.isEmpty()) {
            throw new EntityNotFoundException("No comments found for given IDs.");
        }
        commentRepository.deleteAllById(ids);
        return "Deleted " + comments.size() + " comments successfully.";

    }

}
