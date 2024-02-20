package com.demo.marbgroup.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "services")
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "livingHome_services",
    joinColumns = @JoinColumn(name = "services_id"),
    inverseJoinColumns = @JoinColumn(name = "livingHome_id"))
    private Set<LivingHome> livingHome = new HashSet<>();
}
