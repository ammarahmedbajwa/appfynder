package com.demo.marbgroup.dtos.requests;

import com.demo.marbgroup.models.Location;
import com.demo.marbgroup.models.Reviews;
import com.demo.marbgroup.models.Services;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class LivingHomeRequestDto {
    private String name;
    private Location location;
    private List<Reviews> reviews;
    private Set<Services> services = new HashSet<>();
}
