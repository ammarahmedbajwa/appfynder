package com.demo.marbgroup.services;

import com.demo.marbgroup.dtos.requests.AddReviewRequestDto;
import com.demo.marbgroup.models.Reviews;
import com.demo.marbgroup.repositories.ReviewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    @Autowired
    private ReviewsRepository reviewsRepository;

    public List<Reviews> getAllReviews(){
        return reviewsRepository.findAll();
    }

    public void save(AddReviewRequestDto addReviewRequestDto){
        Reviews reviews = new Reviews();

        reviews.setReview(addReviewRequestDto.getReview());
        reviews.setRating(addReviewRequestDto.getRating());
        reviews.setLivingHomeId(addReviewRequestDto.getLivingHomeId());

        reviewsRepository.save(reviews);
    }

    public List<Reviews> getReviewsByLivingHomeId(Long id){
        return reviewsRepository.findByLivingHomeId(id);
    }
}
