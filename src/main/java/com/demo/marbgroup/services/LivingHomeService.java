package com.demo.marbgroup.services;

import com.demo.marbgroup.dtos.requests.LivingHomeLocationRequestDto;
import com.demo.marbgroup.dtos.requests.LivingHomeRequestDto;
import com.demo.marbgroup.dtos.requests.LivingHomeServicesRequestDto;
import com.demo.marbgroup.models.LivingHome;
import com.demo.marbgroup.repositories.LivingHomeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class LivingHomeService {
    @Autowired
    private LivingHomeRepository livingHomeRepository;




    public Boolean saveNewLivingHome(LivingHomeRequestDto livingHomeRequestDto){
        LivingHome livingHome = new LivingHome();
        livingHome.setName(livingHomeRequestDto.getName());
        livingHome.setLocation(livingHomeRequestDto.getLocation());
        livingHome.setServices(livingHomeRequestDto.getServices());
        livingHome.setReviews(livingHomeRequestDto.getReviews());

        livingHomeRepository.save(livingHome);
        return true;
    }

    public List<LivingHome> getAllLivingHome(){
        return livingHomeRepository.findAll();
    }

    public List<LivingHome> getLivingHomesByLocation(LivingHomeLocationRequestDto livingHomeLocationRequestDto){
        return livingHomeRepository.findByLocation(livingHomeLocationRequestDto.getLocation());
    }

    public List<LivingHome> getLivingHomeByServices(LivingHomeServicesRequestDto livingHomeServicesRequestDto){
        return livingHomeRepository.findByServices(livingHomeServicesRequestDto.getService());
    };
}
