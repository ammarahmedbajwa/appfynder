package com.demo.marbgroup.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String review;
    private int rating;

    //living home id to be added here
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "livingHome_id")
    private LivingHome livingHome;
}
