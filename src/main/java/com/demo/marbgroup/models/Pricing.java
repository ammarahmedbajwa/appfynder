package com.demo.marbgroup.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "pricing")
public class Pricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int price;
}
