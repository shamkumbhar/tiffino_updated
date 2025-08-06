package com.tiffino.orderservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String pincode;

    private Double latitude;

    private Double longitude;

    private Boolean isDefault;

    private String addressType;
}











