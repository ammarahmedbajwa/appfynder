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

    @Autowired
    private LivingHome livingHome;

    public Boolean saveNewLivingHome(LivingHomeRequestDto livingHomeRequestDto){
        livingHome.setName(livingHomeRequestDto.getName());
        livingHome.setLocation(livingHomeRequestDto.getLocation());
        livingHome.setServices(livingHomeRequestDto.getServices());
        livingHome.setReviews(livingHomeRequestDto.getReviews());

        livingHomeRepository.save(livingHome);
        return true;
    }

    public List<LivingHome> getLivingHomesByLocation(LivingHomeLocationRequestDto livingHomeLocationRequestDto){
        return livingHomeRepository.findByLocationName(livingHomeLocationRequestDto.getLocation());
    }

    public List<LivingHome> getLivingHomeByServices(LivingHomeServicesRequestDto livingHomeServicesRequestDto){
        return livingHomeRepository.findByServiceName(livingHomeServicesRequestDto.getService());
    };
}
