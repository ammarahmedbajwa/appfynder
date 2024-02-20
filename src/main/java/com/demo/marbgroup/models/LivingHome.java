package com.demo.marbgroup.models;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "living_home")
public class LivingHome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "livingHome", cascade = CascadeType.ALL)
    private List<Reviews> reviews;

    @ManyToMany(mappedBy = "livingHome")
    private Set<Services> services = new HashSet<>();

}
