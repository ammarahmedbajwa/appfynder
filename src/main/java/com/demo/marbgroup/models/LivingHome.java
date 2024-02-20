package com.demo.marbgroup.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "livingHome", cascade = CascadeType.ALL)
    private List<Reviews> reviews;

    @ManyToMany(mappedBy = "livingHome", cascade = CascadeType.ALL)
    private Set<Services> services = new HashSet<>();

}
