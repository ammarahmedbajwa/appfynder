package com.demo.marbgroup.dtos.requests;

import com.demo.marbgroup.models.LivingHome;
import lombok.Data;

@Data
public class AddReviewRequestDto {

    private String review;
    private int rating;
    private Long livingHomeId;
}
