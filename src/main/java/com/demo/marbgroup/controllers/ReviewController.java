package com.demo.marbgroup.controllers;

import com.demo.marbgroup.dtos.requests.AddReviewRequestDto;
import com.demo.marbgroup.dtos.requests.GetReviewByLivingHomeIdDto;
import com.demo.marbgroup.dtos.responses.LivingHomeResponseDto;
import com.demo.marbgroup.models.Reviews;
import com.demo.marbgroup.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@AllArgsConstructor
public class ReviewController {

    @Autowired
    private ReviewService reviewService;



    @PostMapping("/save")
    public ResponseEntity<LivingHomeResponseDto> save(@RequestBody AddReviewRequestDto addReviewRequestDto){
        LivingHomeResponseDto response = new LivingHomeResponseDto();
        reviewService.save(addReviewRequestDto);
        response.setMessage("Success");
        return new ResponseEntity<LivingHomeResponseDto>(response, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Reviews>> getAll(){
        return new ResponseEntity<List<Reviews>>(reviewService.getAllReviews(), HttpStatus.OK);
    }

    @PostMapping("/getByLivingHome")
    public ResponseEntity<List<Reviews>> getByLivingHome(@RequestBody GetReviewByLivingHomeIdDto getReviewByLivingHomeIdDto){
        return new ResponseEntity<List<Reviews>>(reviewService.getReviewsByLivingHomeId(getReviewByLivingHomeIdDto.getLivingHomeId()),HttpStatus.OK);
    }
}
