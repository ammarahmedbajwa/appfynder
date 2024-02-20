package com.demo.marbgroup.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<LivingHome> livingHome;
}
