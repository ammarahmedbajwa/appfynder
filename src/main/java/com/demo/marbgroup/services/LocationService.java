package com.demo.marbgroup.services;

import com.demo.marbgroup.dtos.requests.LivingHomeLocationRequestDto;
import com.demo.marbgroup.models.Location;
import com.demo.marbgroup.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAllLocations(){
        return locationRepository.findAll();
    }

    public List<Location> getByLocation(LivingHomeLocationRequestDto livingHomeLocationRequestDto){
        return locationRepository.findByLocation(livingHomeLocationRequestDto.getLocation());
    }
}
