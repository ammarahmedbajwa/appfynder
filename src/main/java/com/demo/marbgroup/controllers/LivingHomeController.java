package com.demo.marbgroup.controllers;

import com.demo.marbgroup.dtos.requests.LivingHomeRequestDto;
import com.demo.marbgroup.dtos.responses.LivingHomeResponseDto;
import com.demo.marbgroup.models.LivingHome;
import com.demo.marbgroup.services.LivingHomeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livingHome")
@AllArgsConstructor
public class LivingHomeController {


    private LivingHomeService livingHomeService;

    @Autowired
    private LivingHomeResponseDto livingHomeResponseDto;

    @PostMapping("/save")
    public ResponseEntity<LivingHomeResponseDto> saveLivingHome(LivingHomeRequestDto livingHomeRequestDto){
        Boolean livingHome = livingHomeService.saveNewLivingHome(livingHomeRequestDto);
        if (livingHome){
            livingHomeResponseDto.setMessage("Successful");
            return new ResponseEntity<LivingHomeResponseDto>(livingHomeResponseDto, HttpStatus.OK);
        }
        else {
            livingHomeResponseDto.setMessage("Failed");
            return new ResponseEntity<LivingHomeResponseDto>(livingHomeResponseDto, HttpStatus.OK);
        }
    }

}
