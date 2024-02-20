package com.demo.marbgroup.controllers;

import com.demo.marbgroup.dtos.requests.LivingHomeLocationRequestDto;
import com.demo.marbgroup.dtos.requests.LivingHomeRequestDto;
import com.demo.marbgroup.dtos.responses.LivingHomeResponseDto;
import com.demo.marbgroup.models.LivingHome;
import com.demo.marbgroup.models.Location;
import com.demo.marbgroup.services.LivingHomeService;
import com.demo.marbgroup.services.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livingHome")
@AllArgsConstructor
public class LivingHomeController {

    @Autowired
    private LivingHomeService livingHomeService;

    @Autowired
    private LocationService locationService;


    @PostMapping("/save")
    public ResponseEntity<LivingHomeResponseDto> saveLivingHome(@RequestBody LivingHomeRequestDto livingHomeRequestDto){
        LivingHomeResponseDto response = new LivingHomeResponseDto();
        Boolean livingHome = livingHomeService.saveNewLivingHome(livingHomeRequestDto);
        if (livingHome){
            response.setMessage("Successful");
            return new ResponseEntity<LivingHomeResponseDto>(response, HttpStatus.OK);
        }
        else {
            response.setMessage("Failed");
            return new ResponseEntity<LivingHomeResponseDto>(response, HttpStatus.OK);
        }
    }

    @GetMapping ("/getAll")
    public ResponseEntity<List<LivingHome>> getAllLivingHomes(){
//        LivingHomeResponseDto response = new LivingHomeResponseDto();

        List<LivingHome> response = livingHomeService.getAllLivingHome();
        return new ResponseEntity<List<LivingHome>>(response, HttpStatus.OK);

    }

    @PostMapping("/getByLocation")
    public ResponseEntity<List<Location>> getByLocation(@RequestBody LivingHomeLocationRequestDto livingHomeLocationRequestDto){
        List<Location> response = locationService.getByLocation(livingHomeLocationRequestDto);
        return new ResponseEntity<List<Location>>(response, HttpStatus.OK);
    }


}
